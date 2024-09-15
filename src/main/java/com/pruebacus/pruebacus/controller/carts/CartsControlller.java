package com.pruebacus.pruebacus.controller.carts;

import com.pruebacus.pruebacus.models.carts.EStatusCart;
import com.pruebacus.pruebacus.models.dtos.CartsRequestDTO;
import com.pruebacus.pruebacus.services.carts.CartsService;
import com.pruebacus.pruebacus.wrappers.carts.CartsListWrapperResponse;
import com.pruebacus.pruebacus.wrappers.carts.CartsWrapper;
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
@RequestMapping("/carts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartsControlller {
    @Autowired
    CartsService cartsService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartsWrapper> save(@RequestBody CartsRequestDTO cartsProductsEntities){

        var carts = cartsService.save(cartsProductsEntities);
        HttpStatus status = (carts.getSecond().getStatusError().equals("SUCCESS"))? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CartsWrapper(carts.getFirst(), carts.getSecond()),status);
    }

    @GetMapping("/all/{filter}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartsListWrapperResponse> getAll(@PathVariable("filter") EStatusCart statusCart){


        var carts = cartsService.getAll(statusCart);
        HttpStatus status = (carts.getSecond().getStatusError().equals("SUCCESS"))? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CartsListWrapperResponse(carts.getFirst(), carts.getSecond()),status);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartsWrapper> getOne(@PathVariable("id") Long id){

        var carts = cartsService.getOne(id);
        HttpStatus status = (carts.getSecond().getStatusError().equals("SUCCESS"))? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CartsWrapper(carts.getFirst(), carts.getSecond()),status);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartsWrapper> deleteOne(@PathVariable("id") Long id){

        var carts = cartsService.deleteOne(id);
        HttpStatus status = (carts.getSecond().getStatusError().equals("SUCCESS"))? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CartsWrapper(carts.getFirst(), carts.getSecond()),status);
    }
}


