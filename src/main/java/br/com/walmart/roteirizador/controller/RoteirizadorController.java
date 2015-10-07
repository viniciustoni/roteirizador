package br.com.walmart.roteirizador.controller;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.walmart.roteirizador.service.MapaRoteiroService;
import br.com.walmart.roteirizador.service.RoteirizadorService;
import br.com.walmart.roteirizador.to.ConsultaRoteiroTO;
import br.com.walmart.roteirizador.to.MapaRoteiroTO;
import br.com.walmart.roteirizador.to.RetornoConsultaRoteiroTO;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * Classe contendo os métodos de serviço para o roteirizador.
 *
 * @author Vinicius A Gai
 * @since 05/10/2015
 */
@RestController
public class RoteirizadorController {

	private final Logger logger = LoggerFactory
			.getLogger(RoteirizadorController.class);

	@Autowired
	private MapaRoteiroService mapaRoteiroService;
	
	@Autowired
	private RoteirizadorService roteirizadorService;

	/**
	 * Consulta uma malha de roteiro.
	 * 
	 * @param malha
	 * @return Dados da consulta da malha
	 */
	@RequestMapping(value = "/roteirizador/roteiro/removeRoteiro", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Remove um roteiro", notes = "Remove um roteiro.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Roteiro deletado com sucesso"), })
	@Transactional
	public @ResponseBody void removeRoteiro(
			@RequestBody @NotEmpty String nomMapaRoteiro) {
		logger.info("Apagando roteiro.");
		mapaRoteiroService.removeMapaRoteiro(nomMapaRoteiro);
	}
	
	/**
	 * Consulta uma malha de roteiro.
	 * 
	 * @param malha
	 * @return Dados da consulta da malha
	 */
	@RequestMapping(value = "/roteirizador/roteiro/consultaRoteiro", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consulta um roteiro", notes = "Consulta por um roteiro.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Cadastro efetuado com sucesso"), })
	@Transactional
	public @ResponseBody RetornoConsultaRoteiroTO consultaRoteiro(
			@RequestBody @Valid ConsultaRoteiroTO consultaRoteiroTO) {
		logger.info("Roteirização.");
		final RetornoConsultaRoteiroTO retornoConsultaRoteiroTO = roteirizadorService.calculaRoteiro(consultaRoteiroTO);
		return retornoConsultaRoteiroTO;
	}

	/**
	 * Cadastra uma malha de roteiro.
	 * 
	 * @param malha
	 */
	@RequestMapping(value = "/roteirizador/roteiro/cadastraMalha", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Cadastra a malha de endereços", notes = "Cadastra uma malha para roteirização.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Cadastro efetuado com sucesso"), })
	@Transactional
	public @ResponseBody void cadastraMalha(@RequestBody @Valid MapaRoteiroTO malhaTO) {
		logger.info("cadastraMalha.");
		mapaRoteiroService.gravaMapaRoteiro(malhaTO);
	}

}
