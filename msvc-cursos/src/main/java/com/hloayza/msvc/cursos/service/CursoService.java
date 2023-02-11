package com.hloayza.msvc.cursos.service;

import com.hloayza.msvc.cursos.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso>listar();
    Optional<Curso>xId(Long id);
    Curso guardar(Curso curso);
    void eliminar(Long id);
}
