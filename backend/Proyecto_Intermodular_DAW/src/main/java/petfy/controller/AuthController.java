package petfy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import petfy.modelo.dto.AuthRequest;
import petfy.modelo.dto.AuthResponse;
import petfy.seguridad.JwtUtil;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(), 
                    authRequest.getPassword()
                )
            );
            
            final UserDetails userDetails = userDetailsService.loadUserByUsername(
                authRequest.getEmail()
            );

            final String jwt = jwtUtil.generateToken(userDetails);

            AuthResponse response = new AuthResponse(
                jwt,
                "Bearer",
                userDetails.getUsername(),
                3600  
            );

            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException e) {
            return ResponseEntity
                .status(401)
                .body("Error: Email o contraseña incorrectos");
        } catch (Exception e) {
            return ResponseEntity
                .status(500)
                .body("Error interno del servidor");
        }
    }
}