package br.com.walmart.roteirizador.to;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * TO para cadastrar a malha de roteiros
 *
 * @author Vinicius A Gai
 * @since
 */
@ApiModel(description = "Dados da mapa de roteiro")
@JsonInclude(Include.NON_NULL)
public class MapaRoteiroTO implements Serializable {

	private static final long serialVersionUID = 7456649387581453252L;

	@ApiModelProperty(value = "Nome do mapa de roteiro")
	@NotEmpty
	private String nomMapaRoteiro;

	@ApiModelProperty(value = "Roteiros da malha")
	@NotEmpty
	private List<RoteiroTO> roteirosTO;

	public String getNomMapaRoteiro() {
		return nomMapaRoteiro;
	}

	public void setNomMapaRoteiro(String nomMapaRoteiro) {
		this.nomMapaRoteiro = nomMapaRoteiro;
	}

	public List<RoteiroTO> getRoteirosTO() {
		return roteirosTO;
	}

	public void setRoteirosTO(List<RoteiroTO> roteirosTO) {
		this.roteirosTO = roteirosTO;
	}

}
