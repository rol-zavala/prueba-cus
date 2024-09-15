package com.pruebacus.pruebacus.services.auth;

import com.pruebacus.pruebacus.advisors.ResponseAdvisor;
import com.pruebacus.pruebacus.models.auth.PasswordResetToken;
import com.pruebacus.pruebacus.models.users.UserEntity;
import com.pruebacus.pruebacus.repositories.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;


    public void createPasswordResetTokenForUser(UserEntity user, String passwordToken) {
        PasswordResetToken passwordRestToken = new PasswordResetToken(passwordToken, user);
        passwordResetTokenRepository.save(passwordRestToken);
    }

    public Pair<ResponseAdvisor,UserEntity> validatePasswordResetToken(String passwordResetToken) {
        ResponseAdvisor advisor = new ResponseAdvisor(200, "Usuario Creado Exitosamente");

        PasswordResetToken passwordToken = passwordResetTokenRepository.findByToken(passwordResetToken);
        UserEntity user = passwordToken.getUser();
        Calendar calendar = Calendar.getInstance();

        System.out.println(passwordToken.getExpirationTime().getTime()-calendar.getTime().getTime());
        if ((passwordToken.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){

            advisor.setErrorCode(400);
            advisor.setStatusError(HttpStatus.CONFLICT.name());
            advisor.setMessage("Tiempo Expirado, vuelve a intentarlo");
            return Pair.of(advisor, new UserEntity());
        }
        return Pair.of(advisor, user);
    }
    public Optional<UserEntity> findUserByPasswordToken(String passwordResetToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordResetToken).getUser());
    }

    public PasswordResetToken findPasswordResetToken(String token){
        return passwordResetTokenRepository.findByToken(token);
    }
}
