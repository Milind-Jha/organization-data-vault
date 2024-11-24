package com.milind.organization.service;

import com.milind.organization.dto.OrganizationCredentials;
import com.milind.organization.response.OrganisationDetails;

public interface OrganizationRegister {
    public OrganisationDetails registerOrganization(OrganizationCredentials credentials);
}
