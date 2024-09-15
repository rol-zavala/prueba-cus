package com.pruebacus.pruebacus.wrappers.carts;

import com.pruebacus.pruebacus.advisors.ResponseAdvisor;
import com.pruebacus.pruebacus.models.carts.CartsEntity;
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
public class CartsWrapper {
    CartsWrapperResponse order;
    ResponseAdvisor responseAdvisor;
}
