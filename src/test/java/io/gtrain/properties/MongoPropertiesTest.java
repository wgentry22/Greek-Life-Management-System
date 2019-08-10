package io.gtrain.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class MongoPropertiesTest extends BasePropertiesTest {

	@Autowired
	private MongoProperties mongoProperties;

	@Test
	public void mongoProperties_ShouldBeAutowired() {
		assertThat(mongoProperties).isNotNull();
	}

	@Test
	public void mongoProperties_ShouldProduceUrl() {
		assertThat(mongoProperties.getUrl()).isNotBlank();
	}

	@Test
	public void mongoProperties_ShouldProduceDatabaseName() {
		assertThat(mongoProperties.getDatabase().getName()).isNotBlank();
	}

}
