package petfy.modelo.service;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import petfy.modelo.entities.Usuario;
import petfy.modelo.entities.Usuario.Rol;
import petfy.modelo.repository.UsuarioRepository;

@Service
@Primary
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public List<Usuario> obtenerTodos() {
		
		return usuarioRepository.findByActivoTrue();
	}

	@Override
	public Usuario obtenerPorId(Integer id) {
	    return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

	
	
	@Override
	public Usuario actualizarPerfil(Integer id, Usuario usuarioActualizado) {
		Usuario usuarioExistente = obtenerPorId(id);
		
		//solo deja actualizar los campos permitidos
		
		usuarioExistente.setNombre(usuarioActualizado.getNombre());
		usuarioExistente.setApellidos(usuarioActualizado.getApellidos());
		usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
		
		return usuarioRepository.save(usuarioExistente);
	}

	@Override
	public int eliminar(Integer id) {
		if(usuarioRepository.existsById(id))
			try {
				usuarioRepository.deleteById(id);
				return 1;
			}catch (Exception e) {
				System.out.println(e.getMessage());
				return -1;
			}
		else
			return 0;
	}

	@Override
	public Usuario buscarPorEmail(String email) {
	    return usuarioRepository.findByEmail(email)
                .orElse(null);
    }

	@Override
	public boolean existeEmail(String email) {
	    return usuarioRepository.existsByEmail(email);
	    }
	
	
	//Validación
	private void validarUsuario(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }

        if (usuario.getPassword() == null || usuario.getPassword().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username)
	            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
	        
	        return User.builder()
	            .username(usuario.getEmail())
	            .password(usuario.getPassword())
	            .roles(usuario.getRol().name())
	            .build();
	}

	@Override
	public Usuario crear(Usuario usuario) {
        // Cifrar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        // Establecer valores por defecto
        usuario.setActivo(true);
        usuario.setFechaRegistro(LocalDateTime.now());
        
        // Por defecto es CLIENTE (no ADMIN)
        if (usuario.getRol() == null) {
            usuario.setRol(Rol.CLIENTE);
        }
        
        return usuarioRepository.save(usuario);
    }
	
	
}
