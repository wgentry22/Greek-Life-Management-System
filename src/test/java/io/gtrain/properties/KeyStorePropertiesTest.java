package io.gtrain.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class KeyStorePropertiesTest extends BasePropertiesTest {

	@Autowired
	private KeyStoreProperties keyStoreProperties;

	@Test
	public void keyStoreProperties_ShouldBeAutowired() {
		assertThat(keyStoreProperties).isNotNull();
	}

	@Test
	public void keyStoreProperties_ShouldProducePath() {
		assertThat(keyStoreProperties.getPath()).isNotBlank();
	}

	@Test
	public void keyStoreProperties_ShouldProduceAlias() {
		assertThat(keyStoreProperties.getAlias()).isNotBlank();
	}

	@Test
	public void keyStoreProperties_ShouldProducePassword() {
		assertThat(keyStoreProperties.getPassword()).isNotEmpty();
	}

	@Test
	public void keyStoreProperties_ShouldProduceType() {
		assertThat(keyStoreProperties.getType()).isNotBlank();
	}

	@Test
	public void keyStoreProperties_ShouldProduceSecret() {
		assertThat(keyStoreProperties.getSecret()).isNotBlank();
	}
}
