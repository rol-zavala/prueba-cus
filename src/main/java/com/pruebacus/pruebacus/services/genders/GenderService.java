package com.pruebacus.pruebacus.services.genders;

import com.pruebacus.pruebacus.advisors.ResponseAdvisor;
import com.pruebacus.pruebacus.models.genders.GenderEntity;
import com.pruebacus.pruebacus.repositories.GenderRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GenderService {

    @Autowired
    GenderRepository genderRepository;

    public Pair<List<GenderEntity>, ResponseAdvisor> getAll(){
        ResponseAdvisor advisor = new ResponseAdvisor(200, "SUCCESS");

        List<GenderEntity> genders = null;
        try {
            genders = genderRepository.findAll();

        }catch (Exception ex) {
            log.error(ex.getMessage());
            advisor.setErrorCode(500);
            advisor.setStatusError(HttpStatus.INTERNAL_SERVER_ERROR.name());
            advisor.setMessage(ex.getMessage());
        }

        return Pair.of(genders, advisor);
    }
}
