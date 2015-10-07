package br.com.walmart.roteirizador.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.walmart.roteirizador.converter.MapaRoteiroConverter;
import br.com.walmart.roteirizador.converter.RoteiroConverter;
import br.com.walmart.roteirizador.entity.MapaRoteiro;
import br.com.walmart.roteirizador.entity.Roteiro;
import br.com.walmart.roteirizador.repository.MapaRoteiroRepository;
import br.com.walmart.roteirizador.repository.RoteiroRepository;
import br.com.walmart.roteirizador.service.MapaRoteiroService;
import br.com.walmart.roteirizador.to.MapaRoteiroTO;
import br.com.walmart.roteirizador.to.RoteiroTO;

/**
 * Implementação da classe de serviço.
 * 
 * @author Vinicius A Gai
 */
@Service
public class MapaRoteiroServiceImpl implements MapaRoteiroService {

	private final Logger logger = LoggerFactory
			.getLogger(MapaRoteiroServiceImpl.class);

	@Autowired
	private MapaRoteiroRepository mapaRoteiroRepository;

	@Autowired
	private RoteiroRepository roteiroRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void gravaMapaRoteiro(final MapaRoteiroTO mapaRoteiroTO) {
		logger.debug("Validação do objeto de mapa.");

		Validate.notNull(mapaRoteiroTO, "O objeto de malha é obrigatório.");
		
		logger.debug("Gravaçao do objeto de mapa: " + mapaRoteiroTO.getNomMapaRoteiro());
		// Primeiro grava a malha
		MapaRoteiro mapaRoteiro = mapaRoteiroRepository.findOne(mapaRoteiroTO.getNomMapaRoteiro());
		if (mapaRoteiro == null) {
			mapaRoteiro = MapaRoteiroConverter.converteFromMapaRoteiroTO(mapaRoteiroTO);
			mapaRoteiroRepository.save(mapaRoteiro);
		}

		logger.debug("Gravaçao dos dados de roteiros.");
		// Converter/save a lista de roteiros
		if (CollectionUtils.isNotEmpty(mapaRoteiroTO.getRoteirosTO())) {
			for (RoteiroTO roteiroTO : mapaRoteiroTO.getRoteirosTO()) 
				gravaRoteiro(roteiroTO, mapaRoteiro);
		}
	}
	
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void removeMapaRoteiro(String nomMapaRoteiro) {
		logger.debug("Remoção do objeto de mapa.");

		Validate.notNull(nomMapaRoteiro, "O Nome do mapa é obrigatório.");

		logger.debug("Carrega o roteiro par apagar.");
		final List<Roteiro> roteiros = roteiroRepository.findAllByNomMapaRoteiro(nomMapaRoteiro);
		
		logger.debug("Apaga os roteiro");
		if (CollectionUtils.isNotEmpty(roteiros)) {
			roteiroRepository.delete(roteiros);
		}

		logger.debug("Apaga o mapa de roteiro");
		final MapaRoteiro mapaRoteiro = mapaRoteiroRepository.findOne(nomMapaRoteiro);
		Validate.notNull(mapaRoteiro, "Nenhum mapa de roteiro localizado.");
		mapaRoteiroRepository.delete(mapaRoteiro);
		
	}



	/**
	 * Grava os dados do roteiro.
	 * 
	 * @param roteiroTO
	 * @param mapaRoteiro
	 */
	private void gravaRoteiro(final RoteiroTO roteiroTO, final MapaRoteiro mapaRoteiro) {
		
		// Verifica se já nao existe um roteiro.
		logger.debug("Gravaçao do roteiro na mapa: " + mapaRoteiro.getNomMapaRoteiro());
		Roteiro roteiro = roteiroRepository.findByNomMapaRoteiroAndNomOrigemAndNomDestino(mapaRoteiro.getNomMapaRoteiro(), roteiroTO.getNomOrigem(), roteiroTO.getNomDestino());
		
		// Caso roteiro exista, atualiza a distancia, caso nao exista cria um novo
		if (roteiro != null) {
			roteiro.setVlrDistancia(roteiroTO.getDistancia());
		} else {
			roteiro = RoteiroConverter.converterFromRoteiroTOAndMapaRoteiro(roteiroTO, mapaRoteiro);
		}
		
		roteiroRepository.save(roteiro);
	}

}
