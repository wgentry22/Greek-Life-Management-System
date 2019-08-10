package io.gtrain.util.verifier;

import io.gtrain.util.helper.TestRoutes;
import io.gtrain.util.verifier.base.DeleteRequestEndpointVerifier;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author William Gentry
 */
public class DeleteEventRequestVerifier extends DeleteRequestEndpointVerifier {

	public DeleteEventRequestVerifier(WebTestClient webClient, String token) {
		super(webClient, TestRoutes.EVENTS, token);
	}
}
