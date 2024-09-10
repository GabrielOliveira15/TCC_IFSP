package com.gabrieloliveira.springsecurity.Controller;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.RestController;

import com.gabrieloliveira.springsecurity.Controller.dto.LoginRequest;
import com.gabrieloliveira.springsecurity.Controller.dto.LoginResponse;
import com.gabrieloliveira.springsecurity.models.model.Role;
import com.gabrieloliveira.springsecurity.models.repository.UserRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class TokenController {
    
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login") // Rota para login e criação de token
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        
        var user = userRepository.findByUsername(loginRequest.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder) ) {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }
        

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

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }
    
}
