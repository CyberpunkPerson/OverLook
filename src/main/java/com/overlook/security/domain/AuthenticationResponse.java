package com.overlook.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

    @NotBlank
    private String accessToken;

    @NotNull
    private UserProfile userProfile;

}
