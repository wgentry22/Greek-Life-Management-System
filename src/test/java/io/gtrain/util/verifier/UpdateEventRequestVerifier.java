package io.gtrain.util.verifier;

import io.gtrain.util.helper.TestRoutes;
import io.gtrain.util.verifier.base.PutRequestEndpointVerifier;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author William Gentry
 */
public class UpdateEventRequestVerifier extends PutRequestEndpointVerifier {

	public UpdateEventRequestVerifier(WebTestClient webClient, String token) {
		super(webClient, TestRoutes.EVENTS, token);
	}
}
