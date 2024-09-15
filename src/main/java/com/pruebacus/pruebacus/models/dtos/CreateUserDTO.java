package com.pruebacus.pruebacus.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {


    @Email
    String email;
    String username;
    String password;
    Integer phoneNumber;
    Set<String> roles;
    String name;
    String lastName;
    String numId;
    Long genderId;
    Long departmentId;
    @JsonFormat(pattern="dd/MM/yyyy")
    Date birthday;



}
