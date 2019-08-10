package io.gtrain.security.token;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import io.gtrain.properties.KeyStoreProperties;
import io.gtrain.security.token.interfaces.TokenValidator;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.ParseException;

/**
 * @author William Gentry
 */
class DefaultTokenValidator implements TokenValidator {

	private final Logger logger = Loggers.getLogger(getClass());
	private final ConfigurableJWTProcessor jwtProcessor = new DefaultJWTProcessor();

	public DefaultTokenValidator(KeyStoreProperties keyStoreProperties) {
		try {
			KeyStore keyStore = KeyStore.getInstance(keyStoreProperties.getType());
			keyStore.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(keyStoreProperties.getPath()), keyStoreProperties.getPassword());
			jwtProcessor.setJWEKeySelector(new JWEDecryptionKeySelector(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256, new ImmutableSecret(keyStoreProperties.getSecret().getBytes(StandardCharsets.UTF_8))));
			jwtProcessor.setJWSKeySelector(new JWSVerificationKeySelector(JWSAlgorithm.RS512,
					new ImmutableJWKSet(JWKSet.load(keyStore, alias -> keyStoreProperties.getAlias().equals(alias) ? keyStoreProperties.getPassword() : null))));
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			logger.error("Failed to load KeyStore: {}", e.getMessage(), e);
			throw new IllegalStateException("Failed to load KeyStore: " + e.getMessage(), e);
		}

	}

	@Override
	public boolean isValid(String token) {
		try {
			jwtProcessor.process(token, null);
			return true;
		} catch (ParseException | BadJOSEException | JOSEException e) {
			logger.error("Failed to validate JWT: {}", e.getMessage(), e);
			return false;
		}
	}
}
