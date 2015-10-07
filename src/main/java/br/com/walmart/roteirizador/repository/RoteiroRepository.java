package br.com.walmart.roteirizador.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.walmart.roteirizador.entity.Roteiro;

/**
 * Classe de repositorio para entidade Roteiro.
 * 
 * @author Vinicius A Gai
 *
 */
public interface RoteiroRepository extends CrudRepository<Roteiro, Long> {

	/**
	 * Busca pelos dados da do roteiro.
	 * 
	 * @param nomMapaRoteiro
	 * @param nomOrigem
	 * @param nomDestino
	 * @return Roteiros.
	 */
	Roteiro findByNomMapaRoteiroAndNomOrigemAndNomDestino(final String nomMapaRoteiro, final String nomOrigem, final String nomDestino);
	
	/**
	 * Busca pelos dados dos roteiros para o nome do mapa informado.
	 * 
	 * @param nomMapaRoteiro
	 * @return
	 */
	List<Roteiro> findAllByNomMapaRoteiro(final String nomMapaRoteiro);
	
}
