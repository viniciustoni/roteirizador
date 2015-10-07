package br.com.walmart.roteirizador.to;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Classe de retorno da consulta de roteiro.
 * 
 * @author Vinicius A Gai
 * @since 05/10/2015
 *
 */
@ApiModel(description = "Retorno da consulta de roteiro")
@JsonInclude(Include.NON_NULL)
public class RetornoConsultaRoteiroTO implements Serializable {

	private static final long serialVersionUID = 8879643930041393147L;

	@ApiModelProperty(value = "Mensagem do retorno")
	private String menssagemRetorno;

	@ApiModelProperty(value = "Valor do custo do rota")
	private BigDecimal vlrCustoTrajeto;

	@ApiModelProperty(value = "dados da rota")
	private List<String> rota;

	@ApiModelProperty(value = "Roteiros da malha")
	private List<RoteiroTO> roteirosTO;

	public String getMenssagemRetorno() {
		return menssagemRetorno;
	}

	public void setMenssagemRetorno(String menssagemRetorno) {
		this.menssagemRetorno = menssagemRetorno;
	}

	public List<String> getRota() {
		return rota;
	}

	public void setRota(List<String> rota) {
		this.rota = rota;
	}

	public BigDecimal getVlrCustoTrajeto() {
		return vlrCustoTrajeto;
	}

	public void setVlrCustoTrajeto(BigDecimal vlrCustoTrajeto) {
		this.vlrCustoTrajeto = vlrCustoTrajeto;
	}

	public List<RoteiroTO> getRoteirosTO() {
		return roteirosTO;
	}

	public void setRoteirosTO(List<RoteiroTO> roteirosTO) {
		this.roteirosTO = roteirosTO;
	}

}
