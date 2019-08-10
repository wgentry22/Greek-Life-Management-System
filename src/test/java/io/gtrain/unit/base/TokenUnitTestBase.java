package io.gtrain.unit.base;

import io.gtrain.properties.KeyStoreProperties;
import io.gtrain.security.token.TokenFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @author William Gentry
 */
public class TokenUnitTestBase extends ApplicationUnitTestBase {

	protected final TokenFactory tokenFactory = new TokenFactory(getTokenProperties());

	private final KeyStoreProperties getTokenProperties() {
		Properties props = new Properties();
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("keystore.properties"));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load [keystore.properties]", e);
		}
		KeyStoreProperties keyStoreProperties = new KeyStoreProperties();
		keyStoreProperties.setSecret(props.getProperty("keystore.secret"));
		keyStoreProperties.setType(props.getProperty("keystore.type"));
		keyStoreProperties.setPath(props.getProperty("keystore.path"));
		keyStoreProperties.setAlias(props.getProperty("keystore.alias"));
		keyStoreProperties.setPassword(props.getProperty("keystore.password").toCharArray());
		return keyStoreProperties;
	}
}
