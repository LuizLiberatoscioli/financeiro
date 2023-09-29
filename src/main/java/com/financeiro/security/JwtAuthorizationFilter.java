package com.financeiro.security;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.financeiro.domain.model.Usuario;
import com.financeiro.domain.service.UsuarioService;
import com.financeiro.dto.usuario.UsuarioResponseDto;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
	
	private JwtUtil jwtUtil;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UsuarioService usuarioService;
	

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		
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
			UsuarioResponseDto usuarioDto = usuarioService.obterPorEmail(email);
			Usuario usuario = mapper.map(usuarioDto, Usuario.class);
			
			return new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			
			
			
		}
		return null;
	}
	

}

