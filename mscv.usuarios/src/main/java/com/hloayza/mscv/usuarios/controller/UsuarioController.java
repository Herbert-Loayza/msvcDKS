package com.hloayza.mscv.usuarios.controller;

import com.hloayza.mscv.usuarios.entity.Usuario;
import com.hloayza.mscv.usuarios.service.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioService service;

    @GetMapping("/listar")
    public List<Usuario>listarTodo(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verXid(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = service.buscarxId(id);
        if (usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
        }return ResponseEntity.notFound().build();
    }


    @PostMapping("/crear")
    public ResponseEntity<?>crear(@Valid @RequestBody Usuario usuario, BindingResult result){
        if (result.hasErrors()){
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }


    @PutMapping("/editar/{id}")
    public ResponseEntity<?>editar(@Valid  @RequestBody Usuario usuario,BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validar(result);
        }
        Optional<Usuario> usuarioOptional = service.buscarxId(id);
        if (usuarioOptional.isPresent()){
            Usuario usuarioDb = usuarioOptional.get();
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setApellidos(usuario.getApellidos());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuarioDb));
        }return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?>eliminar(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = service.buscarxId(id);
        if (usuarioOptional.isPresent()){
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
