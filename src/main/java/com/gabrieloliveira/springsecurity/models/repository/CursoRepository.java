package com.gabrieloliveira.springsecurity.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabrieloliveira.springsecurity.models.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

}
