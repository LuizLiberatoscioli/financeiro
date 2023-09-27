package com.financeiro.security;

import java.util.Date;

import javax.naming.AuthenticationException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financeiro.common.ConversorData;
import com.financeiro.domain.model.ErrorResposta;
import com.financeiro.domain.model.Usuario;
import com.financeiro.dto.usuario.LoginRequestDto;
import com.financeiro.dto.usuario.LoginResponseDto;
import com.financeiro.dto.usuario.UsuarioResponseDto;
import com.google.gson.Gson;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;
    
    @Autowired
    private ModelMapper modelMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        super();
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

        setFilterProcessesUrl("/api/auth");
    }

    //valida se a autenticacao esta ok
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){

        try {
            LoginRequestDto login = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha());
            Authentication auth  = authenticationManager.authenticate(authToken);
            return auth;

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Usuário ou senha inválidos");

        } catch(Exception e){
            throw new InternalAuthenticationServiceException(e.getMessage());
        }

    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
    FilterChain chain, Authentication authResult) throws IOException, java.io.IOException{
        
        Usuario usuario  = (Usuario) authResult.getPrincipal();
        String token = jwtUtil.gerarToken(authResult);
        
        UsuarioResponseDto usuarioResponse = modelMapper.map(usuario, UsuarioResponseDto.class);
        
		/*
		 * UsuarioResponseDto usuarioResponse = new UsuarioResponseDto();
		 * usuarioResponse.setId(usuario.getId());
		 * usuarioResponse.setEmail(usuario.getEmail());
		 * usuarioResponse.setNome(usuario.getNome());
		 * usuarioResponse.setFoto(usuario.getFoto());
		 * usuarioResponse.setDataInativacao(usuario.getDataInativacao());
		 * usuarioResponse.setDataCadastro(usuario.getDataCadastro());
		 */

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken("Bearer " + token);
        loginResponseDto.setUsuario(usuarioResponse);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(loginResponseDto));
    }
    
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
        AuthenticationException failed)  throws IOException, ServletException, java.io.IOException {

            String dataHora = ConversorData.converterDateParaDataHora(new Date());

            ErrorResposta erro = new ErrorResposta(dataHora, 401, "Unauthorized", failed.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    
            response.setCharacterEncoding("UTF-8"); 
            response.setContentType("application/json");
            response.getWriter().print(new Gson().toJson(erro));
    }

	
}