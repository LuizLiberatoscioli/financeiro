package com.financeiro.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financeiro.domain.exception.ResourceBadRequestException;
import com.financeiro.domain.exception.ResourceNotFoundException;
import com.financeiro.domain.model.Usuario;
import com.financeiro.domain.repository.UsuarioRepository;
import com.financeiro.dto.usuario.UsuarioRequestDTO;
import com.financeiro.dto.usuario.UsuarioResponseDTO;

@Service
public class UsuarioService implements ICRUDService<UsuarioRequestDTO , UsuarioResponseDTO>{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<UsuarioResponseDTO> obterTodos() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		/*
		 * for (Usuario usuario : usuarios) { UsuarioResponseDTO dto =
		 * mapper.map(usuario, UsuarioResponseDTO.class); usuariosDTO.add(dto); }
		 */
			
		return usuarios.stream()
				.map(usuario -> mapper.map(usuarios, UsuarioResponseDTO.class))
				.collect(Collectors.toList());
		
	}

	@Override
	public UsuarioResponseDTO obterPorId(Long id) {
		//optional é tipo uma caixa, tenta resolver o problema . é padrao. se tiver usuario ele traz.
		Optional<Usuario> optUsuario = usuarioRepository.findById(id);
		
	
		
		return mapper.map(optUsuario.get(), UsuarioResponseDTO.class);
	}

	@Override
	public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
		
		
		if(dto.getEmail() == null || dto.getSenha() == null ) {
			throw new ResourceBadRequestException("E-mail e senha sao obrigatrios");
		} 
		validarUsuario(dto);
		
		Usuario usuario = mapper.map(dto, Usuario.class);
		//dar um encoder na senha.
		usuario.setId(null);
		usuario = usuarioRepository.save(usuario);
		return mapper.map(usuario, UsuarioResponseDTO.class);
	}

	@Override
	public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
		
	
		
		UsuarioResponseDTO usuarioBanco = obterPorId(id);
		validarUsuario(dto);
		
		Usuario usuario = mapper.map(dto, Usuario.class);
		
		//dar um encoder na senha.
		
		usuario.setId(id);
		usuario.setDataInativacao(usuarioBanco.getDataInativacao());
		usuario = usuarioRepository.save(usuario);
		return mapper.map(usuario, UsuarioResponseDTO.class);
	}

	@Override
	public void deletar(Long id) {
		
		UsuarioResponseDTO usuarioEncontrado = obterPorId(id);
		Usuario usuario = mapper.map(usuarioEncontrado, Usuario.class);
		
		usuario.setDataInativacao(new Date());
		usuarioRepository.save(usuario);
		
	
		/*
		 * delecao fisica usuarioRepository.deleteById(id);
		 */
		
	}
	
	private void validarUsuario(UsuarioRequestDTO dto) {
		
		if(dto.getEmail() == null || dto.getSenha() == null) {
			throw new ResourceBadRequestException("E-mail e senha são obrigatórios");
		}
		
	}

	

}
