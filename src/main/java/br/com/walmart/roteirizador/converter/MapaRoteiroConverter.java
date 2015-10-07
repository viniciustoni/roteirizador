package br.com.walmart.roteirizador.converter;

import br.com.walmart.roteirizador.entity.MapaRoteiro;
import br.com.walmart.roteirizador.to.MapaRoteiroTO;

/**
 * Conversor para a entidade Malha
 * 
 * @author Vinicius A Gai
 *
 */
public final class MapaRoteiroConverter {

	/**
	 * Converter do objeto de {@link MapaRoteiroTO} para o objeto de {@link MapaRoteiro}
	 * 
	 * @param mapaRoteiroTO
	 * @return Objeto de Malha.
	 */
	public static MapaRoteiro converteFromMapaRoteiroTO(final MapaRoteiroTO mapaRoteiroTO) {
		
		MapaRoteiro mapaRoteiro = null;
		
		if ( mapaRoteiroTO != null) {
			mapaRoteiro = new MapaRoteiro();
			mapaRoteiro.setNomMapaRoteiro(mapaRoteiroTO.getNomMapaRoteiro());
		}
		
		return mapaRoteiro;
		
	}
	
}
