package com.gabrieloliveira.springsecurity.Controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gabrieloliveira.springsecurity.Controller.dto.CreateUser;
import com.gabrieloliveira.springsecurity.models.model.Role;
import com.gabrieloliveira.springsecurity.models.model.User;
import com.gabrieloliveira.springsecurity.models.repository.RoleRepository;
import com.gabrieloliveira.springsecurity.models.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
public class UserController {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository , RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @Transactional
    @PostMapping("/cadastro-usuario") // Rota para cadastro de usuário
    public ResponseEntity<Void> cadastroUsuario(@RequestBody CreateUser createUser) throws Exception {     
        
        var basicRole = roleRepository.findByRoleName(Role.Values.ROLE_USER.name());

        var userFromDb = userRepository.findByUsername(createUser.username());
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
    
    @GetMapping("/listar-usuarios") // Listagem de usuários somente ROLE_ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')") // Permissão somente para ROLE_ADMIN
    public ResponseEntity<Iterable<User>> listarUsuarios() {
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/enviar-email") // Enviar email
    public String EnviarEmail() {
        
        return "SMS enviado com sucesso!";
    }
    
    
}
