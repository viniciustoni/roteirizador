package br.com.walmart.roteirizador.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import br.com.walmart.roteirizador.entity.Roteiro;
import br.com.walmart.roteirizador.to.RoteiroTO;

/**
 * Classe de converter para o TO de {@link RoteiroTO}
 *
 * @author Vinicius A Gai
 * @since 06/10/2015
 */
public final class RoteiroTOConverter {

	/**
	 * Converter do objeto de {@link Roteiro} para {@link RoteiroTO}.
	 * 
	 * @param roteiro
	 * @return
	 */
	public static RoteiroTO converterFromRoteiro(final Roteiro roteiro) {

		RoteiroTO roteiroTO = null;

		if (roteiro != null) {

			roteiroTO = new RoteiroTO();

			roteiroTO.setNomOrigem(roteiro.getNomOrigem());
			roteiroTO.setNomDestino(roteiro.getNomDestino());
			roteiroTO.setDistancia(roteiro.getVlrDistancia());
		}

		return roteiroTO;

	}

	/**
	 * Converter de uma lista de {@link Roteiro} para uma lista de {@link RoteiroTO}
	 * 
	 * @param roteiros
	 * @return
	 */
	public static List<RoteiroTO> converterFromListRoteiro(final List<Roteiro> roteiros) {

		List<RoteiroTO> roteirosTO = null;

		if (CollectionUtils.isNotEmpty(roteiros)) {
			
			roteirosTO = new ArrayList<RoteiroTO>();
			
			for (Roteiro roteiro : roteiros) {
				roteirosTO.add(converterFromRoteiro(roteiro));
			}
		}
		
		return roteirosTO;

	}

}
