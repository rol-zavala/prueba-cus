package com.pruebacus.pruebacus.models.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pruebacus.pruebacus.models.departments.DepartmentEntity;
import com.pruebacus.pruebacus.models.genders.GenderEntity;
import com.pruebacus.pruebacus.models.roles.RoleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Email
    String email;
    String username;
    String password;
    String name;
    String lastName;
    String numId;
    Integer phoneNumber;
    @JsonFormat(pattern="dd/MM/yyyy")
    Date birthday;
    @OneToOne(targetEntity = DepartmentEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "departmant_id")
    DepartmentEntity departmentId;


    @OneToOne(targetEntity = GenderEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "gender_id")
    GenderEntity genderId;

    Boolean isEnabled;
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<RoleEntity> roles;
    Double cash;
}
