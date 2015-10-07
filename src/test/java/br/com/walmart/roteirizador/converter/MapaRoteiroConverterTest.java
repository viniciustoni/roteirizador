package br.com.walmart.roteirizador.converter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.walmart.roteirizador.entity.MapaRoteiro;
import br.com.walmart.roteirizador.to.MapaRoteiroTO;

/**
 * Junit para a classe de {@link MapaRoteiroConverter}
 *
 * @author Vinicius A Gai
 * @since 06/10/2015
 */
public class MapaRoteiroConverterTest {

	private static final String NOM_MAPA_ROTEIRO = "SP";
	
	private Given given;
	private Verify verify;
	
	@Before
	public void setUp() {
		given = new Given();
		verify = new Verify();
	}
	
	@After
	public void destroy() {
		given = null;
		verify = null;
	}
	
	/**
	 * Teste de converter para mapa de roteiro onde o parametro informado é nulo.
	 */
	@Test
	public void testConverterMapaRoteiroNull() {
		final MapaRoteiroTO mapaRoteiroTO = given.getMapaRoteiroTO();
		final MapaRoteiro mapaRoteiro = MapaRoteiroConverter.converteFromMapaRoteiroTO(mapaRoteiroTO);
		verify.verificaMapaRoteiroNulo(mapaRoteiro);
	}
	
	/**
	 * Teste de converter para mapa de roteiro onde o parametro informado esta preenchido.
	 */
	@Test
	public void testConverterMapaRoteiroCorreto() {
		final MapaRoteiroTO mapaRoteiroTO = given.createMapaRoteiroTO().getMapaRoteiroTO();
		final MapaRoteiro mapaRoteiro = MapaRoteiroConverter.converteFromMapaRoteiroTO(mapaRoteiroTO);
		verify.verificaMapaRoteiroPreenchido(mapaRoteiro)
				.verificaMapaRoteiroNomeMapaRoteiro(mapaRoteiro);
	}
	
	/**
	 * Classe para criar os mocks.
	 *
	 * @author Vinicius A Gai
	 * @since 06/10/2015
	 */
	private class Given {
		
		private MapaRoteiroTO mapaRoteiroTO;

		public Given createMapaRoteiroTO() {
			mapaRoteiroTO = new MapaRoteiroTO();
			mapaRoteiroTO.setNomMapaRoteiro(NOM_MAPA_ROTEIRO);
			return this;
		}

		public MapaRoteiroTO getMapaRoteiroTO() {
			return mapaRoteiroTO;
		}

	}
	
	private class Verify {
		
		public Verify verificaMapaRoteiroNulo(final MapaRoteiro mapaRoteiro) {
			Assert.assertNull("O Mapa de roteiro deve ser nulo.", mapaRoteiro);
			return this;
		}
		
		public Verify verificaMapaRoteiroPreenchido(final MapaRoteiro mapaRoteiro) {
			Assert.assertNotNull("O Mapa de roteiro não deve ser nulo.", mapaRoteiro);
			return this;
		}
		
		public Verify verificaMapaRoteiroNomeMapaRoteiro(final MapaRoteiro mapaRoteiro) {
			Assert.assertEquals("O nome do mapa de roteiro está errado.", NOM_MAPA_ROTEIRO, mapaRoteiro.getNomMapaRoteiro());
			return this;
		}
		
	}
	
}
