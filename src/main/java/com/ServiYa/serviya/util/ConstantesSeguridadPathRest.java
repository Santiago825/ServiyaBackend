package com.ServiYa.serviya.util;

public class ConstantesSeguridadPathRest {
	public static final String PATH_SERVICIOS_PUBLICOS = "/public";
	public static final String PATH_SERVICIOS_PRIVADOS = "/private";
	
    //Login
    public static final String PATH_LOGIN = "/login";
    public static final String PATH_REGISTRO = "/registro";
    public static final String PATH_VALIDAR_USERNAME = "/validar-username";

    public static final String PATH_DEMO = "/demo";
  //Login
    public static final String PATH_OBTENER_USUARIOS = "/obtener_usuarios";
    public static final String PATH_OBTENER_USUARIO_DETALLE = "/obtener_usuario_detalle";

    public static final String PATH_OBTENER_COLABORADORES_SEGUIDOS = "/obtener_colaboradores_seguidos";
    public static final String PATH_OBTENER_SEGUIDORES_COLABORADOR= "/obtener_seguidores_colaborador";
    public static final String PATH_PERSONAS_RAITING = "/obtener_personas_raiting";


    public static final String PATH_OBTENER_SERVICIOS = "/obtener_servicios";
    public static final String PATH_OBTENER_SERVICIOS_MAS_USOS = "/obtener_servicios_mas_usos";

    public static final String PATH_OBTENER_DEPARTAMENTO = "/obtener_departamento";
    public static final String PATH_OBTENER_MUNICIPIO = "/obtener_municipio";
    public static final String PATH_OBTENER_COLABORADOR = "/obtener_colaborador";
    public static final String PATH_OBTENER_DETALLE_COLABORADOR = "/obtener_detalle_colaborador";
    public static final String PATH_OBTENER_TIPO_DOCUMENTO = "/obtener_documento";
    public static final String PATH_SEGUIR_COLABORADOR = "/seguir_colaborador";
    public static final String PATH_DEJAR_SEGUIR_COLABORADOR = "/dejar_Seeguir_colaborador";
    public static final String PATH_OBTENER_CV = "/obtenerCV";
    public static final String PATH_OBTENER_ARCHIVO_CONTRATO = "/obtener_archivo_contrato";

    public static final String PATH_OBTENER_CONTRATOS = "/obtener_contrato";
    public static final String PATH_OBTENER_CONTRATOS_DETALLE = "/obtener_contrato_detalle";
    public static final String PATH_OBTENER_MENSAJES = "/obtener_mensajes";

    // WebSocket / STOMP (añadidos)
    public static final String PATH_WS = "/ws";
    public static final String WS_APP_PREFIX = "/app";
    public static final String WS_USER_PREFIX = "/user";
    public static final String WS_QUEUE_PREFIX = "/queue";
    public static final String WS_DESTINO_ENVIAR = "/private-message";
    public static final String WS_COLA_MENSAJES = "/queue/messages";
    public static final String WS_COLA_ERRORES = "/queue/errors";
}
