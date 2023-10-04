package com.financeiro.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.domain.model.Titulo;

@Repository
public interface TituloRepository extends JpaRepository<Titulo, Long>{

}
