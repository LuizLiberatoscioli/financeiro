package com.financeiro.dto.dashboard;

import java.util.List;

import com.financeiro.dto.usuario.titulo.TituloResponseDto;

public class DashboardResponseDto {

	private Double totalApagar;

	private Double totalAreceber;

	private Double saldo;

	private List<TituloResponseDto> titulosApagar;
	private List<TituloResponseDto> tituloAreceber;

	public DashboardResponseDto() {
		super();
	}

	public DashboardResponseDto(Double totalApagar, Double totalAreceber, Double saldo,
			List<TituloResponseDto> titulosApagar, List<TituloResponseDto> tituloAreceber) {
		super();
		this.totalApagar = totalApagar;
		this.totalAreceber = totalAreceber;
		this.saldo = saldo;
		this.titulosApagar = titulosApagar;
		this.tituloAreceber = tituloAreceber;
	}

	public Double getTotalApagar() {
		return totalApagar;
	}

	public void setTotalApagar(Double totalApagar) {
		this.totalApagar = totalApagar;
	}

	public Double getTotalAreceber() {
		return totalAreceber;
	}

	public void setTotalAreceber(Double totalAreceber) {
		this.totalAreceber = totalAreceber;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public List<TituloResponseDto> getTitulosApagar() {
		return titulosApagar;
	}

	public void setTitulosApagar(List<TituloResponseDto> titulosApagar) {
		this.titulosApagar = titulosApagar;
	}

	public List<TituloResponseDto> getTituloAreceber() {
		return tituloAreceber;
	}

	public void setTituloAreceber(List<TituloResponseDto> tituloAreceber) {
		this.tituloAreceber = tituloAreceber;
	}

}
