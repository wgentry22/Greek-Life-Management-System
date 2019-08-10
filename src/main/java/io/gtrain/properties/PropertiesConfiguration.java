package io.gtrain.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author William Gentry
 */
@Configuration
@EnableConfigurationProperties({KeyStoreProperties.class, MongoProperties.class, SecurityProperties.class})
public class PropertiesConfiguration {
}
