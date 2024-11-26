package com.milind.organization.controller;

import com.milind.organization.dto.OtpCredentials;
import com.milind.organization.service.OtpAutenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization")
public class OtpAuthenticatorController {

    @Autowired
    OtpAutenticationService otpAutenticationService;
    @Value("${otp.authentication.success}")
    private String otpVerified;
    @Value("${otp.authentication.fail}")
    private String otpNotVerified;

    @PostMapping("/authenticateOtp")
    public ResponseEntity<String> authenticateOtp(@RequestBody OtpCredentials otpCredentials) throws Exception {
        if(otpAutenticationService.authentiateMailwithOtp(otpCredentials))
            return ResponseEntity.ok(otpVerified);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(otpNotVerified);
    }
}
