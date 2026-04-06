package petfy.modelo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import petfy.modelo.entities.Usuario;

public interface UsuarioService extends UserDetailsService{
	
    List<Usuario> obtenerTodos();
    Usuario obtenerPorId(Integer id);
    Usuario actualizarPerfil(Integer id, Usuario usuarioActualizado);
    int eliminar(Integer id);

    Usuario buscarPorEmail(String email);
    boolean existeEmail(String email);
    Usuario crear(Usuario usuario);


}
