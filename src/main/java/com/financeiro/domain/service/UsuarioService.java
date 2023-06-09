package com.financeiro.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.financeiro.domain.model.Usuario;
import com.financeiro.domain.repository.UsuarioRepository;
import com.financeiro.dto.usuario.UsuarioRequestDTO;
import com.financeiro.dto.usuario.UsuarioResponseDTO;

public class UsuarioService implements ICRUDService<UsuarioRequestDTO , UsuarioResponseDTO>{
	
	@Autowired
	private UsuarioRepository usuarioRespository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<UsuarioResponseDTO> obterTodos() {
		List<Usuario> usuarios = usuarioRespository.findAll();
		
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
		Optional<Usuario> optUsuario = usuarioRespository.findById(id);
		
		if(optUsuario.isEmpty()) {
			// exception
		}
		
		return mapper.map(optUsuario.get(), UsuarioResponseDTO.class);
	}

	@Override
	public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletar(Long id) {
		// TODO Auto-generated method stub
		
	}

	

}
