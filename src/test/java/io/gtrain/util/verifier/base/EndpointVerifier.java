package io.gtrain.util.verifier.base;

/**
 * @author William Gentry
 */
interface EndpointVerifier {

	boolean isOk();

	boolean isForbidden();
}
