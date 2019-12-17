package com.overlook.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

    @NotNull
    private UUID userId;

    @NotBlank
    private String accessToken;

//    private final String tokenType = "Bearer";

    @NotBlank
    private String username;

    @NotEmpty
    private List<String> roles;

}
