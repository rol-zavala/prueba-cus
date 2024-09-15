package com.pruebacus.pruebacus.wrappers.genders;

import com.pruebacus.pruebacus.advisors.ResponseAdvisor;
import com.pruebacus.pruebacus.models.genders.GenderEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GendersWrapperResponse {
    List<GenderEntity> data;
    ResponseAdvisor responseAdvisor;
}
