package com.example.usuarios.repositorios;

import com.example.usuarios.clases.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Puedes agregar métodos personalizados si lo necesitas, como buscar por correo.
}

