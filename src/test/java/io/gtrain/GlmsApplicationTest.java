package io.gtrain;

import io.gtrain.handler.AuthenticationHandler;
import io.gtrain.security.GlmsAuthenticationManager;
import io.gtrain.security.GlmsUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GlmsApplicationTest {

	private ApplicationContextRunner runner;

	@Autowired
	private ApplicationContext applicationContext;

	@BeforeEach
	void initApplicationContextRunner() {
		runner = new ApplicationContextRunner().withParent(applicationContext);
	}

	@Test
	public void ac_ShouldHaveOne_AuthenticationHandler() {
		runner.run(context -> {
			assertThat(context).hasSingleBean(AuthenticationHandler.class);
		});
	}

	@Test
	public void ac_ShouldHaveOne_GlmsAuthenticationManager() {
		runner.run(context -> {
			assertThat(context.getBean(ReactiveAuthenticationManager.class)).isInstanceOf(GlmsAuthenticationManager.class);
		});
	}

	@Test
	public void ac_ShouldHaveOne_GlmsUserDetailsService() {
		runner.run(context -> {
			assertThat(context.getBean(ReactiveUserDetailsService.class)).isInstanceOf(GlmsUserDetailsService.class);
		});
	}
}

