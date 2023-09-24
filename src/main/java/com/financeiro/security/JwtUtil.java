package com.financeiro.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.financeiro.domain.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	// valores das configuracoes do properties
	@Value("${auth.jwt.secret}")
	private String jwtSecret;
	
	@Value("${auth.jwt-expiration-milliseg}")
	private Long jwtExpirationMilliseg;
	
	public String gerarToken(Authentication authentication) {

		//pega a data atual e soma mais um dia em milli
		Date dataExpiracao = new Date(new Date().getTime() + jwtExpirationMilliseg);
		
		// Aqui pegamos o usuario atual da autenticacao
		Usuario usuario = (Usuario )authentication.getPrincipal();
		
		try {
			
			//aqui gera uma chvae com base na secret
			Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8"));
			
			//aqui gera o token
			return Jwts.builder()
					.setSubject(usuario.getUsername())
					.setIssuedAt(new Date())
					.setExpiration(dataExpiracao)
					.signWith(secretKey)
					.compact();
			
		} catch( Exception e) {
			System.out.println(e.getMessage());
			return "";
			
		}
		
	}

	// metodo que sabe descobrir de dentro do tokem com base na chave privada qual as permissoes do usuario
	private Claims getClaims(String token) {
		try {
			
			Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8"));
			Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
			return claims;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	//metodo que sabe pegar o email do usuario dentro do token
	public String getUserName (String token) {
		Claims claims = getClaims(token);
		
		if (claims == null) {
			return null;
		}
		
		return claims.getSubject();
		
	}
	
	//metodo para validar o token
	public boolean isValidToken(String token) {
		Claims claims = getClaims(token);
		
		if (claims == null) {
			return false;
		}
		
		String email = claims.getSubject();
		Date dataExpiracao = claims.getExpiration();
		Date agora = new Date(System.currentTimeMillis());
		
		if (email != null && agora.before(dataExpiracao)) {
			return true;
		}
		
		return false;
		
	}
	
}
