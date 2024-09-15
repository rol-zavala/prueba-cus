package com.pruebacus.pruebacus.wrappers.carts;

import com.pruebacus.pruebacus.advisors.ResponseAdvisor;
import lombok.*;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartsAndAccountMoneyWrapper {
    CartAndAccountMoneyWrapperResponse data;
    ResponseAdvisor responseAdvisor;
}
