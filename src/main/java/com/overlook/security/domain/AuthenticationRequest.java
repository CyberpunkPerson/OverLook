package com.overlook.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "Username should be defined")
    @Size(min = 5, max = 50)
    private String username;

    @NotBlank(message = "Password should be defined")
    @Size(min = 5, max = 50)
    private String password;
}
