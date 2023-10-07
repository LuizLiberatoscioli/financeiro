package com.financeiro.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.financeiro.domain.exception.ResourceNotFoundException;
import com.financeiro.domain.model.CentroDeCusto;
import com.financeiro.domain.model.Usuario;
import com.financeiro.domain.repository.CentroDeCustoRepository;
import com.financeiro.dto.usuario.centrodecusto.CentroDeCustoRequestDto;
import com.financeiro.dto.usuario.centrodecusto.CentroDeCustoResponseDto;

@Service
public class CentroDeCustoService implements ICRUDService<CentroDeCustoRequestDto, CentroDeCustoResponseDto> {

	@Autowired
	private CentroDeCustoRepository centroDeCustoRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public List<CentroDeCustoResponseDto> obterTodos() {
		List<CentroDeCusto> lista = centroDeCustoRepository.findAll();
		return lista.stream().map(centroDeCusto -> mapper.map(centroDeCusto, CentroDeCustoResponseDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public CentroDeCustoResponseDto obterPorId(Long id) {
		Optional<CentroDeCusto> optCentroDeCusto = centroDeCustoRepository.findById(id);

		if (optCentroDeCusto.isEmpty()) {
			throw new ResourceNotFoundException("Nao foi possivel encontrar o centro de custo com o id " + id);
		}
		return mapper.map(optCentroDeCusto.get(), CentroDeCustoResponseDto.class);
	}

	@Override
	public CentroDeCustoResponseDto cadastrar(CentroDeCustoRequestDto dto) {
		// converter em centro de custo model
		CentroDeCusto centroDeCusto = mapper.map(dto, CentroDeCusto.class);

		// pega o usuario da authenticacao
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// salva no centro de custo
		centroDeCusto.setUsuario(usuario);
		// remove id
		centroDeCusto.setId(null);
		// salva no banco
		centroDeCusto = centroDeCustoRepository.save(centroDeCusto);

		// retorna o centro de custo
		return mapper.map(centroDeCusto, CentroDeCustoResponseDto.class);
	}

	@Override
	public CentroDeCustoResponseDto atualizar(Long id, CentroDeCustoRequestDto dto) {
		obterPorId(id);

		CentroDeCusto centroDeCusto = mapper.map(dto, CentroDeCusto.class);

		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		centroDeCusto.setUsuario(usuario);
		centroDeCusto.setId(id);
		centroDeCusto = centroDeCustoRepository.save(centroDeCusto);

		return mapper.map(centroDeCusto, CentroDeCustoResponseDto.class);
	}

	@Override
	public void deletar(Long id) {
		obterPorId(id);

		centroDeCustoRepository.deleteById(id);

	}

}
