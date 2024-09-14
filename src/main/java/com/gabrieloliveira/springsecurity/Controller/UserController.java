package com.gabrieloliveira.springsecurity.Controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gabrieloliveira.springsecurity.Controller.dto.CreateUserDTO;
import com.gabrieloliveira.springsecurity.Controller.dto.LoginRequestDTO;
import com.gabrieloliveira.springsecurity.Controller.dto.LoginResponseDTO;
import com.gabrieloliveira.springsecurity.models.model.Role;
import com.gabrieloliveira.springsecurity.models.model.User;
import com.gabrieloliveira.springsecurity.models.repository.RoleRepository;
import com.gabrieloliveira.springsecurity.models.repository.UserRepository;
import com.gabrieloliveira.springsecurity.models.service.TokenService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
public class UserController {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;

    public UserController(UserRepository userRepository , RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional
    @PostMapping("/cadastro-usuario") // Rota para cadastro de usuário com ROLE_USER
    public ResponseEntity<Void> cadastroUsuario(@RequestBody CreateUserDTO createUser) throws Exception {     
        
        var basicRole = roleRepository.findByRoleName(Role.Values.ROLE_USER.name()); // Busca a ROLE_USER

        var userFromDb = userRepository.findByUsername(createUser.username()); // Verifica se o usuário já existe
        if (userFromDb.isPresent()) {
            throw new Exception(new ResponseStatusException(HttpStatus.CONFLICT, "Usuário já cadastrado"));
        }

        var user = new User();
        user.setUsername(createUser.username());
        user.setPassword(bCryptPasswordEncoder.encode(createUser.password()));
        user.setRoles(Set.of(basicRole));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @PostMapping("/login") // Rota para login e criação de token
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {

        var user = userRepository.findByUsername(loginRequest.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder) ) {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }

        LoginResponseDTO loginResponse = tokenService.createToken(user);

        return ResponseEntity.ok(loginResponse);
    }
    
    @GetMapping("/listar-usuarios") // Listagem de usuários somente ROLE_ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')") // Permissão somente para ROLE_ADMIN
    public ResponseEntity<Iterable<User>> listarUsuarios() {
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
