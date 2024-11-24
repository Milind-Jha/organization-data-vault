package com.milind.organization.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationDetails {
    private String id;
    private String name;
    private String registeredMailId;
    private boolean mailVerified;
    private LocalDate registrationDate;
}
