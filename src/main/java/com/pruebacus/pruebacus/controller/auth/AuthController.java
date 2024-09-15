package com.pruebacus.pruebacus.controller.auth;

import com.pruebacus.pruebacus.advisors.CreateUserValidator;
import com.pruebacus.pruebacus.advisors.ResetPasswordValidator;
import com.pruebacus.pruebacus.advisors.ResetRequestPasswordValidator;
import com.pruebacus.pruebacus.advisors.ResponseAdvisor;
import com.pruebacus.pruebacus.config.notifications.SendgridEmail;
import com.pruebacus.pruebacus.models.departments.DepartmentEntity;
import com.pruebacus.pruebacus.models.dtos.CreateUserDTO;
import com.pruebacus.pruebacus.models.dtos.PasswordRequestDTO;
import com.pruebacus.pruebacus.models.dtos.PasswordResetDTO;
import com.pruebacus.pruebacus.models.genders.GenderEntity;
import com.pruebacus.pruebacus.models.roles.ERole;
import com.pruebacus.pruebacus.models.roles.RoleEntity;
import com.pruebacus.pruebacus.models.users.ConfirmationUserTokenEntity;
import com.pruebacus.pruebacus.models.users.UserEntity;
import com.pruebacus.pruebacus.repositories.ConfirmationUserTokenRepository;
import com.pruebacus.pruebacus.repositories.DepartmentsRepository;
import com.pruebacus.pruebacus.repositories.GenderRepository;
import com.pruebacus.pruebacus.repositories.UserRepository;
import com.pruebacus.pruebacus.services.auth.PasswordResetTokenService;
import com.pruebacus.pruebacus.services.users.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CreateUserValidator createUserValidator;

    @Autowired
    private ResetPasswordValidator resetPasswordValidator;

    @Autowired
    private ResetRequestPasswordValidator resetRequestPasswordValidator;

    @Autowired
    private SendgridEmail email;

    @Value("${system.frontend-host}")
    private String frontendHost;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private UserSevice userService;

    @Autowired
    ConfirmationUserTokenRepository confirmationUserTokenRepository;

    @Autowired
    DepartmentsRepository departmentsRepository;

    @Autowired
    GenderRepository genderRepository;


    @PostMapping("/register")
    public ResponseEntity<ResponseAdvisor> createUser(@RequestBody CreateUserDTO createUserDTO) throws IOException {

        ResponseAdvisor advisor = new ResponseAdvisor(201, "Usuario Creado Exitosamente");
        var validations = createUserValidator.validate(createUserDTO);
        if (validations.getErrorCode() != 200) {
            return new ResponseEntity<>(
                    validations
                    , HttpStatus.BAD_REQUEST);
        }

        Optional<UserEntity> isValidUser = userRepository.findByUsername(createUserDTO.getUsername());
        Optional<UserEntity> isValidEmail = userRepository.findByEmail(createUserDTO.getEmail());
        DepartmentEntity department = departmentsRepository.findById(createUserDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));

        GenderEntity gender = genderRepository.findById(createUserDTO.getGenderId())
                .orElseThrow(() -> new RuntimeException("Genero no encontrado"));

        if ( isValidUser.isEmpty() && isValidEmail.isEmpty()){
            Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                    .map(role -> RoleEntity.builder()
                            .name(ERole.valueOf(role))
                            .build())
                    .collect(Collectors.toSet());

            UserEntity userEntity = UserEntity.builder()
                    .email(createUserDTO.getEmail())
                    .username(createUserDTO.getUsername())
                    .password(passwordEncoder.encode(createUserDTO.getPassword()))
                    .phoneNumber(createUserDTO.getPhoneNumber())
                    .roles(roles)
                    .name(createUserDTO.getName())
                    .lastName(createUserDTO.getLastName())
                    .numId(createUserDTO.getNumId())
                    .genderId(gender)
                    .departmentId(department)
                    .birthday(createUserDTO.getBirthday())
                    .isEnabled(false)
                    .cash((double) 0)

                    .build();

            userRepository.save(userEntity);

            ConfirmationUserTokenEntity confirmationUserToken = new ConfirmationUserTokenEntity(userEntity);
            confirmationUserTokenRepository.save(confirmationUserToken);


            String message = "Para completar tu registro en Prueba Cus, haz clic en el siguiente enlace: "
                    .concat(frontendHost)
                    .concat("/auth/confirm-account?token=")
                    .concat(confirmationUserToken.getConfirmationToken());
            email.sendEmail(
                    "Completa tu Registro - Prueba Cus ",
                    createUserDTO.getEmail(),
                    message
            );
            return new ResponseEntity<>(
                    advisor
                    , HttpStatus.OK);
        }

        advisor.setErrorCode(400);
        advisor.setStatusError(HttpStatus.CONFLICT.name());
        advisor.setMessage("Correo y/o Contraseña Invalidos");

        return new ResponseEntity<>(
                advisor
                , HttpStatus.BAD_REQUEST);



    }

    @GetMapping("/confirm-account")
    public ResponseEntity<ResponseAdvisor> registerUser(@RequestParam("token")String confirmationToken){
        ConfirmationUserTokenEntity token = confirmationUserTokenRepository.findByConfirmationToken(confirmationToken);
        ResponseAdvisor advisor = new ResponseAdvisor(200, "Usuario Validado Exitosamente");

        if(token != null)
        {
            UserEntity user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setIsEnabled(true);
            userRepository.save(user);
        }
        else
        {
            advisor.setErrorCode(400);
            advisor.setStatusError(HttpStatus.CONFLICT.name());
            advisor.setMessage("Token Invalido");

            return new ResponseEntity<>(
                    advisor
                    , HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                advisor
                , HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));

        return "Se borro el usuario con id ".concat(id);
    }
    @PostMapping("/password-reset-request")
    public ResponseEntity<ResponseAdvisor> resetPasswordRequest(@RequestBody PasswordRequestDTO dto) throws IOException {

        ResponseAdvisor advisor = new ResponseAdvisor(200, "Cambio de contraseña exitoso");
        var validations = resetRequestPasswordValidator.validate(dto);
        if (validations.getErrorCode() != 200) {
            return new ResponseEntity<>(
                    validations
                    , HttpStatus.BAD_REQUEST);
        }
        Optional<UserEntity> user = userRepository.findByEmail(dto.getEmail());
        String passwordResetUrl = "";
        if (user.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            passwordResetTokenService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
            String message = "Para completar tu cambio de contraseña en Prueba Cus, haz clic en el siguiente enlace: "
                    .concat(frontendHost)
                    .concat("/auth/reset-password?token=")
                    .concat(passwordResetToken);
            email.sendEmail(
                    "Cambio de Contraseña - Prueba Cus ",
                    dto.getEmail(),
                    message
            );
            return new ResponseEntity<>(
                    advisor
                    , HttpStatus.OK);
        }
        advisor.setErrorCode(400);
        advisor.setMessage("Usuario no encontrado");
        advisor.setStatusError(HttpStatus.CONFLICT.name());
        return new ResponseEntity<>(
                advisor,
                HttpStatus.BAD_REQUEST
        );
    }
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseAdvisor> resetPassword(@RequestBody PasswordResetDTO dto,
                                @RequestParam("token") String token){
        ResponseAdvisor advisor = new ResponseAdvisor(200, "Cambio de contraseña exitoso");
        if(token.isEmpty()){
            advisor.setErrorCode(400);
            advisor.setMessage("Se requiere token de Acceso");
            return new ResponseEntity<>(
                    advisor
                    , HttpStatus.BAD_REQUEST);
        }
        var validations = resetPasswordValidator.validate(dto);
        if (validations.getErrorCode() != 200 ) {
            return new ResponseEntity<>(
                    validations
                    , HttpStatus.BAD_REQUEST);
        }
        var tokenVerificationResult = passwordResetTokenService.validatePasswordResetToken(token);

        UserEntity user = tokenVerificationResult.getSecond();
        if (user.getUsername() != null) {
            userService.changePassword(user, dto.getNewPassword());
            return new ResponseEntity<>(
                    advisor
                    ,HttpStatus.OK);
        }
        advisor = tokenVerificationResult.getFirst();

        return new ResponseEntity<>(
             advisor
             , HttpStatus.BAD_REQUEST);
    }




}
