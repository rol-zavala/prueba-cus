package com.pruebacus.pruebacus.wrappers.products;

import com.pruebacus.pruebacus.advisors.ResponseAdvisor;
import com.pruebacus.pruebacus.models.products.ProductsProxy;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductsWrapperResponse {
    List<ProductsProxy> products;
    ResponseAdvisor responseAdvisor;
}
