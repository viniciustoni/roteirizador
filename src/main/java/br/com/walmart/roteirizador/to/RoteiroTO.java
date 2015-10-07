package br.com.walmart.roteirizador.to;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * To para determinar os dados do Roteiro
 *
 * @author Vinicius A Gai
 * @since 05/10/2015
 */
@ApiModel(description = "Dados de uma determinada rota")
@JsonInclude(Include.NON_NULL)
public class RoteiroTO implements Serializable {

	private static final long serialVersionUID = -2570965777046902310L;

	@ApiModelProperty(value = "Nome do roteiro de origem")
	@NotEmpty
	private String nomOrigem;

	@ApiModelProperty(value = "Nome do roteiro de destino")
	@NotEmpty
	private String nomDestino;

	@ApiModelProperty(value = "Distacia entre os pontos.")
	@NotNull
	private Double distancia;

	public String getNomOrigem() {
		return nomOrigem;
	}

	public void setNomOrigem(String nomOrigem) {
		this.nomOrigem = nomOrigem;
	}

	public String getNomDestino() {
		return nomDestino;
	}

	public void setNomDestino(String nomDestino) {
		this.nomDestino = nomDestino;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

}
