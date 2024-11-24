package com.milind.organization.dao;

import com.milind.organization.entity.Organization;
import com.milind.organization.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationDao extends JpaRepository<Organization,String>{
    public Optional<Organization> findByOrganizationName(String organizationName);
}
