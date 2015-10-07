package br.com.walmart.roteirizador.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.walmart.roteirizador.entity.Roteiro;
import br.com.walmart.roteirizador.entity.MapaRoteiro;
import br.com.walmart.roteirizador.repository.MapaRoteiroRepository;
import br.com.walmart.roteirizador.repository.RoteiroRepository;
import br.com.walmart.roteirizador.service.impl.MapaRoteiroServiceImpl;
import br.com.walmart.roteirizador.to.MapaRoteiroTO;
import br.com.walmart.roteirizador.to.RoteiroTO;

/**
 * Junit para a classe de {@link MapaRoteiroService}
 *
 * @author Vinicius A Gai
 * @since 06/05/2015 
 */
@RunWith(MockitoJUnitRunner.class)
public class MapaRoteiroServiceTest {

	private static final String NOM_MAPA_ROTEIRO = "SP";
	private static final String NOM_ORIGEM = "A";
	private static final String NOM_DESTINO = "B";
	private static final Double DISTANCIA = 10.0;
	
	@Mock
	private MapaRoteiroRepository mapaRoteiroRepository;
	
	@Mock
	private RoteiroRepository roteiroRepository;
	
	@Captor
	private ArgumentCaptor<Roteiro> roteiroCaptor;
	
	@InjectMocks
	private MapaRoteiroService mapaRoteiroService = new MapaRoteiroServiceImpl();
	
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
	 * Junit para testar o método de save de mapa de roteiro, porém informando um mapa de roteiro nulo.
	 */
	@Test(expected = NullPointerException.class)
	public void testSaveMapaRoteiroMapaRoteiroTONull() {
		final MapaRoteiroTO mapaRoteiroTO = given.getMapaRoteiroTO();
		mapaRoteiroService.gravaMapaRoteiro(mapaRoteiroTO);
	}
	
	@Test
	public void testSaveMapaRoteiroMapaRoteiroTONovo() {
		final MapaRoteiroTO mapaRoteiroTO = given.createMapaRoteiroTO().getMapaRoteiroTO();
		
		mapaRoteiroService.gravaMapaRoteiro(mapaRoteiroTO);
		
		verify.validaQuatidadesSaveMapaRoteiro(1)
			.validaSaveRoteiro(1, given.getRoteiro(), DISTANCIA);
	}
	
	@Test
	public void testSaveMapaRoteiroMapaRoteiroTOExistente() {
		
		// criação dos mocks.
		given.createMapaRoteiroTO()
		     .createRoteiro()
		     .createMapaRoteiro();
		
		final MapaRoteiroTO mapaRoteiroTO = given.getMapaRoteiroTO();
		
		// eventos
		when.simulaMapaRoteiroRepositoryFindOne(NOM_MAPA_ROTEIRO)
				.simulaMapaRoteiroRepositoryFindByNomMapaRoteiroAndNomOrigemAndNomDestino(
						NOM_MAPA_ROTEIRO, NOM_ORIGEM, NOM_DESTINO);
		
		// executa o método
		mapaRoteiroService.gravaMapaRoteiro(mapaRoteiroTO);
		
		// verificações
		verify.validaQuatidadesSaveMapaRoteiro(0)
			.validaSaveRoteiro(1, given.getRoteiro(), DISTANCIA);
	}
	
