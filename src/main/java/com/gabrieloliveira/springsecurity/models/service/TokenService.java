package com.gabrieloliveira.springsecurity.models.service;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.gabrieloliveira.springsecurity.Controller.dto.LoginResponseDTO;
import com.gabrieloliveira.springsecurity.models.model.Role;
import com.gabrieloliveira.springsecurity.models.model.User;

@Service
public class TokenService {
    
    private final JwtEncoder jwtEncoder;

    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public LoginResponseDTO createToken(Optional<User> user) { // Método para criar o token   

        var now = Instant.now();
        var expiresIn = 300L; // 5 minutos

        var scopes = user.get().getRoles()
            .stream()
            .map(Role::getRoleName)
            .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
            .issuer("MyBackend") // Quem emitiu o token
            .subject(user.get().getUsername()) // Quem é o dono do token
            .expiresAt(now.plusSeconds(expiresIn)) // Quando o token expira
            .issuedAt(now) // Quando o token foi emitido
            .claim("scope", scopes) // O que o token permite fazer
            .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(); // Gera o token

        return new LoginResponseDTO(jwtValue, expiresIn);
    }
}
