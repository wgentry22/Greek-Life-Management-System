package io.gtrain.integration.base;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author William Gentry
 */
@ActiveProfiles("authorization")
public class SecuredMethodsBase extends WebIntegrationTestBase {

	@Autowired
	private ApplicationContext context;

	protected WebTestClient securedMethodsWebClient;

	@BeforeEach
	public void initWebTestClient() {
		securedMethodsWebClient = WebTestClient.bindToApplicationContext(context)
				.configureClient()
				.baseUrl("http://localhost:8080")
				.build();
	}
}
