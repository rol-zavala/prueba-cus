package com.pruebacus.pruebacus.services.AccountMoneyService;

import com.pruebacus.pruebacus.advisors.ResponseAdvisor;
import com.pruebacus.pruebacus.models.carts.CartsEntity;
import com.pruebacus.pruebacus.models.dtos.CartsRequestDTO;
import com.pruebacus.pruebacus.models.dtos.DepositRequestDTO;
import com.pruebacus.pruebacus.models.users.UserEntity;
import com.pruebacus.pruebacus.repositories.UserRepository;
import com.pruebacus.pruebacus.repositories.carts.CartsRepository;
import com.pruebacus.pruebacus.services.carts.CartsService;
import com.pruebacus.pruebacus.services.users.UserSevice;
import com.pruebacus.pruebacus.wrappers.accountMoney.AccountMoneyWrapperResponse;
import com.pruebacus.pruebacus.wrappers.carts.CartAndAccountMoneyWrapperResponse;
import com.pruebacus.pruebacus.wrappers.carts.CartsWrapperResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountMoneyService {
    @Autowired
    CartsRepository cartsRepository;


    @Autowired
    CartsService cartsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserSevice userSevice;

    public Pair<CartAndAccountMoneyWrapperResponse, ResponseAdvisor> payCart (Long id) throws Exception {
        ResponseAdvisor advisor = new ResponseAdvisor(200, "SUCCESS");
        CartsEntity cartsEntity = new CartsEntity();
        CartsWrapperResponse order = new CartsWrapperResponse();
        try {
            cartsEntity =  cartsRepository.findById(id);
            if(cartsEntity.isPaid())throw new Exception("Orden ya se encuentra pagada");
            order = cartsService.getWrapperResponse(new CartsRequestDTO(cartsEntity.getProducts()) , cartsEntity.getUser(), cartsEntity.getId(), cartsEntity.isPaid());
            if(cartsEntity.getUser().getCash()<order.getTotal())throw new Exception("Fondos Insuficientes");
            cartsEntity.getUser().setCash(cartsEntity.getUser().getCash()- order.getTotal());
            order.setPaid(cartsService.updatePaid(order.getId()));

        }catch (Exception ex){
            log.error(ex.getMessage());
            advisor.setErrorCode(400);
            advisor.setStatusError(HttpStatus.BAD_REQUEST.name());
            advisor.setMessage(ex.getMessage());
            order = new CartsWrapperResponse();
        }

        return Pair.of(new CartAndAccountMoneyWrapperResponse(order,new AccountMoneyWrapperResponse(cartsEntity.getUser().getUsername(), cartsEntity.getUser().getCash()) ), advisor);
    }


    public Pair<AccountMoneyWrapperResponse, ResponseAdvisor> addDeposit(DepositRequestDTO depositRequestDTO){
        ResponseAdvisor advisor = new ResponseAdvisor(200, "SUCCESS");
        UserEntity user = userSevice.whoami();
        try {
            user.setCash(user.getCash()+depositRequestDTO.getCash());
            userRepository.save(user);
        }catch (Exception ex){
            log.error(ex.getMessage());
            advisor.setErrorCode(400);
            advisor.setStatusError(HttpStatus.BAD_REQUEST.name());
            advisor.setMessage(ex.getMessage());
        }
        return Pair.of(new AccountMoneyWrapperResponse(user.getUsername(), user.getCash()),advisor);
    }

    public Pair<AccountMoneyWrapperResponse, ResponseAdvisor> getMyAccountMoney(){
        ResponseAdvisor advisor = new ResponseAdvisor(200, "SUCCESS");

        AccountMoneyWrapperResponse accountMoneyWrapperResponses = new AccountMoneyWrapperResponse();
        try {
            UserEntity user = userSevice.whoami();
            accountMoneyWrapperResponses.setUsername(user.getUsername());
            accountMoneyWrapperResponses.setCash(user.getCash());
        }catch (Exception ex){
            log.error(ex.getMessage());
            advisor.setErrorCode(400);
            advisor.setStatusError(HttpStatus.BAD_REQUEST.name());
            advisor.setMessage(ex.getMessage());
        }
        return Pair.of(accountMoneyWrapperResponses, advisor);
    }
    public Pair<List<AccountMoneyWrapperResponse>, ResponseAdvisor> getAllAccountMoney(){
        ResponseAdvisor advisor = new ResponseAdvisor(200, "SUCCESS");
        List<UserEntity> userEntityList;
        List<AccountMoneyWrapperResponse> accountMoneyWrapperResponses = new LinkedList<>();
        userEntityList = userRepository.findAll();
        try {
            for (UserEntity userItem : userEntityList){
                accountMoneyWrapperResponses.add(new AccountMoneyWrapperResponse(userItem.getUsername(), userItem.getCash()));
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
            advisor.setErrorCode(400);
            advisor.setStatusError(HttpStatus.BAD_REQUEST.name());
            advisor.setMessage(ex.getMessage());
        }
        return Pair.of(accountMoneyWrapperResponses, advisor);
    }

}
