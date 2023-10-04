package com.financeiro.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.financeiro.domain.exception.ResourceNotFoundException;
import com.financeiro.domain.model.Titulo;
import com.financeiro.domain.model.Usuario;
import com.financeiro.domain.repository.TituloRepository;
import com.financeiro.dto.usuario.titulo.TituloRequestDto;
import com.financeiro.dto.usuario.titulo.TituloResponseDto;

@Service
public class TituloService implements ICRUDService<TituloRequestDto , TituloResponseDto> {

	@Autowired
	private TituloRepository tituloRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public List<TituloResponseDto> obterTodos() {
		
		List<Titulo> titulos = tituloRepository.findAll();
		
		return titulos.stream()
				.map(titulo -> mapper.map(titulo, TituloResponseDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public TituloResponseDto obterPorId(Long id) {
		Optional<Titulo> optTitulo = tituloRepository.findById(id);
		
		if (optTitulo.isEmpty()) {
			throw new ResourceNotFoundException("Nao foi possivel encontrar o titulo com o id "+id);
		}
		return mapper.map(optTitulo.get(), TituloResponseDto.class);
	}

	@Override
	public TituloResponseDto cadastrar(TituloRequestDto dto) {
		//converter em centro de custo model
				Titulo titulo = mapper.map(dto, Titulo.class);
				
				// pega o usuario da authenticacao
				Usuario usuario = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				//salva no centro de custo
				titulo.setUsuario(usuario);
				//remove id
				titulo.setId(null);
				//salva no banco
				titulo= tituloRepository.save(titulo);
					
				//retorna o centro de custo
				return mapper.map(titulo, TituloResponseDto.class);
	}

	@Override
	public TituloResponseDto atualizar(Long id, TituloRequestDto dto) {
		obterPorId(id);
		
		Titulo titulo = mapper.map(dto, Titulo.class);
		
		Usuario usuario = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		titulo.setUsuario(usuario);
		titulo.setId(id);
		titulo = tituloRepository.save(titulo);
		
		return mapper.map(titulo, TituloResponseDto.class);
	}

	@Override
	public void deletar(Long id) {
obterPorId(id);
		
		tituloRepository.deleteById(id);
		
	}

}
