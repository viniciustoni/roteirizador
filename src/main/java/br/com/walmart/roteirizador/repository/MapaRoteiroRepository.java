package br.com.walmart.roteirizador.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.walmart.roteirizador.entity.MapaRoteiro;

/**
 * Repositorio para a entidade de Mapa de roteiro
 * 
 * @author Vinicius A Gai
 *
 */
public interface MapaRoteiroRepository extends CrudRepository<MapaRoteiro, String> {

}
