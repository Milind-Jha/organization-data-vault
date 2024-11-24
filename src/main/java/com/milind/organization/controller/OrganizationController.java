package com.milind.organization.controller;

import com.milind.organization.dao.UserDao;
import com.milind.organization.dto.OrganizationCredentials;
import com.milind.organization.entity.User;
import com.milind.organization.response.OrganisationDetails;
import com.milind.organization.service.LoginService;
import com.milind.organization.service.OrganizationService;
import com.milind.organization.service.impl.OrganizationRegisterImpl;
import com.milind.organization.util.LoginRequest;
import com.milind.organization.util.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
@CrossOrigin
@Slf4j
public class OrganizationController {


    @Autowired
    OrganizationRegisterImpl organizationRegister;
    @Autowired
    LoginService loginService;
    @Autowired
    OrganizationService organizationService;

    @PostMapping("/registerOrganization")
    public ResponseEntity<OrganisationDetails> registerOrganization(@RequestBody OrganizationCredentials organizationCredentials){
        OrganisationDetails organisationDetails = organizationRegister.registerOrganization(organizationCredentials);
        return ResponseEntity.status(HttpStatus.CREATED).body((organisationDetails));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("reached");
        try {
            String token = loginService.login(loginRequest);
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (RuntimeException ex) {
            log.error("Login failed: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<OrganisationDetails> getDetails(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(organizationService.getDetails(id));
    }
}
