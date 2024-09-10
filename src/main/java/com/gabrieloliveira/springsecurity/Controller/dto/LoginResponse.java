package com.gabrieloliveira.springsecurity.Controller.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
    
}
