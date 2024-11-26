package com.milind.organization.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OtpCredentials {
    String otp;
    String organizationId;
}
