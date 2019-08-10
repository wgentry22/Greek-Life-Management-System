package io.gtrain.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class SecurityPropertiesTest extends BasePropertiesTest {

	@Autowired
	private SecurityProperties securityProperties;

	@Test
	public void securityProperties_ShouldBeAutowired() {
		assertThat(securityProperties).isNotNull();
	}

	@Test
	public void securityProperties_ShouldProduceRoles() {
		assertThat(securityProperties.getRoles()).isNotBlank();
	}
}
