package com.milind.organization.service;

public interface EmailService {
    public void sendSimpleMail(String organizationId,String to);
    public boolean valiDateOtp();
}