	@Test(expected = NullPointerException.class)
	public void testRemoveMapaRoteiroNomMapaRoteiroNull() {
		mapaRoteiroService.removeMapaRoteiro(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testRemoveMapaRoteiroMapaRoteiroNull() {
		when.simulaMapaRoteiroRepositoryFindOne(NOM_MAPA_ROTEIRO);
		mapaRoteiroService.removeMapaRoteiro(NOM_MAPA_ROTEIRO);
	}
	
	@Test
	public void testRemoveMapaRoteiroRoteirosNull() {
		// cria os mock
		given.createMapaRoteiro();
		// eventos
		when.simulaMapaRoteiroRepositoryFindOne(NOM_MAPA_ROTEIRO);
		// Executa o método
		mapaRoteiroService.removeMapaRoteiro(NOM_MAPA_ROTEIRO);
		// validações
		verify.validaDeleteRoteiros(0).validaDeleteMapaRoteiro(given.getMapaRoteiro());
	}
	
	@Test
	public void testRemoveMapaRoteiro() {
		// cria os mock
		given.createRoteiros().createMapaRoteiro();
		// eventos
		when.simulaMapaRoteiroRepositoryFindOne(NOM_MAPA_ROTEIRO);
		when.simulaMapaRoteiroRepositoryFindAllByNomMapaRoteiro(NOM_MAPA_ROTEIRO);
		// executa o método
		mapaRoteiroService.removeMapaRoteiro(NOM_MAPA_ROTEIRO);
		// verificações
		verify.validaDeleteRoteiros(1).validaDeleteMapaRoteiro(given.getMapaRoteiro());
	}
	
	/**
	 * Classe para criar os mocks.
	 *
	 * @author Vinicius A Gai
	 * @since 06/10/2015
	 */
	private class Given {

		private static final long COD_ROTEIRO = 1L;
		private MapaRoteiroTO mapaRoteiroTO;
		private MapaRoteiro mapaRoteiro;
		private Roteiro roteiro;
		private List<Roteiro> roteiros;

		public Given createMapaRoteiroTO() {
			mapaRoteiroTO = new MapaRoteiroTO();
			mapaRoteiroTO.setNomMapaRoteiro(NOM_MAPA_ROTEIRO);
			
			// cria os roteiros
			final List<RoteiroTO> roteirosTO = new ArrayList<RoteiroTO>();
			final RoteiroTO roteiroTO = new RoteiroTO();
			
			roteiroTO.setNomOrigem(NOM_ORIGEM);
			roteiroTO.setNomDestino(NOM_DESTINO);
			roteiroTO.setDistancia(DISTANCIA);
			
			roteirosTO.add(roteiroTO);
			mapaRoteiroTO.setRoteirosTO(roteirosTO);
			
			return this;
		}

		public Given createMapaRoteiro() {
			mapaRoteiro = new MapaRoteiro();
			mapaRoteiro.setNomMapaRoteiro(NOM_MAPA_ROTEIRO);
			return this;
		}
		
		public Given createRoteiro() {
			roteiro = new Roteiro();
			roteiro.setCodRoteiro(COD_ROTEIRO);
			roteiro.setNomMapaRoteiro(NOM_MAPA_ROTEIRO);
			roteiro.setNomOrigem(NOM_ORIGEM);
			roteiro.setNomDestino(NOM_DESTINO);
			roteiro.setVlrDistancia(DISTANCIA);
			return this;
		}
		
		public Given createRoteiros() {
			
			roteiros = new ArrayList<Roteiro>();
			
			final Roteiro roteiro = new Roteiro();
			roteiro.setCodRoteiro(COD_ROTEIRO);
			roteiro.setNomMapaRoteiro(NOM_MAPA_ROTEIRO);
			roteiro.setNomOrigem(NOM_ORIGEM);
			roteiro.setNomDestino(NOM_DESTINO);
			roteiro.setVlrDistancia(DISTANCIA);
			
			roteiros.add(roteiro);
			
			return this;
		}
		
		public MapaRoteiro getMapaRoteiro() {
			return mapaRoteiro;
		}

		public Roteiro getRoteiro() {
			return roteiro;
		}

		public MapaRoteiroTO getMapaRoteiroTO() {
			return mapaRoteiroTO;
		}

		public List<Roteiro> getRoteiros() {
			return roteiros;
		}
		
	}
	
	/**
	 * Classe para simular os eventos.
	 *
	 * @author Vinicius A Gai
	 * @since 06/10/2015
	 */
	private class When {
		
		public When simulaMapaRoteiroRepositoryFindOne(final String nomMapaRoteiro) {
			Mockito.when(mapaRoteiroRepository.findOne(nomMapaRoteiro)).thenReturn(given.getMapaRoteiro());
			return this;
		}
		
		public When simulaMapaRoteiroRepositoryFindByNomMapaRoteiroAndNomOrigemAndNomDestino(final String nomMapaRoteiro, final String nomOrigem, final String nomDestino) {
			Mockito.when(roteiroRepository.findByNomMapaRoteiroAndNomOrigemAndNomDestino(nomMapaRoteiro, nomOrigem, nomDestino)).thenReturn(given.getRoteiro());
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
	 * @since 06/10/2015
	 */
	private class Verify {
		
		public Verify validaQuatidadesSaveMapaRoteiro(final int times) {
			Mockito.verify(mapaRoteiroRepository, Mockito.times(times)).save(Matchers.any(MapaRoteiro.class));
			return this;
		}
		
		public Verify validaSaveRoteiro(final int times, final Roteiro roteiroExistente, final Double novaDistancia) {
			Mockito.verify(roteiroRepository, Mockito.times(times)).save(roteiroCaptor.capture());
			
			final Roteiro roteiroNew = roteiroCaptor.getValue();
			
			Assert.assertEquals("A distancia deve ser igual", novaDistancia, roteiroNew.getVlrDistancia());
			
			// Caso roteiro ja exista na base, verifica se manteve o mesmo ID.
			if (roteiroExistente != null) {
				Assert.assertEquals("O código do roteiro deve ser o mesmo que o localizado no banco.", roteiroExistente.getCodRoteiro(), roteiroNew.getCodRoteiro()); 
			} else {
				Assert.assertNull("O código do roteiro deve ser nulo.", roteiroNew.getCodRoteiro());
			}
			
			return this;
		}
		
		public Verify validaDeleteRoteiros(final int times) {
			Mockito.verify(roteiroRepository, Mockito.times(times)).delete(Matchers.anyCollectionOf(Roteiro.class));
			return this;
		}
		
		public Verify validaDeleteMapaRoteiro(final MapaRoteiro mapaRoteiro) {
			Mockito.verify(mapaRoteiroRepository, Mockito.times(1)).delete(Matchers.eq(mapaRoteiro));
			return this;
		}
		
	}
}
