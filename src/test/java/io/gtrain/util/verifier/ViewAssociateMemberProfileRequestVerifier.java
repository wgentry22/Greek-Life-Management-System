package io.gtrain.util.verifier;

import io.gtrain.util.verifier.base.GetRequestEndpointVerifier;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author William Gentry
 */
public class ViewAssociateMemberProfileRequestVerifier extends GetRequestEndpointVerifier {

	public ViewAssociateMemberProfileRequestVerifier(WebTestClient webClient, String token) {
		super(webClient, "/api/profiles/am/member-id", token);
	}
}
