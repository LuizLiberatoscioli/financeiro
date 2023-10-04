package com.financeiro.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.domain.model.CentroDeCusto;

@Repository
public interface CentroDeCustoRepository extends JpaRepository<CentroDeCusto , Long> {
	
	

}
