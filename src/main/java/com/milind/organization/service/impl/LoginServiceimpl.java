package com.milind.organization.service.impl;

import com.milind.organization.dao.OrganizationDao;
import com.milind.organization.dao.UserDao;
import com.milind.organization.entity.Organization;
import com.milind.organization.entity.User;
import com.milind.organization.service.LoginService;
import com.milind.organization.util.JwtTokenUtil;
import com.milind.organization.util.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LoginServiceimpl implements LoginService {

    @Autowired
    UserDao userDao;
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public String login(LoginRequest loginRequest) {
        // Fetch User and Organization
        List<User> all = userDao.findAll();
        System.out.println("USERS :- "+all);
        User user = userDao.findByName(loginRequest.getName())
                .orElseThrow(() -> new RuntimeException("Invalid username"));
        Organization organization = organizationDao.findById(loginRequest.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Invalid organization ID"));

        // Validate Organization's password
        if (!passwordEncoder.matches(loginRequest.getPassword(), organization.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate JWT token
        return jwtTokenUtil.generateToken(user.getName() , user.getOrganizationId());
    }

//    public String login(LoginRequest loginRequest) {
//        User user = userDao.findByUserName(
//                loginRequest.getUsername()
//        ).orElseThrow(() -> new RuntimeException("Invalid username"));
//        log.info("inside service method");
//        System.out.println("reached service layer");
//        if(user.getOrganizationId()!=loginRequest.getOrganizationId())
//            throw new RuntimeException("Invalid Credentials");
//        log.info("organization Credentials verified");
//        // Verify the password
//        Organization organization = organizationDao.findById(loginRequest.getOrganizationId())
//                .orElseThrow(() -> new RuntimeException("Invalid Id"));
//        log.info("individual Credentials verified");
//        if(passwordEncoder.encode(loginRequest.getPassword()).equals(organization.getPassword()))
//            throw new RuntimeException("Invalid credentials");
//        log.info("Password Match");
//        // Generate JWT token
//        String token = jwtTokenUtil.generateToken(
//                user.getUserName(),
//                user.getOrganizationId()
//        );
//        return jwtTokenUtil.generateToken(user.getUserName(), user.getOrganizationId());
//    }
}
