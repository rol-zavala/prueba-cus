package com.pruebacus.pruebacus.models.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepositRequestDTO {
    Double cash;
}
