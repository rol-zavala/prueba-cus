package com.pruebacus.pruebacus.wrappers.accountMoney;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountMoneyWrapperResponse {
    String username;
    Double cash;
}
