package com.pruebacus.pruebacus.services.products;

import com.pruebacus.pruebacus.advisors.ResponseAdvisor;
import com.pruebacus.pruebacus.models.products.ProductsProxy;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductsService {
    @Value("${products.api.url}")
    String apiProductsUrl;
    public Pair<List<ProductsProxy>, ResponseAdvisor> getAll(){
        ResponseAdvisor advisor = new ResponseAdvisor(200, "SUCCESS");

        List<ProductsProxy> listProducts = null;
        try {
            WebClient webClient =  WebClient.builder().baseUrl(apiProductsUrl).build();
            Flux<ProductsProxy> products = webClient.get()
                    .uri("/products")
                    .retrieve()
                    .bodyToFlux(ProductsProxy.class);
            listProducts = products.collectList().block();

        }catch (Exception ex) {
            log.error(ex.getMessage());
            advisor.setErrorCode(500);
            advisor.setStatusError(HttpStatus.INTERNAL_SERVER_ERROR.name());
            advisor.setMessage(ex.getMessage());
        }

        return Pair.of(listProducts, advisor);
    }

    public Pair<ProductsProxy, ResponseAdvisor> getOne(Integer id){
        ResponseAdvisor advisor = new ResponseAdvisor(200, "SUCCESS");

        ProductsProxy product = null;
        try {
            WebClient webClient =  WebClient.builder().baseUrl(apiProductsUrl).build();
            Mono<ProductsProxy> products = webClient.get()
                    .uri("/products/"+id)
                    .retrieve()
                    .bodyToMono(ProductsProxy.class);
            product = products.block();

        }catch (Exception ex) {
            log.error(ex.getMessage());
            advisor.setErrorCode(500);
            advisor.setStatusError(HttpStatus.INTERNAL_SERVER_ERROR.name());
            advisor.setMessage(ex.getMessage());
        }

        if(product==null){
            product = new ProductsProxy();
            advisor.setErrorCode(404);
            advisor.setStatusError(HttpStatus.NOT_FOUND.name());
            advisor.setMessage("Producto No Encontrado");
        }
        return Pair.of(product, advisor);
    }
}
