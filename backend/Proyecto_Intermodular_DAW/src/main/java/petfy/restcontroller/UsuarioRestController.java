package petfy.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import petfy.modelo.entities.Usuario;
import petfy.modelo.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor

public class UsuarioRestController {
	
	private final UsuarioService uService;
	
	 
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        List<Usuario> usuarios = uService.obtenerTodos();
        return ResponseEntity.ok(usuarios);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Integer id) {
        Usuario usuario = uService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    /*
     * GET /api/usuarios/email/{email}
     * Buscar usuario por email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        Usuario usuario = uService.buscarPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    /*
     * GET /api/usuarios/verificar-email?email=xxx
     * Verificar si existe un email
     */
    @GetMapping("/verificar-email")
    public ResponseEntity<Boolean> verificarEmail(@RequestParam String email) {
        boolean existe = uService.existeEmail(email);
        return ResponseEntity.ok(existe);
    }

    /*
     * POST /api/usuarios/registro
     * Registrar nuevo usuario (cliente)
     */
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@Valid @RequestBody Usuario usuario) {
        Usuario nuevoUsuario = uService.registrar(usuario);
        
        // No devolver la contraseña en la respuesta
        nuevoUsuario.setPassword(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    /*
     * PUT /api/usuarios/{id}/perfil
     * Actualizar perfil de usuario
     */
    @PutMapping("/{id}/perfil")
    public ResponseEntity<Usuario> actualizarPerfil(
            @PathVariable Integer id,
            @Valid @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = uService.actualizarPerfil(id, usuario);
        usuarioActualizado.setPassword(null);
        return ResponseEntity.ok(usuarioActualizado);
    }

 

    /*
     * DELETE /api/usuarios/{id}
     * Eliminar usuario 
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        uService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
	

}
