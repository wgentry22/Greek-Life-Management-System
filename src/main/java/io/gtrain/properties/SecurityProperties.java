package io.gtrain.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author William Gentry
 */
@Component
@PropertySource("classpath:security.properties")
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

	private String roles;

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
}
