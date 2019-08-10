package io.gtrain.security.token;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.gtrain.domain.model.GlmsAuthenticationToken;
import io.gtrain.properties.KeyStoreProperties;
import io.gtrain.security.token.interfaces.TokenSignerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author William Gentry
 */
class TokenSignerServiceImpl implements TokenSignerService {

	private final Logger logger = Loggers.getLogger(getClass());
	private final RSASSASigner signer;
	private final JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS512).build();

	public TokenSignerServiceImpl(KeyStoreProperties keyStoreProperties) {
		try {
			KeyStore keyStore = KeyStore.getInstance(keyStoreProperties.getType());
			keyStore.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(keyStoreProperties.getPath()), keyStoreProperties.getPassword());
			this.signer = new RSASSASigner((PrivateKey) keyStore.getKey(keyStoreProperties.getAlias(), keyStoreProperties.getPassword()));
		} catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException | IOException e) {
			logger.error("Failed to load KeyStore: {}", e.getMessage(), e);
			throw new IllegalStateException("Failed to load KeyStore: " + e.getMessage(), e);
		}
	}

	@Override
	public SignedJWT getSignedToken(Authentication authentication) {
		SignedJWT jwt = new SignedJWT(header, getClaimsFromAuthentication(authentication));
		try {
			jwt.sign(signer);
			return jwt;
		} catch (JOSEException e) {
			logger.error("Failed to sign JWT: {}", e.getMessage(), e);
			throw new IllegalStateException("Failed to sign JWT: See logs");
		}
	}


	private JWTClaimsSet getClaimsFromAuthentication(Authentication authentication) {
		GlmsAuthenticationToken token = (GlmsAuthenticationToken) authentication;
		final long current = System.currentTimeMillis();
		return new JWTClaimsSet.Builder()
						.subject(token.getName())
						.claim("aty", getCommaSeparatedStringFromAuthorities(token.getAuthorities()))
						.issueTime(new Date(current))
						.expirationTime(new Date(current + (1000 * 60 * 15)))
						.issuer("GLMS")
						.build();
	}

	private String getCommaSeparatedStringFromAuthorities(Collection<? extends GrantedAuthority> authorities) {
		return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
	}
}
