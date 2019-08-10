package io.gtrain.security.token;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import io.gtrain.domain.model.GlmsAuthenticationToken;
import io.gtrain.properties.KeyStoreProperties;
import io.gtrain.security.token.interfaces.TokenVerifierService;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

/**
 * @author William Gentry
 */
class TokenVerifierServiceImpl implements TokenVerifierService {

	private final Logger logger = Loggers.getLogger(getClass());
	private final RSASSAVerifier verifier;

	public TokenVerifierServiceImpl(KeyStoreProperties keyStoreProperties) {
		try {
			KeyStore keyStore = KeyStore.getInstance(keyStoreProperties.getType());
			keyStore.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(keyStoreProperties.getPath()), keyStoreProperties.getPassword());
			this.verifier = new RSASSAVerifier(((RSAPublicKey) keyStore.getCertificate(keyStoreProperties.getAlias()).getPublicKey()));
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			logger.error("Failed to load KeyStore: {}", e.getMessage(), e);
			throw new IllegalStateException("Failed to load KeyStore: " + e.getMessage(), e);
		}
	}

	@Override
	public Mono<Authentication> getAuthentication(SignedJWT jwt) {
		if (jwt == null)
			throw new IllegalStateException("Token must be present");
		try {
			if (jwt.verify(verifier)) {
				return Mono.just(new GlmsAuthenticationToken(jwt.getJWTClaimsSet()));
			}
		} catch (JOSEException e) {
			logger.error("Failed to verify JWT: {}", e.getMessage(), e);
			throw new IllegalStateException("Failed to verify JWT: See logs");
		} catch (ParseException e) {
			logger.error("Failed to read JWT Claims: {}", e.getMessage(), e);
			throw new IllegalArgumentException("Failed to read JWT Claims: See logs");
		}
		return null;
	}
}
