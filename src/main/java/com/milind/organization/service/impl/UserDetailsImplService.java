package com.milind.organization.service.impl;

import com.milind.organization.dao.OrganizationDao;
import com.milind.organization.dao.UserDao;
import com.milind.organization.entity.Organization;
import com.milind.organization.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImplService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrganizationDao organizationDao;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        // Split username to retrieve organizationId (if required by your design)
        String[] parts = name.split(":");
        for(String nameElement:parts){
            System.out.println(nameElement+"\n");
        }
        if (parts.length != 2) {
            throw new UsernameNotFoundException("Invalid username format. Use 'username:organizationId'");
        }
        String nameFound = parts[0];
        String organizationId = parts[1];
        // Fetch User and Organization
        User user = userDao.findByName(nameFound)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + nameFound));
        Organization organization = organizationDao.findById(organizationId)
                .orElseThrow(() -> new UsernameNotFoundException("Organization not found with ID: " + organizationId));

        // Return UserDetails object (Spring Security requires a password, provide org's password here)
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getName() + ":" + user.getOrganizationId()) // Include orgId for clarity
                .password(organization.getPassword()) // Organization password
                .roles(user.getRole().name()) // Use User's role for security
                .build();
    }
}
