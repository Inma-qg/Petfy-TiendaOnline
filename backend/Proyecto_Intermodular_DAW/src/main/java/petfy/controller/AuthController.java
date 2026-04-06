package petfy.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import petfy.modelo.entities.Usuario;
import petfy.modelo.service.UsuarioService;
import petfy.seguridad.JwtUtil;



@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;
 
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;

//LOGIN
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();
 
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");
 
            // Buscar usuario por email
      
            Usuario usuario = usuarioService.buscarPorEmail(email);
 
            if (usuario == null) {
                response.put("success", false);
                response.put("mensaje", "Email o contraseña incorrectos");
                return ResponseEntity.status(401).body(response);
            }
 
            // Verificar contraseña
            if (!passwordEncoder.matches(password, usuario.getPassword())) {
                response.put("success", false);
                response.put("mensaje", "Email o contraseña incorrectos");
                return ResponseEntity.status(401).body(response);
            }
 

            UserDetails userDetails = usuarioService.loadUserByUsername(email);
            String token = jwtUtil.generateToken(userDetails);
 
            // Login exitoso - Crear respuesta SIN password
            Map<String, Object> usuarioData = new HashMap<>();
            usuarioData.put("id", usuario.getId());
            usuarioData.put("nombre", usuario.getNombre());
            usuarioData.put("apellidos", usuario.getApellidos());
            usuarioData.put("email", usuario.getEmail());
            usuarioData.put("rol", usuario.getRol().name());
 
            response.put("success", true);
            response.put("token", token);   
            response.put("usuario", usuarioData);
            response.put("mensaje", "Login exitoso");
 
            return ResponseEntity.ok(response);
 
        } catch (Exception e) {
            response.put("success", false);
            response.put("mensaje", "Error en el servidor: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
 
//REGISTRO
    
    @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registro(@RequestBody Usuario usuario) {
        Map<String, Object> response = new HashMap<>();
 
        try {
            // Verificar que el email no exista
            if (usuarioService.existeEmail(usuario.getEmail())) {
                response.put("success", false);
                response.put("mensaje", "El email ya está registrado");
                return ResponseEntity.status(400).body(response);
            }
 
            // Crear usuario
            Usuario nuevoUsuario = usuarioService.crear(usuario);
 
            // Respuesta SIN password
            Map<String, Object> usuarioData = new HashMap<>();
            usuarioData.put("id", nuevoUsuario.getId());
            usuarioData.put("nombre", nuevoUsuario.getNombre());
            usuarioData.put("email", nuevoUsuario.getEmail());
            usuarioData.put("rol", nuevoUsuario.getRol().name());
 
            response.put("success", true);
            response.put("usuario", usuarioData);
            response.put("mensaje", "Registro exitoso");
 
            return ResponseEntity.status(201).body(response);
 
        } catch (Exception e) {
            response.put("success", false);
            response.put("mensaje", "Error al registrar: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    
}