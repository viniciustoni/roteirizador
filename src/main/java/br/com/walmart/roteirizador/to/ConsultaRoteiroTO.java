package br.com.walmart.roteirizador.to;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Classe para consulta de roteiro.
 * 
 * @author Vinicius A Gai
 * @since 05/10/2015
 *
 */
@ApiModel(description = "Dados de consulta de roteiro")
@JsonInclude(Include.NON_NULL)
public class ConsultaRoteiroTO implements Serializable {

	private static final long serialVersionUID = -103320634692993006L;

	@ApiModelProperty(value = "Nome do mapa de roteiro")
	@NotEmpty
	private String nomMapaRoteiro;

	@ApiModelProperty(value = "Nome do roteiro de origem")
	@NotEmpty
	private String nomOrigem;

	@ApiModelProperty(value = "Nome do roteiro de destino")
	@NotEmpty
	private String nomDestino;

	@ApiModelProperty(value = "Autonomia do veiculo")
	@NotNull
	private BigDecimal vlrAutonomia;

	@ApiModelProperty(value = "Valor do combustivel")
	@NotNull
	private BigDecimal vlrCombustivel;

	public String getNomMapaRoteiro() {
		return nomMapaRoteiro;
	}

	public void setNomMapaRoteiro(String nomMapaRoteiro) {
		this.nomMapaRoteiro = nomMapaRoteiro;
	}

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

	public BigDecimal getVlrAutonomia() {
		return vlrAutonomia;
	}

	public void setVlrAutonomia(BigDecimal vlrAutonomia) {
		this.vlrAutonomia = vlrAutonomia;
	}

	public BigDecimal getVlrCombustivel() {
		return vlrCombustivel;
	}

	public void setVlrCombustivel(BigDecimal vlrCombustivel) {
		this.vlrCombustivel = vlrCombustivel;
	}

}
