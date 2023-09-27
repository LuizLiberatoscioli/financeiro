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

import com.financeiro.domain.service.UsuarioService;
import com.financeiro.dto.usuario.UsuarioRequestDto;
import com.financeiro.dto.usuario.UsuarioResponseDto;

//aceitar requisicao de qualquer lugar
@CrossOrigin("*")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<UsuarioResponseDto>> obterTodos(){
		return ResponseEntity.ok(usuarioService.obterTodos());	
	}
	
	@GetMapping("/{id}")
	//pegar o id do getmapping e transformar no do path
	public ResponseEntity<UsuarioResponseDto> obterPorId(@PathVariable Long id){
		return ResponseEntity.ok(usuarioService.obterPorId(id));	
	}
	
	@PostMapping()
	//post vem no corpo , diferente do get
	//a capitura do corpo do post, usa o requestbody
	public ResponseEntity<UsuarioResponseDto> cadastrar(@RequestBody UsuarioRequestDto dto) {
		UsuarioResponseDto usuario = usuarioService.cadastrar(dto);
		return new ResponseEntity<>(usuario, HttpStatus.CREATED);
		
	}
	@PutMapping("/{id}")
	// o put e o get e o post juntos , recebe o id  pela url e o corpo
	public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable Long id,@RequestBody UsuarioRequestDto dto) {
		UsuarioResponseDto usuario = usuarioService.atualizar(id, dto);
		return new ResponseEntity<>(usuario, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar (@PathVariable Long id){
		usuarioService.deletar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
