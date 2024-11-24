package com.milind.organization.util;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {
    private String username;
    private String organizationId;
    private String password;
}
