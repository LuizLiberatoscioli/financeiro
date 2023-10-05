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

import com.financeiro.domain.service.TituloService;
import com.financeiro.dto.usuario.titulo.TituloRequestDto;
import com.financeiro.dto.usuario.titulo.TituloResponseDto;

@CrossOrigin("")
@RestController
@RequestMapping("/api/titulo")
public class TituloController {

	@Autowired
	private TituloService tituloService;
	
	@GetMapping
	public ResponseEntity<List<TituloResponseDto>> obterTodos(){
		
		return ResponseEntity.ok(tituloService.obterTodos());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TituloResponseDto> obterId(@PathVariable Long id){
		
		return ResponseEntity.ok(tituloService.obterPorId(id));
	}
	
	@PostMapping
	public ResponseEntity<TituloResponseDto> cadastrar(@RequestBody TituloRequestDto dto){
		TituloResponseDto response = tituloService.cadastrar(dto);
		return new ResponseEntity<>(response , HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TituloResponseDto> atualizar(@PathVariable Long id ,@RequestBody TituloRequestDto dto){
		TituloResponseDto response = tituloService.atualizar(id, dto);
		return ResponseEntity.ok(tituloService.atualizar(id, dto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		tituloService.deletar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	
}
