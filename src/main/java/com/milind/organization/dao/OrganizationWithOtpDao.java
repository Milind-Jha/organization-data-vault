package com.milind.organization.dao;

import com.milind.organization.entity.OrganizationwithOtp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationWithOtpDao extends JpaRepository<OrganizationwithOtp,String> {
}
