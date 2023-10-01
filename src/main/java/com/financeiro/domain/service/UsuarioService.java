package com.financeiro.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.financeiro.domain.exception.ResourceBadRequestException;
import com.financeiro.domain.exception.ResourceNotFoundException;
import com.financeiro.domain.model.Usuario;
import com.financeiro.domain.repository.UsuarioRepository;
import com.financeiro.dto.usuario.UsuarioRequestDto;
import com.financeiro.dto.usuario.UsuarioResponseDto;

@Service
public class UsuarioService implements ICRUDService<UsuarioRequestDto , UsuarioResponseDto>{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public List<UsuarioResponseDto> obterTodos() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		/*
		 * for (Usuario usuario : usuarios) { UsuarioResponseDTO dto =
		 * mapper.map(usuario, UsuarioResponseDTO.class); usuariosDTO.add(dto); }
		 */
			
		return usuarios.stream()
				.map(usuario -> mapper.map(usuarios, UsuarioResponseDto.class))
				.collect(Collectors.toList());
		
	}

	@Override
	public UsuarioResponseDto obterPorId(Long id) {
		//optional é tipo uma caixa, tenta resolver o problema . é padrao. se tiver usuario ele traz.
		Optional<Usuario> optUsuario = usuarioRepository.findById(id);
		
		return mapper.map(optUsuario.get(), UsuarioResponseDto.class);
	}

	@Override
	public UsuarioResponseDto cadastrar(UsuarioRequestDto dto) {
		

		validarUsuario(dto);
		
		Optional<Usuario> optUsuario = usuarioRepository.findByEmail(dto.getEmail());
		
		if(optUsuario.isPresent()) {
			throw new ResourceBadRequestException("Ja existe um usuario cadastrado com esse email");
		}
		
		Usuario usuario = mapper.map(dto, Usuario.class);
		//dar um encoder na senha.
		
		String senha = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(senha);
		
		usuario.setId(null);
		usuario.setDataCadastro(new Date());
		usuario = usuarioRepository.save(usuario);
		return mapper.map(usuario, UsuarioResponseDto.class);
	}

	@Override
	public UsuarioResponseDto atualizar(Long id, UsuarioRequestDto dto) {
			
		UsuarioResponseDto usuarioBanco = obterPorId(id);
		validarUsuario(dto);
		
		Usuario usuario = mapper.map(dto, Usuario.class);
		
		//dar um encoder na senha.
		String senha = passwordEncoder.encode(dto.getSenha());
		usuario.setSenha(senha);
		
		usuario.setId(id);
		usuario.setDataInativacao(usuarioBanco.getDataInativacao());
		usuario.setDataCadastro(usuarioBanco.getDataCadastro());
		usuario = usuarioRepository.save(usuario);
		return mapper.map(usuario, UsuarioResponseDto.class);
	}

	@Override
	public void deletar(Long id) {
		
		Optional<Usuario> optUsuario = usuarioRepository.findById(id);
		
		if (optUsuario.isEmpty()) {
			throw new ResourceNotFoundException("Usuario nao encontrado");
		}
		
		Usuario usuario = optUsuario.get();
		usuario.setDataInativacao(new Date());
		usuarioRepository.save(usuario);
		
	
		/*
		 * delecao fisica usuarioRepository.deleteById(id);
		 */
		
	}
	
	private void validarUsuario(UsuarioRequestDto dto) {
		
		if(dto.getEmail() == null || dto.getSenha() == null) {
			throw new ResourceBadRequestException("E-mail e senha são obrigatórios");
		}
		
	}
	
	
	public UsuarioResponseDto obterPorEmail(String email) {
		//optional é tipo uma caixa, tenta resolver o problema . é padrao. se tiver usuario ele traz.
		Optional<Usuario> optUsuario = usuarioRepository.findByEmail(email);
		
		if(optUsuario.isPresent()) {
			throw new ResourceNotFoundException("Email nao encontrado ");
		}
		
		return mapper.map(optUsuario.get(), UsuarioResponseDto.class);
	}

	

}
