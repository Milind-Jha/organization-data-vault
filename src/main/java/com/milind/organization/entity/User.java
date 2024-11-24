package com.milind.organization.entity;

import com.milind.organization.constants.Role;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String userName;
    private Role role;
    private String organizationId;
    private boolean noLogin;

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    public User setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public User setNoLogin(boolean noLogin) {
        this.noLogin = noLogin;
        return this;
    }
}
