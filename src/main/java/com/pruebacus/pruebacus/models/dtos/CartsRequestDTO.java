package com.pruebacus.pruebacus.models.dtos;

import com.pruebacus.pruebacus.models.carts.CartsProductsEntity;
import com.pruebacus.pruebacus.models.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartsRequestDTO {

    Set<CartsProductsEntity> products;

}
