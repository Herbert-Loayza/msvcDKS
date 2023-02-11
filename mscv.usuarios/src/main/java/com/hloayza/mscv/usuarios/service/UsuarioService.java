package com.hloayza.mscv.usuarios.service;


import com.hloayza.mscv.usuarios.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario>listar();
    Optional<Usuario>buscarxId(Long id);
    Usuario guardar (Usuario usuario);
    void eliminar (Long id);


}
