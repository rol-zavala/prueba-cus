package com.pruebacus.pruebacus.wrappers.accountMoney;

import com.pruebacus.pruebacus.advisors.ResponseAdvisor;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountMoneyWrapper {
    AccountMoneyWrapperResponse account;
    ResponseAdvisor responseAdvisor;
}
