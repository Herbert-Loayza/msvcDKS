package com.hloayza.msvc.cursos.controller;


import com.hloayza.msvc.cursos.entity.Curso;
import com.hloayza.msvc.cursos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/curso")
public class CursoController {


    @Autowired
    private CursoService service;

    @GetMapping("/listar")
    public ResponseEntity<List<Curso>> listarTodo(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?>listarXid(@PathVariable Long id){
        Optional<Curso> cursoOptional = service.xId(id);
        if(cursoOptional.isPresent()){
           return ResponseEntity.ok(cursoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear")
    public ResponseEntity<?>crear(@Valid @RequestBody Curso curso, BindingResult result){
        if (result.hasErrors()){
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(curso));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?>editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validar(result);
        }
        Optional<Curso> cursoOptional1 = service.xId(id);
        if (cursoOptional1.isPresent()){
            Curso cursoDb = cursoOptional1.get();
            cursoDb.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cursoDb));
    }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?>eliminar(@PathVariable Long id){
        Optional<Curso> optionalCurso2 = service.xId(id);
        if(optionalCurso2.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String>errores =  new HashMap<>();
        result.getFieldErrors().forEach(e->{
            errores.put(e.getField(),"El campo " + e.getField() + " " + e.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
