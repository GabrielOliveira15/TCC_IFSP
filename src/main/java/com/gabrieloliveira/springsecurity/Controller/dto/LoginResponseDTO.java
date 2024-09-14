package com.gabrieloliveira.springsecurity.Controller.dto;

public record LoginResponseDTO(String accessToken, Long expiresIn) {
    
}
