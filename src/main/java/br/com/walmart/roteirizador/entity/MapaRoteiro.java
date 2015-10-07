package br.com.walmart.roteirizador.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidade para determinar o mapa de roteirizacao
 * 
 * @author Vinicius A Gai
 */
@Entity
@Table(name = "MAPA_ROTEIRO")
public class MapaRoteiro implements Serializable {

	private static final long serialVersionUID = 5698701836072372795L;

	@Id
	@Column(name = "NOM_MAPA_ROTEIRO", length = 50, nullable = false)
	private String nomMapaRoteiro;

	public String getNomMapaRoteiro() {
		return nomMapaRoteiro;
	}

	public void setNomMapaRoteiro(String nomMapaRoteiro) {
		this.nomMapaRoteiro = nomMapaRoteiro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nomMapaRoteiro == null) ? 0 : nomMapaRoteiro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapaRoteiro other = (MapaRoteiro) obj;
		if (nomMapaRoteiro == null) {
			if (other.nomMapaRoteiro != null)
				return false;
		} else if (!nomMapaRoteiro.equals(other.nomMapaRoteiro))
			return false;
		return true;
	}

}
