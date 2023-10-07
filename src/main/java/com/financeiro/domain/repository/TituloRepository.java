package com.financeiro.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.financeiro.domain.model.Titulo;
import com.financeiro.domain.model.Usuario;

@Repository
public interface TituloRepository extends JpaRepository<Titulo, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM public.titulo " + "WHERE data_vencimento "
			+ "BETWEEN TO_TIMESTAMP(:periodoInicial, 'YYYY-MM-DD hh24:MI:SS') AND "
			+ "TO_TIMESTAMP(:periodoFinal, 'YYYY-MM-DD hh24:MI:SS')")
	List<Titulo> obterFluxoCaixaPorDataVencimento(
			// PARAM SAO EJETADOS DENTRO DA QUERY
			@Param("periodoInicial") String periodoInicial, @Param("periodoFinal") String periodoFinal);

	List<Titulo> findByUsuario(Usuario usuario);
}
