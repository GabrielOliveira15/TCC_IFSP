package com.gabrieloliveira.springsecurity.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gabrieloliveira.springsecurity.Controller.dto.CreateCurso;
import com.gabrieloliveira.springsecurity.Controller.dto.ListaCursoDTO;
import com.gabrieloliveira.springsecurity.Controller.dto.ListagemCursos;
import com.gabrieloliveira.springsecurity.models.model.Curso;
import com.gabrieloliveira.springsecurity.models.model.Role;
import com.gabrieloliveira.springsecurity.models.repository.CursoRepository;
import com.gabrieloliveira.springsecurity.models.repository.UserRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class CursoController {
    
    private final CursoRepository cursoRepository;

    private final UserRepository userRepository;

    public CursoController(CursoRepository cursoRepository, UserRepository userRepository) {
        this.cursoRepository = cursoRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/criar-curso") // Rota para criar um curso
    public ResponseEntity<Void> criarCurso(@RequestBody CreateCurso createCurso, JwtAuthenticationToken token) {

        var user = userRepository.findByUsername(token.getName());
        
        var curso = new Curso();
        curso.setUser(user.get());
        curso.setDescricao(createCurso.conteudo());

        cursoRepository.save(curso);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/cursos") // Rota para listar todos os cursos
    public ResponseEntity<ListagemCursos> listagemCursos(@RequestParam(value = "page", defaultValue = "0") int page, 
                                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
                                                            
        var cursos = cursoRepository.findAll( // Pega todos os cursos
            PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimeStamp"))
            .map(curso -> new ListaCursoDTO(curso.getCursoId(), curso.getDescricao(), curso.getUser().getUsername())); // Mapeia os cursos para um DTO

        return ResponseEntity.ok(new ListagemCursos(cursos.getContent(), page, pageSize, cursos.getTotalPages(), cursos.getTotalElements()));                                        
    }
    

    @DeleteMapping("/deletar-curso/{id}") // Rota para deletar um curso
    public ResponseEntity<Void> deletarCurso(@PathVariable("id") Long id, JwtAuthenticationToken token) {

        var user = userRepository.findByUsername(token.getName());

        var curso = cursoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado")); // Se não encontrar o curso, retorna 404

        var isAdmin = user.get().getRoles() // Verifica se o usuário é admin, se for, pode deletar qualquer curso
            .stream()
            .anyMatch(role -> role.getRoleName().equalsIgnoreCase(Role.Values.ROLE_ADMIN.name())); 

        if (isAdmin || curso.getUser().getUsername().equals(token.getName()) ) { // Se o usuário que está tentando deletar o curso é o dono do curso
            cursoRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para deletar este curso");
        }
        return ResponseEntity.ok().build();
    }
    
}
