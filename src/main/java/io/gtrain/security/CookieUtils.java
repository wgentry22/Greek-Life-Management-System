package io.gtrain.security;

import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.web.server.ServerWebExchange;

import java.time.Duration;
import java.util.Optional;

/**
 * @author William Gentry
 */
public class CookieUtils {

	private CookieUtils() {}

	public static final String COOKIE_NAME = "api_token";

	public static ResponseCookie createApiToken(String value) {
		return ResponseCookie.from("api_token", value).path("/").httpOnly(true).secure(true).sameSite("lax").maxAge(Duration.ofDays(1L)).build();
	}

	public static ResponseCookie createCookie(String name, String value) {
		return ResponseCookie.from(name, value).path("/").httpOnly(true).secure(true).sameSite("lax").maxAge(Duration.ofDays(1L)).build();
	}

	public static Optional<String> getJWTFromCookie(ServerWebExchange exchange) {
		HttpCookie cookie = exchange.getRequest().getCookies().getFirst(COOKIE_NAME);
		return cookie == null ? Optional.empty() : Optional.of(cookie.getValue());
	}
}
