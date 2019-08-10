package io.gtrain.util.verifier;

import io.gtrain.util.helper.TestRoutes;
import io.gtrain.util.verifier.base.PostRequestEndpointVerifier;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author William Gentry
 */
public class CreateProfileRequestVerifier extends PostRequestEndpointVerifier {

	public CreateProfileRequestVerifier(WebTestClient webClient, String token) {
		super(webClient, TestRoutes.PROFILES, token);
	}
}
