package io.gtrain.util.verifier;

import io.gtrain.util.helper.TestRoutes;
import io.gtrain.util.verifier.base.GetRequestEndpointVerifier;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author William Gentry
 */
public class ViewMemberProfileRequestVerifier extends GetRequestEndpointVerifier {

	public ViewMemberProfileRequestVerifier(WebTestClient webClient, String token) {
		super(webClient, TestRoutes.PROFILES + "/mem/member-id", token);
	}
}
