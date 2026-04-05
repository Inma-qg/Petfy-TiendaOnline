package petfy.modelo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import petfy.modelo.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{


    // Buscar por email (para login)
    Optional<Usuario> findByEmail(String email);

    // Verificar si existe un email
    boolean existsByEmail(String email);

    // Buscar por rol
    List<Usuario> findByRol(Usuario.Rol rol);

    // Buscar usuarios activos
    List<Usuario> findByActivoTrue();

    // Buscar clientes activos
    List<Usuario> findByRolAndActivoTrue(Usuario.Rol rol);
	
}
