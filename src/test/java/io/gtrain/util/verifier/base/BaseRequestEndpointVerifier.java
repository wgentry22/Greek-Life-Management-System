package io.gtrain.util.verifier.base;

import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author William Gentry
 */
abstract class BaseRequestEndpointVerifier extends EndpointBase implements EndpointVerifier{

	protected EntityExchangeResult<byte[]> result;

	BaseRequestEndpointVerifier(WebTestClient webClient, String endpoint, String token) {
		super(endpoint, token);
	}
}
