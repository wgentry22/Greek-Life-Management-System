package io.gtrain.util.verifier;

import io.gtrain.util.helper.TestRoutes;
import io.gtrain.util.verifier.base.GetRequestEndpointVerifier;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author William Gentry
 */
public class ListEventsRequestVerifier extends GetRequestEndpointVerifier {

	public ListEventsRequestVerifier(WebTestClient client, String token) {
		super(client, TestRoutes.EVENTS, token);
	}
}
