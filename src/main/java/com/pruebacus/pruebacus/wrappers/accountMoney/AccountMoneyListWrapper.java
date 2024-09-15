package com.pruebacus.pruebacus.wrappers.accountMoney;

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
public class AccountMoneyListWrapper {
    List<AccountMoneyWrapperResponse> account;
    ResponseAdvisor responseAdvisor;
}