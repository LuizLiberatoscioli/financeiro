package com.financeiro.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.domain.model.CentroDeCusto;
import com.financeiro.domain.model.Usuario;

@Repository
public interface CentroDeCustoRepository extends JpaRepository<CentroDeCusto , Long> {
	
	List<CentroDeCusto> findByUsuario(Usuario usuario);

}
