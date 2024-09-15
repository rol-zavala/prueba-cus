package com.pruebacus.pruebacus.wrappers.departments;

import com.pruebacus.pruebacus.advisors.ResponseAdvisor;
import com.pruebacus.pruebacus.models.departments.DepartmentEntity;
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
public class DepartmentsWrapperResponse {
    List<DepartmentEntity> data;
    ResponseAdvisor responseAdvisor;
}
