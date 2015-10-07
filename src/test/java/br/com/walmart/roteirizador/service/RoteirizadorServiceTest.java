package br.com.walmart.roteirizador.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.walmart.roteirizador.entity.MapaRoteiro;
import br.com.walmart.roteirizador.entity.Roteiro;
import br.com.walmart.roteirizador.repository.MapaRoteiroRepository;
import br.com.walmart.roteirizador.repository.RoteiroRepository;
import br.com.walmart.roteirizador.service.impl.RoteirizadorServiceImpl;
import br.com.walmart.roteirizador.to.ConsultaRoteiroTO;
import br.com.walmart.roteirizador.to.RetornoConsultaRoteiroTO;

/**
 * Junit para a classe de {@link RoteirizadorService}
 *
 * @author Vinicius A Gai
 * @since 07/10/2015
 */
@RunWith(MockitoJUnitRunner.class)
public class RoteirizadorServiceTest {

	private static final String NOM_MAPA_ROTEIRO = "SP";
	private static final String NOM_ROTA_A = "A";
	private static final String NOM_ROTA_B = "B";
	private static final String NOM_ROTA_C = "C";
	private static final String NOM_ROTA_D = "D";
	private static final String NOM_ROTA_E = "E";
	private static final BigDecimal VLR_CUSTO_FINAL = new BigDecimal(6.25);
	private static final List<String> ROTA_FINAL = new ArrayList<String>();
	static {
		ROTA_FINAL.add(NOM_ROTA_A);
		ROTA_FINAL.add(NOM_ROTA_B);
		ROTA_FINAL.add(NOM_ROTA_D);
	}

	@Mock
	private MapaRoteiroRepository mapaRoteiroRepository;

	@Mock
	private RoteiroRepository roteiroRepository;

	@InjectMocks
	private RoteirizadorService roteirizadorService = new RoteirizadorServiceImpl();

	private Given given;
	private When when;
	private Verify verify;

	@Before
	public void setUp() {
		given = new Given();
		when = new When();
		verify = new Verify();
	}

	@After
	public void destroy() {
		given = null;
		when = null;
		verify = null;
	}
	
	/**
	 * Junit para testar o método de calculo de rota onde o objeto de consulta de roteiro é nulo.
	 */
	@Test(expected = NullPointerException.class)
	public void testCalculaRoteiroConsultaRoteiroTONull() {

		// carrega os objetos para o teste
		final ConsultaRoteiroTO consultaRoteiroTO = given.getConsultaRoteiroTO();
				
		roteirizadorService.calculaRoteiro(consultaRoteiroTO);
	}
	
	/**
	 * Junit para testar o método de calculo de rota onde o Mapa de roteiro nao foi localizado.
	 */
	@Test(expected = NullPointerException.class)
	public void testCalculaRoteiroMapaRoteiroNaoEncontrado() {

		// inicializa os mock.
		given.createConsultaRoteiroTO();
		
		// carrega os objetos para o teste
		final ConsultaRoteiroTO consultaRoteiroTO = given.getConsultaRoteiroTO();

		roteirizadorService.calculaRoteiro(consultaRoteiroTO);
	}
	
	/**
	 * Junit para testar o método de calculo de rota onde os roteiros nao foram localizados.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCalculaRoteiroRoteirosNaoEncontrado() {
		
		// inicializa os mock.
		given.createConsultaRoteiroTO().createMapaRoteiro();
		
		// carrega os objetos para o teste
		final ConsultaRoteiroTO consultaRoteiroTO = given.getConsultaRoteiroTO();
		
		// simula eventos
		when.simulaMapaRoteiroRepositoryFindOne(NOM_MAPA_ROTEIRO);
		
		roteirizadorService.calculaRoteiro(consultaRoteiroTO);
	}
	
	/**
	 * Junit para testar o método de calculo de rota onde os com sucesso, onde
	 * os dados de entrada serão: 
	 * <br>
	 * Malha de roteiro:
	 * <p>A B 10</p>
	 * <p>B D 15</p>
	 * <p>A C 20</p>
	 * <p>C D 30</p>
	 * <p>B E 50</p>
	 * <p>D E 30</p>
	 * 
	 * <br>
	 * Onde a rota será com Origem A, destino D, autonomia de 10 e valor do litro de combustivel de R$ 2,50
	 * <br>
	 * O resultado esperado é: 
	 * <br>Rota: A, B, D
	 * <br>Custo de R$ 6,25
	 */
	@Test
	public void testCalculaRoteiro() {
		
		// inicializa os mock.
		given.createConsultaRoteiroTO().createMapaRoteiro().createRoteiros();
		
		// carrega os objetos para o teste
		final ConsultaRoteiroTO consultaRoteiroTO = given.getConsultaRoteiroTO();
		
		// simula eventos
		when.simulaMapaRoteiroRepositoryFindOne(NOM_MAPA_ROTEIRO);
		when.simulaMapaRoteiroRepositoryFindAllByNomMapaRoteiro(NOM_MAPA_ROTEIRO);
		
		// Executa o roteirizador
		final RetornoConsultaRoteiroTO retornoConsultaRoteiroTO = roteirizadorService.calculaRoteiro(consultaRoteiroTO);
		
		// verificação
		verify.verificaRetornoRoteirizador(retornoConsultaRoteiroTO);
	}

