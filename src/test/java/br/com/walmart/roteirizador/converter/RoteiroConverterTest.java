package br.com.walmart.roteirizador.converter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.walmart.roteirizador.entity.MapaRoteiro;
import br.com.walmart.roteirizador.entity.Roteiro;
import br.com.walmart.roteirizador.to.RoteiroTO;

/**
 * Junit para a classe de {@link RoteiroConverter}
 *
 * @author Vinicius A Gai
 * @since 06/10/2015
 */
public class RoteiroConverterTest {
	
	private static final String NOM_MAPA_ROTEIRO = "SP";
	private static final String NOM_ORIGEM = "A";
	private static final String NOM_DESTINO = "B";
	private static final Double DISTANCIA = 10.0;
	
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
	 * Teste de converter para roteiro onde o parametro informado é nulo.
	 */
	@Test
	public void testConverterRoteiroNull() {
		final RoteiroTO roteiroTO = given.getRoteiroTO();
		final MapaRoteiro mapaRoteiro = given.createMapaRoteiro().getMapaRoteiro();
		final Roteiro roteiro = RoteiroConverter.converterFromRoteiroTOAndMapaRoteiro(roteiroTO, mapaRoteiro);
		verify.verificaRoteiroNulo(roteiro);
	}

	/**
	 * Teste de converter para roteiro onde o parametro informado esta
	 * preenchido.
	 */
	@Test
	public void testConverterRoteiroCorreto() {
		final MapaRoteiro mapaRoteiro = given.createMapaRoteiro().getMapaRoteiro();
		final RoteiroTO roteiroTO = given.createRoteiroTO().getRoteiroTO();
		final Roteiro roteiro = RoteiroConverter.converterFromRoteiroTOAndMapaRoteiro(roteiroTO, mapaRoteiro);
		verify.verificaRoteiroNotNull(roteiro)
			  .verificaRoteiroPreenchimento(roteiro);
	}
	
	/**
	 * Classe para criar os mocks.
	 *
	 * @author Vinicius A Gai
	 * @since 06/10/2015
	 */
	private class Given {

		private RoteiroTO roteiroTO;
		private MapaRoteiro mapaRoteiro;

		public Given createRoteiroTO() {
			roteiroTO = new RoteiroTO();
			
			roteiroTO.setNomOrigem(NOM_ORIGEM);
			roteiroTO.setNomDestino(NOM_DESTINO);
			roteiroTO.setDistancia(DISTANCIA);
			
			return this;
		}
		
		public Given createMapaRoteiro() {
			mapaRoteiro = new MapaRoteiro();
			mapaRoteiro.setNomMapaRoteiro(NOM_MAPA_ROTEIRO);
			return this;
		}

		public MapaRoteiro getMapaRoteiro() {
			return mapaRoteiro;
		}

		public RoteiroTO getRoteiroTO() {
			return roteiroTO;
		}
		
	}

	private class Verify {

		public Verify verificaRoteiroNulo(final Roteiro roteiro) {
			Assert.assertNull("O roteiro deve ser nulo.", roteiro);
			return this;
		}

		public Verify verificaRoteiroNotNull(final Roteiro roteiro) {
			Assert.assertNotNull("O roteiro não deve ser nulo.", roteiro);
			return this;
		}
		
		public Verify verificaRoteiroPreenchimento(final Roteiro roteiro) {
			Assert.assertEquals("O nome do mapa de roteiro do roteiro está errado.", NOM_MAPA_ROTEIRO, roteiro.getNomMapaRoteiro());
			Assert.assertEquals("A origem do roteiro está errado.", NOM_ORIGEM, roteiro.getNomOrigem());
			Assert.assertEquals("O destino do roteiro está errado.", NOM_DESTINO, roteiro.getNomDestino());
			Assert.assertEquals("A distancia do roteiro está errado.", DISTANCIA, roteiro.getVlrDistancia());
			return this;
		}

	}
}
