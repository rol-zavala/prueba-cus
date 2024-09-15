package com.pruebacus.pruebacus.controller.AccountMoney;

import com.pruebacus.pruebacus.models.dtos.DepositRequestDTO;
import com.pruebacus.pruebacus.services.AccountMoneyService.AccountMoneyService;
import com.pruebacus.pruebacus.wrappers.accountMoney.AccountMoneyListWrapper;
import com.pruebacus.pruebacus.wrappers.accountMoney.AccountMoneyWrapper;
import com.pruebacus.pruebacus.wrappers.carts.CartsAndAccountMoneyWrapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/account-money")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountMoneyController {
    @Autowired
    AccountMoneyService accountMoneyService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AccountMoneyWrapper> addMoney(@RequestBody DepositRequestDTO depositRequestDTO){

        var accounts = accountMoneyService.addDeposit(depositRequestDTO);
        HttpStatus status = (accounts.getSecond().getStatusError().equals("SUCCESS"))? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new AccountMoneyWrapper(accounts.getFirst(), accounts.getSecond()),status);
    }
    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AccountMoneyWrapper> myAccountMoney(){

        var accounts = accountMoneyService.getMyAccountMoney();
        HttpStatus status = (accounts.getSecond().getStatusError().equals("SUCCESS"))? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new AccountMoneyWrapper(accounts.getFirst(), accounts.getSecond()),status);
    }

    @GetMapping("/data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountMoneyListWrapper> allAccountMoney(){

        var accounts = accountMoneyService.getAllAccountMoney();
        HttpStatus status = (accounts.getSecond().getStatusError().equals("SUCCESS"))? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new AccountMoneyListWrapper(accounts.getFirst(), accounts.getSecond()),status);
    }

    @PutMapping("/pay/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartsAndAccountMoneyWrapper> payCart(@PathVariable("id") Long id) throws Exception {
        var accounts = accountMoneyService.payCart(id);
        HttpStatus status = (accounts.getSecond().getStatusError().equals("SUCCESS"))? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CartsAndAccountMoneyWrapper(accounts.getFirst(), accounts.getSecond()),status);
    }




}
