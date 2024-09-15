package com.pruebacus.pruebacus.advisors;

import com.pruebacus.pruebacus.models.dtos.CreateUserDTO;
import com.pruebacus.pruebacus.repositories.DepartmentsRepository;
import com.pruebacus.pruebacus.repositories.GenderRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserValidator implements AdvisorValidator<CreateUserDTO, ResponseAdvisor>{

    @Autowired
    GenderRepository genderRepository;

    @Autowired
    DepartmentsRepository departmentsRepository;

    @Override
    public ResponseAdvisor validate(CreateUserDTO dto) {


        ResponseAdvisor advisor = new ResponseAdvisor(200, "SUCCESS");
        //Validaciones del Objeto
        if (dto == null) advisor.setMessage("El objeto login no puede ser nulo");

        //Validaciones Email
        if (dto.getEmail()==null || dto.getEmail()=="")
        {
            advisor.setMessage("El Correo no puede estar vacio");
        } else if(dto.getEmail().length()>80) {
            advisor.setMessage("El Correo no puede tener mas de 80 Caracteres");
        } else if (!dto.getEmail().matches("[A-Za-z0-9+_.-]+@[a-z]+\\.[a-z]{2,6}")){
            advisor.setMessage("Formato de Correo Incorrecto");
        }

        //Validaciones del Username
        if (dto.getUsername() == null || dto.getUsername()==""){
            advisor.setMessage("El Usuario no puede ser nulo");
        } else if (dto.getUsername().length()<4 || dto.getUsername().length()>8) {
            advisor.setMessage("El Usuario debe tener entre 4 a 8 caracteres");
        }

        //Validaciones del Name
        if (dto.getName() == null || dto.getName()==""){
            advisor.setMessage("El Nombre no puede ser nulo");
        } else if (dto.getName().length()<4 || dto.getName().length()>30) {
            advisor.setMessage("El Nombre debe tener entre 4 a 30 caracteres");
        }

        //Validaciones del Apellido
        if (dto.getLastName() == null || dto.getLastName()==""){
            advisor.setMessage("El Apellido no puede ser nulo");
        } else if (dto.getLastName().length()<4 || dto.getLastName().length()>30) {
            advisor.setMessage("El Apellido debe tener entre 4 a 30 caracteres");
        }

        //Validaciones del Password
        if (dto.getPassword() == null || dto.getPassword()=="") {
            advisor.setMessage("La Contraseña no puede ser nula");
        } else if
            (dto.getPassword().length()<4 || dto.getPassword().length()>30) {
            advisor.setMessage("La Contraseña debe tener entre 4 a 30 caracteres");
        }

        //Validaciones del Genero
        if (dto.getGenderId()==null)
        {
            advisor.setMessage("El Genero no puede estar vacio");
        } else if(!genderRepository.existsGenderEntitiesById(dto.getGenderId())) {
            advisor.setMessage("Error en Campo de genero");
        }

        //Validaciones del Departamento
        if (dto.getDepartmentId()==null)
        {
            advisor.setMessage("El Departamento no puede estar vacio");
        } else if(!departmentsRepository.existsById(dto.getDepartmentId())) {
            advisor.setMessage("Error en Campo de Departamento");
        }

        if (!advisor.getErrorMessages().isEmpty()){
            advisor.setErrorCode(400);
            advisor.setStatusError(HttpStatus.BAD_REQUEST.name());
        }

        //Validaciones del Username
        if (dto.getPhoneNumber() == null){
            advisor.setMessage("El Numero de telefon no puede ser nulo");
        } else if (dto.getPhoneNumber()<30000000 || dto.getUsername().length()>99999999) {
            advisor.setMessage("El Usuario debe ser un numero de red Tigo o Claro");
        }
        //Validaciones del Numero de Identidad
        if (dto.getNumId() == null){
            advisor.setMessage("El Numero de Identidad no puede ser nulo");
        } else if (dto.getPhoneNumber()<12 || dto.getUsername().length()>13) {
            advisor.setMessage("El Numero de identidad debe colocarse sin espacios ni guiones y constar de 13 digitos");
        }
        if (dto.getBirthday() == null){
            advisor.setMessage("La fecha no puede ser nula");
        }
            return advisor;
    }
}