	/**
	 * Classe para criar os mocks.
	 *
	 * @author Vinicius A Gai
	 * @since 07/10/2015
	 */
	private class Given {

		private ConsultaRoteiroTO consultaRoteiroTO;
		private MapaRoteiro mapaRoteiro;
		private List<Roteiro> roteiros;

		public Given createConsultaRoteiroTO() {
			consultaRoteiroTO = new ConsultaRoteiroTO();
			
			consultaRoteiroTO.setNomMapaRoteiro(NOM_MAPA_ROTEIRO);
			consultaRoteiroTO.setNomOrigem(NOM_ROTA_A);
			consultaRoteiroTO.setNomDestino(NOM_ROTA_D);
			consultaRoteiroTO.setVlrAutonomia(new BigDecimal(10.0));
			consultaRoteiroTO.setVlrCombustivel(new BigDecimal(2.50));
			
			return this;
		}
		
		public Given createMapaRoteiro() {
			mapaRoteiro = new MapaRoteiro();
			mapaRoteiro.setNomMapaRoteiro(NOM_MAPA_ROTEIRO);
			return this;
		}

		public Given createRoteiros() {

			roteiros = new ArrayList<Roteiro>();

			roteiros.add(createRoteiro(1l, NOM_MAPA_ROTEIRO, NOM_ROTA_A, NOM_ROTA_B, 10.0));
			roteiros.add(createRoteiro(2l, NOM_MAPA_ROTEIRO, NOM_ROTA_B, NOM_ROTA_D, 15.0));
			roteiros.add(createRoteiro(3l, NOM_MAPA_ROTEIRO, NOM_ROTA_A, NOM_ROTA_C, 20.0));
			roteiros.add(createRoteiro(4l, NOM_MAPA_ROTEIRO, NOM_ROTA_C, NOM_ROTA_D, 30.0));
			roteiros.add(createRoteiro(5l, NOM_MAPA_ROTEIRO, NOM_ROTA_B, NOM_ROTA_E, 50.0));
			roteiros.add(createRoteiro(6l, NOM_MAPA_ROTEIRO, NOM_ROTA_D, NOM_ROTA_E, 30.0));

			return this;
		}

		/**
		 * Cria o Roteiro.
		 * 
		 * @param codRoteiro
		 * @param nomMapaRoteiro
		 * @param nomOrigem
		 * @param nomDestino
		 * @param vlrDistancia
		 * @return
		 */
		private Roteiro createRoteiro(
				final Long codRoteiro,
				final String nomMapaRoteiro,
				final String nomOrigem, 
				final String nomDestino,
				final Double vlrDistancia) {

			final Roteiro roteiro = new Roteiro();
			roteiro.setCodRoteiro(codRoteiro);
			roteiro.setNomMapaRoteiro(nomMapaRoteiro);
			roteiro.setNomOrigem(nomOrigem);
			roteiro.setNomDestino(nomDestino);
			roteiro.setVlrDistancia(vlrDistancia);

			return roteiro;

		}

		public MapaRoteiro getMapaRoteiro() {
			return mapaRoteiro;
		}

		public ConsultaRoteiroTO getConsultaRoteiroTO() {
			return consultaRoteiroTO;
		}

		public List<Roteiro> getRoteiros() {
			return roteiros;
		}

	}

	/**
	 * Classe para simular os eventos.
	 *
	 * @author Vinicius A Gai
	 * @since 07/10/2015
	 */
	private class When {

		public When simulaMapaRoteiroRepositoryFindOne(final String nomMapaRoteiro) {
			Mockito.when(mapaRoteiroRepository.findOne(nomMapaRoteiro)).thenReturn(given.getMapaRoteiro());
			return this;
		}

		public When simulaMapaRoteiroRepositoryFindAllByNomMapaRoteiro(final String nomMapaRoteiro) {
			Mockito.when(roteiroRepository.findAllByNomMapaRoteiro(nomMapaRoteiro)).thenReturn(given.getRoteiros());
			return this;
		}

	}

	/**
	 * Classe para executar as verificações.
	 *
	 * @author Vinicius A Gai
	 * @since 07/10/2015
	 */
	private class Verify {

		public Verify verificaRetornoRoteirizador(final RetornoConsultaRoteiroTO retornoConsultaRoteiroTO) {
			
			Assert.assertNotNull("Os dados de retorno são obrigatórios.", retornoConsultaRoteiroTO);
			Assert.assertEquals(VLR_CUSTO_FINAL, retornoConsultaRoteiroTO.getVlrCustoTrajeto());
			Assert.assertArrayEquals(ROTA_FINAL.toArray(), retornoConsultaRoteiroTO.getRota().toArray());
			
			return this;
		}

	}

}
