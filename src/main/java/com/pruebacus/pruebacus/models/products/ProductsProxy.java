package com.pruebacus.pruebacus.models.products;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsProxy {

    Integer id;
    String title;
    Float price;
    String description;
    String category;
    String image;
    RatingProxy rating;


}
