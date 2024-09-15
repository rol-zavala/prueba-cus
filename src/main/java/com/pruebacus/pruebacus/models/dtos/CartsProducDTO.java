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
public class CartsProducDTO {

    String title;
    Float price;
    Long quantity;

    Double subTotal;
}
