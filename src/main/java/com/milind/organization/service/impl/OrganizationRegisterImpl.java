package com.milind.organization.service.impl;

import com.milind.organization.constants.Role;
import com.milind.organization.dao.OrganizationDao;
import com.milind.organization.dao.UserDao;
import com.milind.organization.dto.OrganizationCredentials;
import com.milind.organization.entity.Organization;
import com.milind.organization.entity.User;
import com.milind.organization.response.OrganisationDetails;
import com.milind.organization.service.OrganizationRegister;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class OrganizationRegisterImpl implements OrganizationRegister {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    UserDao userDao;

    public OrganisationDetails registerOrganization(OrganizationCredentials credentials){
        if (!credentials.getPassword().equals(credentials.getRetypePassword())) {
            log.info("Passwords do not match");
            throw new RuntimeException("Passwords do not match");
        } else {
            Organization saveOrg = modelMapper.map(credentials, Organization.class);
            saveOrg.setRegistrationDate(LocalDate.now());
            saveOrg.setPassword(passwordEncoder.encode(saveOrg.getPassword()));
            Organization savedOrganization = organizationDao.save(saveOrg);
            log.info("Saved organization: {}", savedOrganization);
            OrganisationDetails response = modelMapper.map(savedOrganization, OrganisationDetails.class);
            User user = new User().setName("DEFAULT").setOrganizationId(savedOrganization.getId()).
                    setId(UUID.randomUUID().toString()).setRole(Role.ADMIN).setNoLogin(true);
            userDao.save(user);
            return response;
        }
    }
}
