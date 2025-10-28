package com.javabackend.controller.request;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class UserPasswordRequest implements Serializable {
    private Long id;
    private String password;
    private String confirmPassword;
}
