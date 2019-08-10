package io.gtrain.util.verifier.base;

import io.gtrain.security.CookieUtils;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.net.URI;

/**
 * @author William Gentry
 */
public abstract class PostRequestEndpointVerifier extends BaseRequestEndpointVerifier {

	public PostRequestEndpointVerifier(WebTestClient webClient, String endpoint, String token) {
		super(webClient, endpoint, token);
		result = webClient.post().uri(URI.create(endpoint)).cookie(CookieUtils.COOKIE_NAME, token).exchange().expectBody().returnResult();
	}

	@Override
	public boolean isOk() {
		return result.getStatus().equals(HttpStatus.OK);
	}

	@Override
	public boolean isForbidden() {
		return result.getStatus().equals(HttpStatus.FORBIDDEN);
	}
}
