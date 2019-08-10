package io.gtrain.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gtrain.security.GlmsAuthenticationManager;
import io.gtrain.security.GlmsPermissionEvaluator;
import io.gtrain.security.GlmsTokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.json.Jackson2CodecSupport;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author William Gentry
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebFluxSecurityConfig {

	private final GlmsTokenAuthenticationFilter tokenAuthenticationFilter;
	private final GlmsPermissionEvaluator permissionEvaluator;
	private final GlmsAuthenticationManager authenticationManager;

	public WebFluxSecurityConfig(GlmsTokenAuthenticationFilter tokenAuthenticationFilter, GlmsPermissionEvaluator permissionEvaluator, GlmsAuthenticationManager authenticationManager) {
		this.tokenAuthenticationFilter = tokenAuthenticationFilter;
		this.permissionEvaluator = permissionEvaluator;
		this.authenticationManager = authenticationManager;
	}

	@Bean
	@Primary
	public DefaultMethodSecurityExpressionHandler customMethodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
		DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
		handler.setRoleHierarchy(roleHierarchy);
		handler.setPermissionEvaluator(permissionEvaluator);
		return handler;
	}

	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder.baseUrl("http://localhost:8080/").build();
	}


	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity httpSecurity) {
		return httpSecurity
				.authenticationManager(authenticationManager)
				.authorizeExchange()
					.pathMatchers(HttpMethod.POST, "/login").permitAll()
					.pathMatchers(HttpMethod.POST, "/register").permitAll()
					.pathMatchers(HttpMethod.GET, "/verify/email").permitAll()
					.anyExchange()
						.authenticated()
					.and()
				.httpBasic().disable()
				.formLogin().disable()
				.csrf().disable()
				.addFilterAt(tokenAuthenticationFilter, SecurityWebFiltersOrder.FIRST)
				.build();
	}
}
