package com.pruebacus.pruebacus.controller.products;

import com.pruebacus.pruebacus.services.products.ProductsService;
import com.pruebacus.pruebacus.wrappers.products.ProductWrapperResponse;
import com.pruebacus.pruebacus.wrappers.products.ProductsWrapperResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/products")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ProductsController {


    @Autowired
    ProductsService productsService;

    @GetMapping("/all")
    public ResponseEntity<ProductsWrapperResponse> getAllProducts(){
        var products = productsService.getAll();
        HttpStatus status = (!products.getFirst().equals(null))? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ProductsWrapperResponse(products.getFirst(), products.getSecond()), status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductWrapperResponse> getOneProduct(@PathVariable("id") Integer id){
        var products = productsService.getOne(id);
        HttpStatus status = (!products.getFirst().equals(null))? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ProductWrapperResponse(products.getFirst(), products.getSecond()), status);
    }





}
