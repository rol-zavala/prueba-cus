package com.pruebacus.pruebacus.controller.lookups;

import com.pruebacus.pruebacus.services.departments.DepartmentsService;
import com.pruebacus.pruebacus.services.genders.GenderService;
import com.pruebacus.pruebacus.wrappers.departments.DepartmentsWrapperResponse;
import com.pruebacus.pruebacus.wrappers.genders.GendersWrapperResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/lookups")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LookupsController {

    @Autowired
    DepartmentsService departmentsService;

    @Autowired
    GenderService genderService;

    @GetMapping("departments/all")
    public ResponseEntity<DepartmentsWrapperResponse> getAllDepartments(){
         var departments = departmentsService.getAll();
        HttpStatus status = (!departments.getFirst().equals(null))? HttpStatus.OK : HttpStatus.BAD_REQUEST;
         return new ResponseEntity<>(new DepartmentsWrapperResponse(departments.getFirst(), departments.getSecond()), status);
    }

    @GetMapping("genders/all")
    public ResponseEntity<GendersWrapperResponse> getAllGenders(){
        var genders = genderService.getAll();
        HttpStatus status = (!genders.getFirst().equals(null))? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new GendersWrapperResponse(genders.getFirst(), genders.getSecond()), status);
    }

}
