package io.gtrain.security.token.interfaces;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.Authentication;

/**
 * @author William Gentry
 */
public interface TokenSignerService {

    SignedJWT getSignedToken(Authentication authentication);
}
