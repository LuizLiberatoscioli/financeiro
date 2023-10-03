package com.financeiro.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.financeiro.domain.model.Usuario;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
	
	private JwtUtil jwtUtil;
	
	private UserDetailsSecurityServer userDetailsSecurityServer;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsSecurityServer userDetailsSecurityServer) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsSecurityServer = userDetailsSecurityServer;
		
	}
	
	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws  ServletException, java.io.IOException{
		//valor da propriedade authorization
		String header = request.getHeader("Authorization");
		
		if (header != null && header.startsWith("Bearer")) {
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
			
			if(auth != null && auth.isAuthenticated() ) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		
		chain.doFilter(request, response);
		
		
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		
		if (jwtUtil.isValidToken(token)) {
			String email = jwtUtil.getUserName(token);
			/*
			 * UsuarioResponseDto usuarioDto = usuarioService.obterPorEmail(email); Usuario
			 * usuario = mapper.map(usuarioDto, Usuario.class);
			 */
			
			Usuario usuario = (Usuario)userDetailsSecurityServer.loadUserByUsername(email);
			
			
			return new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			
			
			
		}
		return null;
	}
	

}

