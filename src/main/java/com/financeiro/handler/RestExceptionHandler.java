package com.financeiro.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.financeiro.common.ConversorData;
import com.financeiro.domain.exception.ResourceBadRequestException;
import com.financeiro.domain.exception.ResourceNotFoundException;
import com.financeiro.domain.model.ErrorResposta;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResposta> handlerResourceNotFoundException(ResourceNotFoundException ex){
		
		String dataHora = ConversorData.converterDateParaDataHora(new Date());
		
		ErrorResposta erro = new ErrorResposta(dataHora, HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage());
		
		return new ResponseEntity<> (erro, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(ResourceBadRequestException.class)
	public ResponseEntity<ErrorResposta> handlerResourceBadRequestException(ResourceBadRequestException ex){
		
		String dataHora = ConversorData.converterDateParaDataHora(new Date());
		
		ErrorResposta erro = new ErrorResposta(dataHora, HttpStatus.BAD_REQUEST.value(), "Bad request", ex.getMessage());
		
		return new ResponseEntity<> (erro, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResposta> handlerRequestException(Exception ex){
		
		String dataHora = ConversorData.converterDateParaDataHora(new Date());
		
		ErrorResposta erro = new ErrorResposta(dataHora, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal error", ex.getMessage());
		
		return new ResponseEntity<> (erro, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
