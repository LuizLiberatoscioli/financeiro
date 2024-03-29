package com.financeiro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financeiro.domain.service.CentroDeCustoService;
import com.financeiro.dto.usuario.centrodecusto.CentroDeCustoRequestDto;
import com.financeiro.dto.usuario.centrodecusto.CentroDeCustoResponseDto;

@CrossOrigin("")
@RestController
@RequestMapping("/api/centrodecustos")
public class CentroDeCustoController {
	
	@Autowired
	private CentroDeCustoService centroDeCustoService;

	@GetMapping
	public ResponseEntity<List<CentroDeCustoResponseDto>> obterTodos() {

		return ResponseEntity.ok(centroDeCustoService.obterTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CentroDeCustoResponseDto> obterId(@PathVariable Long id) {

		return ResponseEntity.ok(centroDeCustoService.obterPorId(id));
	}

	@PostMapping
	public ResponseEntity<CentroDeCustoResponseDto> cadastrar(@RequestBody CentroDeCustoRequestDto dto) {
		CentroDeCustoResponseDto response = centroDeCustoService.cadastrar(dto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CentroDeCustoResponseDto> atualizar(@PathVariable Long id,
			@RequestBody CentroDeCustoRequestDto dto) {
		CentroDeCustoResponseDto response = centroDeCustoService.atualizar(id, dto);
		return ResponseEntity.ok(centroDeCustoService.atualizar(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		centroDeCustoService.deletar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

}
