package com.springboot.studentservices.userservices;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}