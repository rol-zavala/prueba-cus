package com.pruebacus.pruebacus.advisors;

import com.pruebacus.pruebacus.models.dtos.PasswordRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ResetRequestPasswordValidator implements AdvisorValidator<PasswordRequestDTO, ResponseAdvisor>{

    @Override
    public ResponseAdvisor validate(PasswordRequestDTO dto) {
        ResponseAdvisor advisor = new ResponseAdvisor(200, "SUCCESS");
        if (dto == null) {
            advisor.setErrorCode(400);
            advisor.setStatusError(HttpStatus.BAD_REQUEST.name());
            advisor.setMessage("El objeto login no puede ser nulo");
        }

        if (dto.getEmail().isEmpty()) {
            advisor.setErrorCode(400);
            advisor.setStatusError(HttpStatus.BAD_REQUEST.name());
            advisor.setMessage("El Correo no puede estar vacio");
        }
        if (dto.getEmail().length()>80) {
            advisor.setErrorCode(400);
            advisor.setStatusError(HttpStatus.BAD_REQUEST.name());
            advisor.setMessage("El Correo no puede tener mas de 80 Caracteres");
        }
        if (!dto.getEmail().matches("[A-Za-z0-9+_.-]+@[a-z]+\\.[a-z]{2,6}")) {
            advisor.setErrorCode(400);
            advisor.setStatusError(HttpStatus.BAD_REQUEST.name());
            advisor.setMessage("Formato de Correo Incorrecto");
        }

        return advisor;

    }
}
