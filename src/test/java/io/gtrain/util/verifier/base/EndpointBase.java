package io.gtrain.util.verifier.base;

/**
 * @author William Gentry
 */
abstract class EndpointBase {

	private final String endpoint;

	private final String token;

	public EndpointBase(String endpoint, String token) {
		this.endpoint = endpoint;
		this.token = token;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public String getToken() {
		return token;
	}

}
