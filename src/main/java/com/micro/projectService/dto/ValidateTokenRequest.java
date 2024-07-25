package com.micro.projectService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenRequest {private String token;

    public ValidateTokenRequest(String token) {
        this.token = token;
    }
}
