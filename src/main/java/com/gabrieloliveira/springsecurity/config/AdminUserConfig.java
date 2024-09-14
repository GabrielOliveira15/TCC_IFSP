package com.gabrieloliveira.springsecurity.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.gabrieloliveira.springsecurity.models.model.Role;
import com.gabrieloliveira.springsecurity.models.model.User;
import com.gabrieloliveira.springsecurity.models.repository.RoleRepository;
import com.gabrieloliveira.springsecurity.models.repository.UserRepository;

@Configuration
public class AdminUserConfig implements CommandLineRunner{ // Classe para criar um usuário admin quando for iniciado a aplicação, mas como já tenho criado, não precisa rodar

    @SuppressWarnings("unused")
    private RoleRepository roleRepository;

    @SuppressWarnings("unused")
    private UserRepository userRepository;

    @SuppressWarnings("unused")
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        /*
        var roleAdmin = roleRepository.findByRoleName(Role.Values.ROLE_ADMIN.name());

        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
            (user) -> {
                System.out.println("Usuário admin já existe");
            },
            () -> {
                var user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("123"));
                user.setRoles(Set.of(roleAdmin));
                userRepository.save(user);
            }
        ); 
        */
    }
     
}
