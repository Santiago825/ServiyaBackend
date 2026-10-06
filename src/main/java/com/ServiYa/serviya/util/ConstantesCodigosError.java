package com.ServiYa.serviya.util;

public class ConstantesCodigosError {
    public static final String CODIGO_EXITO = "OK";
    public static final String MENSAJE_NO_ESPECIFICADO = "Mensaje no especificado.";
    public static final String CODIGO_ERROR_NO_CONTROLADO = "ERR_NO_CTRL";
    public static final String MENSAJE_CODIGO_EXITO = "Operación realizada exitosamente.";
    public static final String MENSAJE_CODIGO_ERROR_NO_CONTROLADO = "(MSJ999) Error no controlado.";
    public static final String CODIGO_DATOS_NO_ENCONTRADOS = "INF_DAT_NO_ENC";
    public static final String MENSAJE_LOGIN_NO_ENOCNTRADO = "Usuario o contraseña incorrectos.";

    // PATH ARCHIVOS
    public static final String MENSAJE_ARCHIVO_CV_NO_ENCONTRADO = "No tiene hoja de vida.";
    public static final String MENSAJE_ARCHIVO_CONTRATO_NO_ENCONTRADO = "Contrato no subido.";

    // PATH CONTRATOS
    public static final String MENSAJE_CONTRATOS_NO_ENCONTRADO = "El usuario no tiene contratos registrados.";
    public static final String MENSAJE_DETALLE_CONTRATOS_NO_ENCONTRADO = "No hay información del contrato.";

    // PATH DEPARTAMENTO
    public static final String MENSAJE_DEPATAMENTOS_NO_ENCONTRADO = "No se encontraron departamentos.";

    // PATH MUNICIPIO
    public static final String MENSAJE_MUNICIPIOS_NO_ENCONTRADO = "No se encontraron municipios.";

    // PATH SERVICIOS
    public static final String MENSAJE_SERVICIOS_NO_ENCONTRADO = "No se encontraron servicios.";
    public static final String MENSAJE_COLABORADORES_NO_ENCONTRADO = "No se encontraron colaboradores.";
    public static final String MENSAJE_DETALLE_COLABORADORES_NO_ENCONTRADO = "No se encontró el detalle del colaborador.";
    public static final String MENSAJE_NO_SEGUIR_COLABBORADOR = "No se pudo dejar de seguir al colaborador.";
    public static final String MENSAJE_SEGUIR_COLABBORADOR = "Ya estás siguiendo este colaborador.";

    // PATH TIPO DOCUMENTO
    public static final String MENSAJE_TIPO_DOCUMENTO_NO_ENCONTRADOS = "No se encontraron tipos de documentos.";

    // PATH USUARIO
    public static final String MENSAJE_USUARIOS_NO_ENCONTRADOS = "No se encontraron usuarios.";
    public static final String MENSAJE_REGISTRO_USUARIO_EXISTENTE = "El usuario ya existe.";
    public static final String MENSAJE_REGISTRO_USUARIO_NO_CREADO = "No se pudo crear el usuario.";
    public static final String MENSAJE_COLABORADORES_NO_SEGUIDOS = "No has seguido a ningún colaborador.";
    public static final String MENSAJE_CONTRATANTE_NO_SEGUIDOR = "Ningún contratante te ha seguido.";

    // ---- Seguridad / validación / errores HTTP (añadidos) ----
    public static final String CODIGO_LOGIN_INVALIDO = "ERR_LOGIN";
    public static final String CODIGO_NO_AUTENTICADO = "ERR_NO_AUTENTICADO";
    public static final String MENSAJE_NO_AUTENTICADO = "Debes iniciar sesión para continuar.";
    public static final String CODIGO_SESION_EXPIRADA = "ERR_SESION_EXPIRADA";
    public static final String MENSAJE_SESION_EXPIRADA = "Tu sesión expiró. Inicia sesión de nuevo.";
    public static final String CODIGO_PROHIBIDO = "ERR_PROHIBIDO";
    public static final String MENSAJE_PROHIBIDO = "No tienes permisos para realizar esta acción.";
    public static final String CODIGO_VALIDACION = "ERR_VALIDACION";
    public static final String MENSAJE_VALIDACION = "Hay datos inválidos en la solicitud.";
    public static final String CODIGO_SOLICITUD_INVALIDA = "ERR_SOLICITUD";
    public static final String CODIGO_RECURSO_NO_ENCONTRADO = "ERR_NO_ENCONTRADO";
    public static final String MENSAJE_RECURSO_NO_ENCONTRADO = "Recurso no encontrado.";
    public static final String CODIGO_CONFLICTO = "ERR_CONFLICTO";
    // Códigos de duplicado (el frontend tenía CORREO/DOCUMENTO intercambiados)
    public static final String CODIGO_USUARIO_EXISTE = "ERR_USU_EXI";
    public static final String CODIGO_CORREO_EXISTE = "ERR_USU_COR_EXI";
    public static final String MENSAJE_CORREO_EXISTE = "El correo ya está registrado.";
    public static final String CODIGO_DOCUMENTO_EXISTE = "ERR_USU_DOC_EXI";
    public static final String MENSAJE_DOCUMENTO_EXISTE = "El documento ya está registrado.";
    public static final String MENSAJE_SERVICIO_OBLIGATORIO = "Los colaboradores deben elegir un servicio.";
    public static final String MENSAJE_REGISTRO_EXITOSO = "Registro realizado exitosamente.";
    // Chat
    public static final String CODIGO_CHAT_INVALIDO = "ERR_CHAT";
    public static final String MENSAJE_CHAT_VACIO = "El mensaje no puede estar vacío.";
    public static final String MENSAJE_CHAT_LARGO = "El mensaje no puede superar 1000 caracteres.";
    public static final String MENSAJE_CHAT_DESTINATARIO_INVALIDO = "El destinatario no existe.";
    public static final String MENSAJE_CHAT_A_SI_MISMO = "No puedes enviarte mensajes a ti mismo.";
}
