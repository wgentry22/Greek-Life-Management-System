package io.gtrain.security.token;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import io.gtrain.properties.KeyStoreProperties;
import io.gtrain.security.token.interfaces.*;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;

/**
 * @author William Gentry
 */
public class TokenFactory implements TokenGenerator, TokenParser, TokenValidator {

	private final JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256).contentType("JWT").build();
	private final Logger logger = Loggers.getLogger(getClass());
	private final TokenSignerService tokenSignerService;
	private final TokenVerifierService tokenVerifierService;
	private final TokenValidator tokenValidator;
	private final DirectDecrypter decrypter;
	private final DirectEncrypter encrypter;

	public TokenFactory(KeyStoreProperties keyStoreProperties) {
		tokenSignerService = new TokenSignerServiceImpl(keyStoreProperties);
		tokenVerifierService = new TokenVerifierServiceImpl(keyStoreProperties);
		tokenValidator = new DefaultTokenValidator(keyStoreProperties);
		try {
			decrypter = new DirectDecrypter(keyStoreProperties.getSecret().getBytes(StandardCharsets.UTF_8));
			encrypter = new DirectEncrypter(keyStoreProperties.getSecret().getBytes(StandardCharsets.UTF_8));
		} catch (KeyLengthException e) {
			logger.error("Failed to instantiate TokenFactory: {}", e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public String generateToken(Authentication authentication) {
		JWEObject jwe = new JWEObject(header, new Payload(tokenSignerService.getSignedToken(authentication)));
		try {
			jwe.encrypt(encrypter);
		} catch (JOSEException e) {
			logger.error("Failed to verify JWT: {}", e.getMessage(), e);
			throw new IllegalStateException("Failed to verify JWT: See logs");
		}
		return jwe.serialize();
	}

	@Override
	public boolean isValid(String token) {
		return tokenValidator.isValid(token);
	}

	@Override
	public Mono<Authentication> getAuthentication(String token) {
		try {
			JWEObject jwe = JWEObject.parse(token);
			jwe.decrypt(decrypter);
			return tokenVerifierService.getAuthentication(jwe.getPayload().toSignedJWT());
		} catch (ParseException e) {
			logger.error("Failed to parse JWE: {}", e.getMessage(), e);
			throw new IllegalArgumentException("Failed to JWE: See logs");
		} catch (JOSEException e) {
			logger.error("Failed to verify JWE: {}", e.getMessage(), e);
			throw new IllegalStateException("Failed to verify JWE: See logs");
		}
	}
}
