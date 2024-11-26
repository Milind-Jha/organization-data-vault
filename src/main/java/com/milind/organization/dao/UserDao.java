package com.milind.organization.dao;

import com.milind.organization.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User,String> {
    public Optional<User> findByName(String name);
}
