package com.ServiYa.serviya.negocio.servicios.personaService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ServiYa.serviya.modelos.modelos.persona.PersonaRainting;
import com.ServiYa.serviya.repository.personaRepository.PersonaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonaService {

	private final PersonaRepository personaRepository;

	@Transactional(readOnly = true)
	public List<PersonaRainting> ranking() {
		return personaRepository.findPersonasRaiting();
	}
}
