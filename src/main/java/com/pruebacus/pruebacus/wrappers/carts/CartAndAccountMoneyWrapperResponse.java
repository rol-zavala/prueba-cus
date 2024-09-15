package com.pruebacus.pruebacus.wrappers.carts;

import com.pruebacus.pruebacus.wrappers.accountMoney.AccountMoneyWrapperResponse;
import lombok.*;

@Data
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartAndAccountMoneyWrapperResponse {
    CartsWrapperResponse order;
    AccountMoneyWrapperResponse statusAccount;
}
