package com.milind.organization.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequest {
    private String name;
    private String organizationId;
    private String password;
}
