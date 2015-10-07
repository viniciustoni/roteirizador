package br.com.walmart.roteirizador.service;

import br.com.walmart.roteirizador.to.ConsultaRoteiroTO;
import br.com.walmart.roteirizador.to.RetornoConsultaRoteiroTO;

/**
 * Interface para declarar os métodos para roteirização.
 *
 * @author Vinicius A Gai
 * @since 06/10/2015
 */
public interface RoteirizadorService {

	/**
	 * Efetua o calculo de roteirização.
	 * 
	 * @param consultaRoteiroTO
	 * @return
	 */
	public RetornoConsultaRoteiroTO calculaRoteiro(final ConsultaRoteiroTO consultaRoteiroTO);
	
}
