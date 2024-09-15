package com.pruebacus.pruebacus.services.carts;

import com.pruebacus.pruebacus.advisors.ResponseAdvisor;
import com.pruebacus.pruebacus.models.carts.CartsEntity;
import com.pruebacus.pruebacus.models.carts.CartsProductsEntity;
import com.pruebacus.pruebacus.models.carts.EStatusCart;
import com.pruebacus.pruebacus.models.dtos.CartsProducDTO;
import com.pruebacus.pruebacus.models.dtos.CartsRequestDTO;
import com.pruebacus.pruebacus.models.products.ProductsProxy;
import com.pruebacus.pruebacus.models.users.UserEntity;
import com.pruebacus.pruebacus.repositories.carts.CartsRepository;
import com.pruebacus.pruebacus.services.products.ProductsService;
import com.pruebacus.pruebacus.services.users.UserSevice;
import com.pruebacus.pruebacus.wrappers.carts.CartsWrapperResponse;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartsService {

    @Autowired
    CartsRepository cartsRepository;

    @Autowired
    UserSevice userSevice;

    @Autowired
    ProductsService productsService;

    public Pair<CartsWrapperResponse, ResponseAdvisor> save(CartsRequestDTO cartsRequestDTO){
        ResponseAdvisor advisor = new ResponseAdvisor(200, "SUCCESS");

        CartsWrapperResponse cartsWrapperResponse = new CartsWrapperResponse();
        CartsEntity carts;
        UserEntity user = userSevice.whoami();
        boolean isPaid = false;
        try {
            cartsWrapperResponse = getWrapperResponse(cartsRequestDTO, user, 0L, isPaid);

            carts = CartsEntity.builder()
                    .products(cartsRequestDTO.getProducts())
                    .user(user)
                    .isPaid(isPaid)
                    .build();

            carts=cartsRepository.save(carts);
            cartsWrapperResponse.setId(carts.getId());

        }catch (Exception ex){
            log.error(ex.getMessage());
            advisor.setErrorCode(400);
            advisor.setStatusError(HttpStatus.BAD_REQUEST.name());
            advisor.setMessage(ex.getMessage());
        }

        return Pair.of( cartsWrapperResponse, advisor);
    }

    public Pair<List<CartsWrapperResponse>, ResponseAdvisor> getAll(EStatusCart statusCart){
        ResponseAdvisor advisor = new ResponseAdvisor(200, "SUCCESS");
        CartsEntity carts = new CartsEntity();
        List<CartsWrapperResponse> cartsWrapperResponseList= new LinkedList<>();
        List<CartsEntity> cartsEntities = new LinkedList<>();

        try {
            switch (statusCart){
                case all -> cartsEntities= cartsRepository.findAll();
                case my_pendings -> cartsEntities = cartsRepository.findAllByUserAndPaid(userSevice.whoami().getId(),false);
                case my_paids -> cartsEntities = cartsRepository.findAllByUserAndPaid(userSevice.whoami().getId(),true);
            }


            for (CartsEntity cartsEntityItem: cartsEntities){
                cartsWrapperResponseList.add(getWrapperResponse(new CartsRequestDTO(cartsEntityItem.getProducts()) , cartsEntityItem.getUser(), cartsEntityItem.getId(), cartsEntityItem.isPaid()));
            }


        }catch (Exception ex){
            log.error(ex.getMessage());
            advisor.setErrorCode(400);
            advisor.setStatusError(HttpStatus.BAD_REQUEST.name());
            advisor.setMessage(ex.getMessage());
        }

        return Pair.of( cartsWrapperResponseList, advisor);
    }

    public Pair<CartsWrapperResponse, ResponseAdvisor> getOne(Long id){
        ResponseAdvisor advisor = new ResponseAdvisor(200, "SUCCESS");

        CartsWrapperResponse cartsWrapperResponse = new CartsWrapperResponse();
        CartsEntity cartsEntity;

        try {

            cartsEntity =  cartsRepository.findById(id);
            cartsWrapperResponse = getWrapperResponse(new CartsRequestDTO(cartsEntity.getProducts()) , cartsEntity.getUser(), cartsEntity.getId(), cartsEntity.isPaid());

        }catch (Exception ex){
            log.error(ex.getMessage());
            advisor.setErrorCode(400);
            advisor.setStatusError(HttpStatus.BAD_REQUEST.name());
            advisor.setMessage(ex.getMessage());
        }

        return Pair.of( cartsWrapperResponse, advisor);
    }


    @Transactional
    public Pair<CartsWrapperResponse, ResponseAdvisor> deleteOne(Long id){

        ResponseAdvisor advisor = new ResponseAdvisor(200, "SUCCESS");
        CartsWrapperResponse cartsWrapperResponse = new CartsWrapperResponse();

        try {
            if(!cartsRepository.existsById(id))throw new Exception("La orden no existe");
            cartsRepository.deleteById(id);
        }catch (Exception ex){
            log.error(ex.getMessage());
            advisor.setErrorCode(400);
            advisor.setStatusError(HttpStatus.BAD_REQUEST.name());
            advisor.setMessage(ex.getMessage());
        }
        return Pair.of( cartsWrapperResponse, advisor);
    }


    private Pair<Set<CartsProducDTO>, Double> getCartsProductsAndTotal(CartsRequestDTO cartsProductsEntity) throws Exception {
        Set<CartsProducDTO> cartsProducDTO = new HashSet<>();
        Double total = (double) 0;

        for (CartsProductsEntity cartsItem : cartsProductsEntity.getProducts()){

            ProductsProxy productsProxy = productsService.getOne(Math.toIntExact(cartsItem.getProductId())).getFirst();
            if(productsProxy.getId()==null)throw new Exception("El Producto con id " + cartsItem.getProductId() + " no existe.");
            Double subtotal = (double) (productsProxy.getPrice()*cartsItem.getQuantity());
            cartsProducDTO.add(
                    CartsProducDTO.builder()
                            .title(productsProxy.getTitle())
                            .price(productsProxy.getPrice())
                            .quantity(cartsItem.getQuantity())
                            .subTotal(subtotal)
                            .build()
            );
            total+= subtotal;
        }
        return Pair.of(cartsProducDTO, total);
    }

    public CartsWrapperResponse getWrapperResponse(CartsRequestDTO cartsRequestDTO, UserEntity user, Long id, boolean isPaid) throws Exception {
        var cartsProductsAndTotal= getCartsProductsAndTotal(cartsRequestDTO);
        return  CartsWrapperResponse.builder()
                .id(id)
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .isPaid(isPaid)
                .products(cartsProductsAndTotal.getFirst())
                .total(cartsProductsAndTotal.getSecond())
                .build();
    }

    public boolean updatePaid(Long id){
        CartsEntity cartsEntity;
        try {
            cartsEntity=cartsRepository.findById(id);
            cartsEntity.setPaid(true);
            cartsRepository.save(cartsEntity);
        }catch (Exception ex){
            log.error(ex.getMessage());
            return false;
        }
        return true;
    }
}
