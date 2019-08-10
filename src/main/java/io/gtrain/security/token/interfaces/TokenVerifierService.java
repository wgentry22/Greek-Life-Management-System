package io.gtrain.security.token.interfaces;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface TokenVerifierService {

    Mono<Authentication> getAuthentication(SignedJWT jwt);
}
