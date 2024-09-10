package com.gabrieloliveira.springsecurity.Controller.dto;

import java.util.List;

public record ListagemCursos(List<ListaCursoDTO> listaCurso, int page, int pageSize, int totalPages, Long totalElements) {

        
}
