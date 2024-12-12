package com.milind.organization.service.impl;

import com.milind.organization.dao.OrganizationDao;
import com.milind.organization.dao.OrganizationWithOtpDao;
import com.milind.organization.dto.OtpCredentials;
import com.milind.organization.entity.Organization;
import com.milind.organization.entity.OrganizationwithOtp;
import com.milind.organization.service.OtpAutenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

@Service
@Slf4j
public class OtpAutenticationServiceImpl implements OtpAutenticationService {

    @Autowired
    OrganizationWithOtpDao organizationWithOtpDao;
    @Autowired
    OrganizationDao organizationDao;
    @Override
    public boolean authentiateMailwithOtp(OtpCredentials otpCredentials) throws Exception {
        OrganizationwithOtp organizationwithOtp = organizationWithOtpDao.findById(
                otpCredentials.getOrganizationId()).orElseThrow(() -> new RuntimeException
                ("No Such organization"));
        if(organizationwithOtp.getOtp().equals(otpCredentials.getOtp())){
            Organization organization = organizationDao.findById(otpCredentials.getOrganizationId()).get();
            organization.setMailVerified(true);
            organization.setKeyPair(setRSAKeyPair());
            organization.setSecretKey(setAESCipher());
            organizationDao.save(organization);
            return true;
        }
        return false;
    }

    private SecretKey setAESCipher() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(AES_KEY_SIZE);
        SecretKey secretKey = keyGenerator.generateKey();
        log.info("Generating AES key pair "+secretKey);
        return secretKey;
    }

    private KeyPair setRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        log.info("Generating RSA key pair "+keyPair);
        return keyPair;
    }
}
