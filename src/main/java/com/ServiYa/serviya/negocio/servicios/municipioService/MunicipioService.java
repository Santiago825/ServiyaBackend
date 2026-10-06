package com.ServiYa.serviya.negocio.servicios.municipioService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ServiYa.serviya.modelos.modelos.departamento.DepartamentoDTO;
import com.ServiYa.serviya.modelos.modelos.lista.ListaDTO;
import com.ServiYa.serviya.modelos.modelos.municipio.MunicipioDTO;
import com.ServiYa.serviya.repository.MunicipioRepository.MunicipioRepository;
import com.ServiYa.serviya.repository.departamentoRepository.DepartamentoRepository;
import com.ServiYa.serviya.repository.servicioRepository.ServicioRepository;
import com.ServiYa.serviya.util.ConstantesCodigosError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MunicipioService {
	private final MunicipioRepository municipioRepository;

	public ListaDTO getMunicipio(Long id) {
		ListaDTO respuesta;
		try {
			List<MunicipioDTO> listado = municipioRepository.findAllMunicipio(id);
			respuesta = new ListaDTO();
			if (listado.isEmpty()) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_MUNICIPIOS_NO_ENCONTRADO);
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
