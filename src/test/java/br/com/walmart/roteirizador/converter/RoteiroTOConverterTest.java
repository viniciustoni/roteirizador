package br.com.walmart.roteirizador.converter;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.walmart.roteirizador.entity.Roteiro;
import br.com.walmart.roteirizador.to.RoteiroTO;

/**
 * Junit para a classe de {@link RoteiroTOConverter}.
 *
 * @author Vinicius A Gai
 * @since 06/10/2015
 */
public class RoteiroTOConverterTest {
	
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
	 * Teste de converter para roteiroTO onde o parametro informado é nulo.
	 */
	@Test
	public void testConverterRoteiroTONull() {
		final Roteiro roteiro = given.getRoteiro();
		final RoteiroTO roteiroTO = RoteiroTOConverter.converterFromRoteiro(roteiro);
		verify.verificaRoteiroTONulo(roteiroTO);
	}

	/**
	 * Teste de converter para roteiroTO onde o parametro informado esta
	 * preenchido.
	 */
	@Test
	public void testConverterRoteiroTOCorreto() {
		final Roteiro roteiro = given.createRoteiro().getRoteiro();
		final RoteiroTO roteiroTO = RoteiroTOConverter.converterFromRoteiro(roteiro);
		verify.verificaRoteiroTONotNull(roteiroTO)
			  .verificaRoteiroTOPreenchimento(roteiroTO);
	}
	
	/**
	 * Teste de converter para uma lista de roteiroTO onde o parametro informado é nulo.
	 */
	@Test
	public void testConverterListRoteiroTONull() {
		final List<Roteiro> roteiros = given.getRoteiros();
		final List<RoteiroTO> roteiroTO = RoteiroTOConverter.converterFromListRoteiro(roteiros);
		verify.verificaListRoteiroTONulo(roteiroTO);
	}

	/**
	 * Teste de converter para uma lista de roteiroTO onde o parametro informado esta
	 * preenchido.
	 */
	@Test
	public void testConverterListRoteiroTOCorreto() {
		final List<Roteiro> roteiros = given.createListRoteiros().getRoteiros();
		final List<RoteiroTO> roteiroTO = RoteiroTOConverter.converterFromListRoteiro(roteiros);
		verify.verificaListRoteiroTONotNull(roteiroTO)
			  .verificaRoteiroTOPreenchimento(roteiroTO.get(0));
	}
	
	/**
	 * Classe para criar os mocks.
	 *
	 * @author Vinicius A Gai
	 * @since 06/10/2015
	 */
	private class Given {

		private Roteiro roteiro;
		private List<Roteiro> roteiros;

		public Given createRoteiro() {
			roteiro = new Roteiro();
			
			roteiro.setNomOrigem(NOM_ORIGEM);
			roteiro.setNomDestino(NOM_DESTINO);
			roteiro.setVlrDistancia(DISTANCIA);
			
			return this;
		}
		
		public Given createListRoteiros() {
			roteiro = new Roteiro();
			
			roteiro.setNomOrigem(NOM_ORIGEM);
			roteiro.setNomDestino(NOM_DESTINO);
			roteiro.setVlrDistancia(DISTANCIA);
			
			roteiros = new ArrayList<Roteiro>();
			roteiros.add(roteiro);
			
			return this;
		}
		
		public List<Roteiro> getRoteiros() {
			return roteiros;
		}

		public Roteiro getRoteiro() {
			return roteiro;
		}
		
	}

	private class Verify {

		public Verify verificaRoteiroTONulo(final RoteiroTO roteiroTO) {
			Assert.assertNull("O roteiroTO deve ser nulo.", roteiroTO);
			return this;
		}
		
		public Verify verificaListRoteiroTONulo(final List<RoteiroTO> roteirosTO) {
			Assert.assertNull("A lista roteirosTO deve ser nulo.", roteirosTO);
			return this;
		}

		public Verify verificaRoteiroTONotNull(final RoteiroTO roteiroTO) {
			Assert.assertNotNull("O roteiroTO não deve ser nulo.", roteiroTO);
			return this;
		}
		
		public Verify verificaListRoteiroTONotNull(final List<RoteiroTO> roteirosTO) {
			Assert.assertNotNull("A lista roteirosTO não deve ser nulo.", roteirosTO);
			return this;
		}
		
		public Verify verificaRoteiroTOPreenchimento(final RoteiroTO roteiroTO) {
			Assert.assertEquals("A origem do roteiroTO está errado.", NOM_ORIGEM, roteiroTO.getNomOrigem());
			Assert.assertEquals("O destino do roteiroTO está errado.", NOM_DESTINO, roteiroTO.getNomDestino());
			Assert.assertEquals("A distancia do roteiroTO está errado.", DISTANCIA, roteiroTO.getDistancia());
			return this;
		}

	}
}
