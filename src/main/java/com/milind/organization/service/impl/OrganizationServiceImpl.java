package com.milind.organization.service.impl;

import com.milind.organization.dao.OrganizationDao;
import com.milind.organization.dto.OrganizationCredentials;
import com.milind.organization.entity.Organization;
import com.milind.organization.response.OrganisationDetails;
import com.milind.organization.service.OrganizationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public OrganisationDetails getDetails(String id) {
        Organization byId = organizationDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        return modelMapper.map(byId,OrganisationDetails.class);
    }
}
