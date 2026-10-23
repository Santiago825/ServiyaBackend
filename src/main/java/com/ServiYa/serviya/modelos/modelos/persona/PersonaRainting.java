package com.ServiYa.serviya.modelos.modelos.persona;

public interface PersonaRainting {
    Long getIdPersona();
    String getNombre();
    byte[] getFoto();
    String getMunicipio();
    String getServicio();
    /** Promedio de estrellas (1 a 5, un decimal). */
    Double getPromedio();
    Long getNumeroResenas();

}
