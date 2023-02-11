package com.hloayza.mscv.usuarios.repository;

import com.hloayza.mscv.usuarios.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario,Long> {
}
