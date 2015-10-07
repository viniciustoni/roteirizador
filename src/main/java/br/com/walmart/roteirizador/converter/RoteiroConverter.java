package br.com.walmart.roteirizador.converter;

import br.com.walmart.roteirizador.entity.MapaRoteiro;
import br.com.walmart.roteirizador.entity.Roteiro;
import br.com.walmart.roteirizador.to.RoteiroTO;

/**
 * Classe para efetuar o converter dos dados de Roteiro
 * 
 * @author Vinicius A Gai
 */
public final class RoteiroConverter {

	/**
	 * Converter do objeto de {@link RoteiroTO} para Roteiro.
	 * 
	 * @param roteiroTO
	 * @param mapaRoteiro
	 * @return Objeto convertido
	 */
	public static Roteiro converterFromRoteiroTOAndMapaRoteiro(final RoteiroTO roteiroTO, final MapaRoteiro mapaRoteiro) {
		
		Roteiro roteiro = null;
		
		if (roteiroTO != null) {
			roteiro = new Roteiro();
			
			roteiro.setMapaRoteiro(mapaRoteiro);
			roteiro.setNomMapaRoteiro(mapaRoteiro.getNomMapaRoteiro());
			roteiro.setNomOrigem(roteiroTO.getNomOrigem());
			roteiro.setNomDestino(roteiroTO.getNomDestino());
			roteiro.setVlrDistancia(roteiroTO.getDistancia());
		}
		
		return roteiro;
		
	}
	
}
