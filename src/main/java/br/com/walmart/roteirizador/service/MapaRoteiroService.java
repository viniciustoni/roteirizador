package br.com.walmart.roteirizador.service;

import br.com.walmart.roteirizador.to.MapaRoteiroTO;

/**
 * Interface de servico para a Malha de roteirização
 * 
 * @author Vinicius A Gai
 *
 */
public interface MapaRoteiroService {

	/**
	 * Grava os dados do mapa de roteiro.
	 * 
	 * @param mapaRoteiroTO
	 */
	void gravaMapaRoteiro(final MapaRoteiroTO mapaRoteiroTO);
	
	/**
	 * Remove o mapa de roteiro.
	 * 
	 * @param nomMapaRoteiro
	 */
	void removeMapaRoteiro(final String nomMapaRoteiro);
	
}
