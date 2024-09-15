package com.pruebacus.pruebacus.wrappers.carts;

import com.pruebacus.pruebacus.advisors.ResponseAdvisor;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartsListWrapperResponse {
    List<CartsWrapperResponse> orders;
    ResponseAdvisor responseAdvisor;
}
