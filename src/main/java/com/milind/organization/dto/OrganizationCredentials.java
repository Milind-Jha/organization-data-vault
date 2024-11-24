package com.milind.organization.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrganizationCredentials {
    private String name;
    private String registeredMailId;
    private String password;
    private String retypePassword;
}
