package com.pruebacus.pruebacus.services.users;

import com.pruebacus.pruebacus.advisors.ResponseAdvisor;
import com.pruebacus.pruebacus.models.dtos.DepositRequestDTO;
import com.pruebacus.pruebacus.models.users.UserEntity;
import com.pruebacus.pruebacus.repositories.UserRepository;
import com.pruebacus.pruebacus.services.carts.CartsService;
import com.pruebacus.pruebacus.wrappers.accountMoney.AccountMoneyWrapperResponse;
import com.pruebacus.pruebacus.wrappers.carts.CartAndAccountMoneyWrapperResponse;
import com.pruebacus.pruebacus.wrappers.carts.CartsWrapperResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserSevice {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;


    public void changePassword(UserEntity user, String newPassword) {

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public UserEntity whoami(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername((String) authentication.getPrincipal()).get();
    }

}
