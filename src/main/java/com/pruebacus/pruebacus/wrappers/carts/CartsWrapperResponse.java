package com.pruebacus.pruebacus.wrappers.carts;

import com.pruebacus.pruebacus.models.dtos.CartsProducDTO;
import com.pruebacus.pruebacus.models.products.ProductsProxy;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartsWrapperResponse {
    Long id;
    String username;
    String email;
    String name;
    Set<CartsProducDTO> products;
    Double total;
    boolean isPaid;
}
