package com.milind.organization.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.crypto.SecretKey;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.security.KeyPair;
import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Organization {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @NotBlank
    private String organizationName;
    @NotBlank
    private String registeredMailId;
    private String password;
    private boolean mailVerified;
    private LocalDate registrationDate;
    private LocalDate lastDateOfUse;
    private KeyPair keyPair;
    private SecretKey secretKey;
}
