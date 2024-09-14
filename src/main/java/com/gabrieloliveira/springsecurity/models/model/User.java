package com.gabrieloliveira.springsecurity.models.model;

import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.gabrieloliveira.springsecurity.Controller.dto.LoginRequestDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Gera o UUID automaticamente
    @Column(name = "user_id") // Nome da coluna no banco de dados
    private UUID userId;

    @Column(unique = true)
    private String username;

    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Relacionamento muitos para muitos com a tabela de roles
    @JoinTable( // Significa que é uma tabela de relacionamento
        name = "tb_users_roles", // Nome da tabela de relacionamento
        joinColumns = @JoinColumn(name = "user_id"), // Nome da coluna que faz referência a tabela atual
        inverseJoinColumns = @JoinColumn(name = "role_id") // Nome da coluna que faz referência a outra tabela
        ) 
    private Set<Role> roles;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isLoginCorrect(LoginRequestDTO loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }

}
