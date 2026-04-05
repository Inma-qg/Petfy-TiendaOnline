package petfy.seguridad;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

	@Component
	public class JwtRequestFilter extends OncePerRequestFilter {

	    @Autowired
	    private UserDetailsService userDetailsService;

	    @Autowired
	    private JwtUtil jwtUtil;
	    
	    @Autowired
	    private JwtConfig jwtConfig;

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, 
	                                    HttpServletResponse response, 
	                                    FilterChain chain)
	            throws ServletException, IOException {

	        // 1. Obtener el header Authorization
	        final String authorizationHeader = request.getHeader(jwtConfig.getHeaderName());

	        String username = null;
	        String jwt = null;

	        // 2. Extraer el token del header
	        if (authorizationHeader != null && authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
	            // Remover "Bearer " del inicio
	            jwt = authorizationHeader.substring(jwtConfig.getTokenPrefix().length());
	            
	            try {
	                // Extraer username del token
	                username = jwtUtil.extractUsername(jwt);
	            } catch (Exception e) {
	                // Token inválido o expirado
	                logger.error("Error al extraer username del token: " + e.getMessage());
	            }
	        }

	        // 3. Validar token y autenticar usuario
	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            
	            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

	            // Validar token
	            if (jwtUtil.validateToken(jwt, userDetails)) {
	                
	                // Crear objeto de autenticación
	                UsernamePasswordAuthenticationToken authenticationToken = 
	                    new UsernamePasswordAuthenticationToken(
	                        userDetails, 
	                        null, 
	                        userDetails.getAuthorities()
	                    );
	                
	                authenticationToken.setDetails(
	                    new WebAuthenticationDetailsSource().buildDetails(request)
	                );
	                
	                // Establecer autenticación
	                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	            }
	        }
	        
	        // 4. Continuar con la cadena de filtros
	        chain.doFilter(request, response);
	    }
	}