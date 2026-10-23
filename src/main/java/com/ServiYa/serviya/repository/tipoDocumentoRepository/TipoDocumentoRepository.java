package com.ServiYa.serviya.repository.tipoDocumentoRepository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.ServiYa.serviya.modelos.entity.tipoDocumento.TipoDocumento;

/** Catálogo de solo lectura: se expone únicamente lo que el servicio usa. */
public interface TipoDocumentoRepository extends Repository<TipoDocumento, Long> {

	List<TipoDocumento> findAll();
}
