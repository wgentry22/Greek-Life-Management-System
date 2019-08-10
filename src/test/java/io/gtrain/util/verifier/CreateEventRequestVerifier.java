package io.gtrain.util.verifier;

import io.gtrain.util.helper.TestRoutes;
import io.gtrain.util.verifier.base.PostRequestEndpointVerifier;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author William Gentry
 */
public class CreateEventRequestVerifier extends PostRequestEndpointVerifier {

	public CreateEventRequestVerifier(WebTestClient client, String token) {
		super(client, TestRoutes.EVENTS, token);
	}
}
