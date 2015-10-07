package br.com.walmart.roteirizador.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Classe para representar o roteiro.
 * 
 * @author Vinicius A Gai
 */
@Entity
@Table(name = "roteiro")
public class Roteiro implements Serializable {

	private static final long serialVersionUID = 2939347460177498433L;

	@Id
	@GeneratedValue
	@Column(name = "COD_ROTEIRO", scale = 8)
	private Long codRoteiro;

	@Column(name = "NOM_MAPA_ROTEIRO")
	private String nomMapaRoteiro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "NOM_MAPA_ROTEIRO", name = "NOM_MAPA_ROTEIRO", nullable = false, updatable = false, insertable = false)
	private MapaRoteiro mapaRoteiro;

	@Column(name = "NOM_ORIGEM", length = 50, nullable = false)
	private String nomOrigem;

	@Column(name = "NOM_DESTINO", length = 50, nullable = false)
	private String nomDestino;

	@Column(name = "VLR_DISTANCIA")
	private Double vlrDistancia;

	public Long getCodRoteiro() {
		return codRoteiro;
	}

	public void setCodRoteiro(Long codRoteiro) {
		this.codRoteiro = codRoteiro;
	}

	public String getNomMapaRoteiro() {
		return nomMapaRoteiro;
	}

	public void setNomMapaRoteiro(String nomMapaRoteiro) {
		this.nomMapaRoteiro = nomMapaRoteiro;
	}

	public MapaRoteiro getMapaRoteiro() {
		return mapaRoteiro;
	}

	public void setMapaRoteiro(MapaRoteiro mapaRoteiro) {
		this.mapaRoteiro = mapaRoteiro;
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

	public Double getVlrDistancia() {
		return vlrDistancia;
	}

	public void setVlrDistancia(Double vlrDistancia) {
		this.vlrDistancia = vlrDistancia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codRoteiro == null) ? 0 : codRoteiro.hashCode());
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
		Roteiro other = (Roteiro) obj;
		if (codRoteiro == null) {
			if (other.codRoteiro != null)
				return false;
		} else if (!codRoteiro.equals(other.codRoteiro))
			return false;
		return true;
	}

}
