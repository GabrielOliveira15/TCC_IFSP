package com.gabrieloliveira.springsecurity.models.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera o ID automaticamente
    @Column(name = "role_id") // Nome da coluna no banco de dados
    private Long roleId;

    private String roleName;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    // Tipo do perfil
    public enum Values {
        ROLE_USER(2L), // ROLE_USER
        ROLE_ADMIN(1L); // ROLE_ADMIN

        long role_id; // ID da role

        Values(long role_id) { // Construtor
            this.role_id = role_id;
        }

        public long getRole_id() { // Getter da role
            return role_id;
        }
    }
}
