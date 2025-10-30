package com.javabackend.controller.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SignInRequest implements Serializable {
    private String username;
    private String password;
    private String platform; // web, mobile, miniapp
    private String deviceToken;
    private String versionApp;
}
