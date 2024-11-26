package com.milind.organization.service;

import com.milind.organization.dto.OtpCredentials;

public interface OtpAutenticationService {
    String RSA_ALGORITHM = "RSA";
    String AES_ALGORITHM = "AES";
    int AES_KEY_SIZE = 256;
    public boolean authentiateMailwithOtp(OtpCredentials otpCredentials) throws Exception;
}
