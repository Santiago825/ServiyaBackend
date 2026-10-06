package com.ServiYa.serviya.negocio.servicios.auth;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * RESTAURADO (el .java estaba vacío; recuperado del JAR) y ENDURECIDO:
 *  - El secreto ya NO está en el código: se lee de la propiedad jwt.secret (variable de
 *    entorno JWT_SECRET). El valor que estaba commiteado debe considerarse comprometido.
 *  - Falla al arrancar si falta o es demasiado corto (HS256 exige >= 256 bits).
 *  - La expiración es configurable (jwt.expiration-ms).
 */
@Service
public class JwtService {

	private final Key key;
	private final long expirationMs;

	public JwtService(@Value("${jwt.secret:}") String secretBase64,
			@Value("${jwt.expiration-ms:86400000}") long expirationMs) {
		if (secretBase64 == null || secretBase64.isBlank()) {
			throw new IllegalStateException(
					"Falta la propiedad jwt.secret (variable de entorno JWT_SECRET). "
							+ "Genera una con: openssl rand -base64 48");
		}
		byte[] keyBytes;
		try {
			keyBytes = Decoders.BASE64.decode(secretBase64.trim());
		} catch (RuntimeException e) {
			throw new IllegalStateException("jwt.secret debe estar codificado en Base64.", e);
		}
		if (keyBytes.length < 32) {
			throw new IllegalStateException("jwt.secret es demasiado corto: se requieren al menos 32 bytes "
					+ "(openssl rand -base64 48).");
		}
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.expirationMs = expirationMs;
	}

	public String getToken(UserDetails user) {
		Map<String, Object> extra = new HashMap<>();
		extra.put("rol", user.getAuthorities().stream().findFirst().map(Object::toString).orElse(""));
		return buildToken(extra, user);
	}

	private String buildToken(Map<String, Object> extraClaims, UserDetails user) {
		long now = System.currentTimeMillis();
		return Jwts.builder().setClaims(extraClaims).setSubject(user.getUsername()).setIssuedAt(new Date(now))
				.setExpiration(new Date(now + expirationMs)).signWith(key, SignatureAlgorithm.HS256).compact();
	}

	/** Lanza JwtException si el token es inválido, está manipulado o expiró. */
	public String getUsernameFromToken(String token) {
		return getClaim(token, Claims::getSubject);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private Claims getAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		return claimsResolver.apply(getAllClaims(token));
	}

	private boolean isTokenExpired(String token) {
		return getClaim(token, Claims::getExpiration).before(new Date());
	}
}
