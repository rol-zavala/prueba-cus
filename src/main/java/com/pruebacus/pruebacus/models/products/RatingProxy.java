package com.pruebacus.pruebacus.models.products;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingProxy {
    Float rate;
    Integer count;
}
