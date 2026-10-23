package com.ServiYa.serviya.negocio.servicios.dashboardService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.ServiYa.serviya.modelos.modelos.contrato.EstadoContrato;
import com.ServiYa.serviya.modelos.modelos.dashboard.DashboardColaboradorDTO.ContratoProximo;
import com.ServiYa.serviya.modelos.modelos.dashboard.DashboardColaboradorDTO.Contratos;
import com.ServiYa.serviya.modelos.modelos.dashboard.DashboardColaboradorDTO.Ingresos;
import com.ServiYa.serviya.modelos.modelos.dashboard.DashboardColaboradorDTO.MesIngreso;
import com.ServiYa.serviya.modelos.modelos.dashboard.DashboardColaboradorDTO.Resenas;

/**
 * Cálculos puros del dashboard (sin base de datos ni Spring): reciben datos ya leídos y la fecha de
 * hoy, así se pueden probar con facilidad y el servicio solo orquesta.
 */
public final class EstadisticasColaborador {

	public static final int MESES = 6;
	public static final int MAX_PROXIMOS = 5;

	private EstadisticasColaborador() {
	}

	public static Contratos contratos(List<DatoContrato> datos, LocalDate hoy) {
		long pendientes = 0, aceptados = 0, rechazados = 0, enCurso = 0, porIniciar = 0, finalizados = 0;
		for (DatoContrato c : datos) {
			switch (c.estado()) {
			case PENDIENTE -> pendientes++;
			case RECHAZADO -> rechazados++;
			case ACEPTADO -> {
				aceptados++;
				if (c.inicio() != null && c.inicio().isAfter(hoy)) {
					porIniciar++;
				} else if (c.fin() != null && c.fin().isBefore(hoy)) {
					finalizados++;
				} else {
					enCurso++;
				}
			}
			}
		}
		long respondidos = aceptados + rechazados;
		Double tasa = respondidos == 0 ? null : redondear1(aceptados * 100.0 / respondidos);
		return new Contratos(datos.size(), pendientes, aceptados, rechazados, enCurso, porIniciar, finalizados,
				tasa);
	}

	/** Valor aceptado en total y por mes (últimos {@link #MESES}, el actual incluido; según fecha de inicio). */
	public static Ingresos ingresos(List<DatoContrato> datos, LocalDate hoy) {
		YearMonth actual = YearMonth.from(hoy);
		List<MesIngreso> porMes = new ArrayList<>();
		for (int i = MESES - 1; i >= 0; i--) {
			YearMonth mes = actual.minusMonths(i);
			BigDecimal total = BigDecimal.ZERO;
			long cantidad = 0;
			for (DatoContrato c : datos) {
				if (c.estado() == EstadoContrato.ACEPTADO && c.inicio() != null && YearMonth.from(c.inicio()).equals(mes)) {
					total = total.add(precioDe(c.precio()));
					cantidad++;
				}
			}
			porMes.add(new MesIngreso(mes.toString(), dinero(total), cantidad));
		}
		BigDecimal totalAceptado = datos.stream().filter(c -> c.estado() == EstadoContrato.ACEPTADO)
				.map(c -> precioDe(c.precio())).reduce(BigDecimal.ZERO, BigDecimal::add);
		return new Ingresos(dinero(totalAceptado), porMes.get(porMes.size() - 1).total(), porMes);
	}

	/** Aceptados que están en curso o por iniciar, los que empiezan antes primero. */
	public static List<ContratoProximo> proximos(List<DatoContrato> datos, LocalDate hoy) {
		return datos.stream()
				.filter(c -> c.estado() == EstadoContrato.ACEPTADO && c.fin() != null && !c.fin().isBefore(hoy))
				.sorted(Comparator.comparing(DatoContrato::inicio, Comparator.nullsLast(Comparator.naturalOrder())))
				.limit(MAX_PROXIMOS)
				.map(c -> new ContratoProximo(c.idContrato(), c.nombreContrato(), c.nombreContratante(), c.inicio(), c.fin()))
				.toList();
	}

	/** @param porPuntuacion  puntuación (1 a 5) -> cantidad de reseñas con esa puntuación */
	public static Resenas resenas(Map<Integer, Long> porPuntuacion) {
		List<Long> distribucion = new ArrayList<>();
		long total = 0, suma = 0;
		for (int estrellas = 1; estrellas <= 5; estrellas++) {
			long n = porPuntuacion.getOrDefault(estrellas, 0L);
			distribucion.add(n);
			total += n;
			suma += n * estrellas;
		}
		Double promedio = total == 0 ? null : redondear1((double) suma / total);
		return new Resenas(total, promedio, distribucion);
	}

	/** "precio" se guarda como texto: lo ilegible o vacío cuenta como 0 en vez de romper el resumen. */
	static BigDecimal precioDe(String precio) {
		if (precio == null || precio.isBlank()) {
			return BigDecimal.ZERO;
		}
		try {
			return new BigDecimal(precio.trim());
		} catch (NumberFormatException e) {
			return BigDecimal.ZERO;
		}
	}

	private static BigDecimal dinero(BigDecimal valor) {
		return valor.setScale(2, RoundingMode.HALF_UP);
	}

	private static double redondear1(double valor) {
		return BigDecimal.valueOf(valor).setScale(1, RoundingMode.HALF_UP).doubleValue();
	}
}
