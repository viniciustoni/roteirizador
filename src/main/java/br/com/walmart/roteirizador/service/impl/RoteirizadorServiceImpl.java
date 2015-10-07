package br.com.walmart.roteirizador.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.walmart.roteirizador.converter.RoteiroTOConverter;
import br.com.walmart.roteirizador.entity.MapaRoteiro;
import br.com.walmart.roteirizador.entity.Roteiro;
import br.com.walmart.roteirizador.repository.MapaRoteiroRepository;
import br.com.walmart.roteirizador.repository.RoteiroRepository;
import br.com.walmart.roteirizador.service.RoteirizadorService;
import br.com.walmart.roteirizador.to.ConsultaRoteiroTO;
import br.com.walmart.roteirizador.to.RetornoConsultaRoteiroTO;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * Implementação dos métodos de roteirização.
 *
 * @author Vinicius A Gai
 * @since 06/10/2015
 */
@Service
public class RoteirizadorServiceImpl implements RoteirizadorService {

	private final Logger logger = LoggerFactory.getLogger(RoteirizadorServiceImpl.class);
	
	private static final String MENSAGEM_RETORNO = "A rota recomendada é a {0}, com uma distância de {1} Km a um custo de {2}.";
	
	@Autowired
	private MapaRoteiroRepository mapaRoteiroRepository;
	
	@Autowired
	private RoteiroRepository roteiroRepository;
	
	@Override
	public RetornoConsultaRoteiroTO calculaRoteiro(final ConsultaRoteiroTO consultaRoteiroTO) {
		
		logger.debug("Calculo de roteiro.");
		Validate.notNull(consultaRoteiroTO, "Os dados de consulta de roteiro são obrigatórios.");

		logger.debug("Busca pelos dados da mapa de roteiro.");
		final MapaRoteiro mapaRoteiro = mapaRoteiroRepository.findOne(consultaRoteiroTO.getNomMapaRoteiro());
		Validate.notNull(mapaRoteiro, "Nenhuma informação de malha logistica encontrada para o nome da malha informado.");
		
		logger.debug("Busca pelos dados do roteiro da mapa de roteiro.");
		final List<Roteiro> roteiros = roteiroRepository.findAllByNomMapaRoteiro(mapaRoteiro.getNomMapaRoteiro());
		Validate.notEmpty(roteiros, "Nenhum roteiro localizado para a malha informada.");
		
		logger.debug("Calcula o menor caminho.");
		final RetornoConsultaRoteiroTO retornoConsultaRoteiroTO = menorCaminho(roteiros, consultaRoteiroTO);
		
		return retornoConsultaRoteiroTO;
		
	}
	
	/**
	 * Efetua o calculo do menor caminho.
	 * 
	 * @param roteiros
	 * @param consultaRoteiroTO
	 * @return
	 */
	private RetornoConsultaRoteiroTO menorCaminho(final List<Roteiro> roteiros, final ConsultaRoteiroTO consultaRoteiroTO) {
		
		RetornoConsultaRoteiroTO retornoConsultaRoteiroTO = null;
		
		final Graph<String, Roteiro> graph = new SparseMultigraph<String, Roteiro>();
		final Transformer<Roteiro, Double> roteiroTransformer = new Transformer<Roteiro, Double>() {
			@Override
			public Double transform(Roteiro roteiro) {
				return roteiro.getVlrDistancia();
			}
		};
		
		// Cria os roteiros
		for(Roteiro roteiro : roteiros) {
			logger.debug(MessageFormat.format("Criando rota {0} -> {1} distancia: {2}", roteiro.getNomOrigem(), roteiro.getNomDestino(), roteiro.getVlrDistancia()));
			graph.addEdge(roteiro, roteiro.getNomOrigem(), roteiro.getNomDestino(), EdgeType.DIRECTED);
		}
		
		// Criando o objeto de roteirizador
		final DijkstraShortestPath<String, Roteiro> dijkstra = new DijkstraShortestPath<String, Roteiro>(graph, roteiroTransformer);
		final Double distancia = dijkstra.getDistance(consultaRoteiroTO.getNomOrigem(), consultaRoteiroTO.getNomDestino()).doubleValue();
		final List<Roteiro> menorCaminho = dijkstra.getPath(consultaRoteiroTO.getNomOrigem(), consultaRoteiroTO.getNomDestino());
		
		// monta os dados para o retorno.
		retornoConsultaRoteiroTO = montaRetornoConsultaRoteiroTO(BigDecimal.valueOf(distancia), menorCaminho, consultaRoteiroTO.getVlrAutonomia(), consultaRoteiroTO.getVlrCombustivel());
		
		return retornoConsultaRoteiroTO;
	}
	
	/**
	 * Monta os dados para o retorno da roteirização.
	 * 
	 * @param distancia
	 * @param roteiros
	 * @param autonomia
	 * @param vlrCombustivel
	 * @return
	 */
	private RetornoConsultaRoteiroTO montaRetornoConsultaRoteiroTO(final BigDecimal distancia, final List<Roteiro> roteiros, final BigDecimal autonomia, final BigDecimal vlrCombustivel) {
		
		final RetornoConsultaRoteiroTO retornoConsultaRoteiroTO = new RetornoConsultaRoteiroTO();
		
		// monta os dados para o retorno
		final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("PT", "BR"));
		final List<String> pontosRoteiro = montaCaminho(roteiros);
		final BigDecimal vlrCustoRoteiro = distancia.divide(autonomia).multiply(vlrCombustivel);
		final String mensagem = MessageFormat.format(MENSAGEM_RETORNO, pontosRoteiro.toString(), distancia.toString(), numberFormat.format(vlrCustoRoteiro.doubleValue()));
		
		logger.debug(mensagem);
		
		retornoConsultaRoteiroTO.setMenssagemRetorno(mensagem);
		retornoConsultaRoteiroTO.setRota(pontosRoteiro);
		retornoConsultaRoteiroTO.setRoteirosTO(RoteiroTOConverter.converterFromListRoteiro(roteiros));
		retornoConsultaRoteiroTO.setVlrCustoTrajeto(vlrCustoRoteiro);
		
		return retornoConsultaRoteiroTO;
	}
	
	/**
	 * Monta a lista contendo os pontos para o roteiro.
	 * 
	 * @param roteiros
	 * @return
	 */
	private List<String> montaCaminho(final List<Roteiro> roteiros) {
		
		List<String> menorCaminho = null;
		int index = 0;
		
		if (CollectionUtils.isNotEmpty(roteiros)) {
			menorCaminho = new ArrayList<String>();
			
			Roteiro roteiro = null;
			while (index < roteiros.size()) {
				
				roteiro = roteiros.get(index);
				menorCaminho.add(roteiro.getNomOrigem());
				
				// Inclui o destino.
				index++;
				if (index == roteiros.size()) {
					menorCaminho.add(roteiro.getNomDestino());
				}
				
			}
			
		}
		
		return menorCaminho;
	}
	
}
