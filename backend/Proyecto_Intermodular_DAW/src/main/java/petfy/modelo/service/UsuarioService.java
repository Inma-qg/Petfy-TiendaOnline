package petfy.modelo.service;

import java.util.List;

import petfy.modelo.entities.Usuario;

public interface UsuarioService {
	
    List<Usuario> obtenerTodos();
    Usuario obtenerPorId(Integer id);
    Usuario registrar(Usuario usuario);
    Usuario actualizarPerfil(Integer id, Usuario usuarioActualizado);
    int eliminar(Integer id);

    Usuario buscarPorEmail(String email);
    boolean existeEmail(String email);


}
