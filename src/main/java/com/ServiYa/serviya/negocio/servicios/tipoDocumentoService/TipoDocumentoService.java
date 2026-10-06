package com.ServiYa.serviya.negocio.servicios.tipoDocumentoService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ServiYa.serviya.modelos.entity.tipoDocumento.TipoDocumento;
import com.ServiYa.serviya.modelos.modelos.departamento.DepartamentoDTO;
import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.repository.departamentoRepository.DepartamentoRepository;
import com.ServiYa.serviya.repository.tipoDocumentoRepository.TipoDocumentoRepository;
import com.ServiYa.serviya.util.ConstantesCodigosError;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class TipoDocumentoService {
	private final TipoDocumentoRepository documentoRepository;
	public ListaDTO getTipoDocumento() {
		ListaDTO respuesta;
		try {
			List<TipoDocumento> listado = documentoRepository.findAll();
			respuesta = new ListaDTO();
			if (listado.isEmpty()) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_TIPO_DOCUMENTO_NO_ENCONTRADOS);
				respuesta.setLista(listado);
			} else {
				respuesta.setLista(listado);
				respuesta.setTotalPaginas(listado.size() > 0 ? 1 : 0);
				respuesta.setTotalRegistros(listado.size());
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);

			}

		} catch (Exception e) {
			respuesta = new ListaDTO();
			respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
			respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);

		}
		return respuesta;

	}

}
