package io.gtrain.unit.base;

import io.gtrain.handler.AuthenticationHandler;
import io.gtrain.handler.RegistrationHandler;
import io.gtrain.handler.UserInfoHandler;
import io.gtrain.service.email.OutgoingVerificationEmailService;
import io.gtrain.util.helper.SecuredMethodHandler;
import io.gtrain.util.helper.SecuredMethodRouter;
import io.gtrain.service.registration.DefaultMemberRegistrationService;
import io.gtrain.service.registration.GlmsRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * @author William Gentry
 */
public class WebUnitTestBase extends TokenUnitTestBase {

	protected WebTestClient authenticationWebClient;

	protected WebTestClient userInfoWebClient;

	protected WebTestClient registrationWebClient;

	protected WebTestClient securedMethodWebClient;

	@BeforeEach
	void initAuthenticationHandlerWebClient() {
		AuthenticationHandler handler = new AuthenticationHandler(mapper, authenticationManager, tokenFactory);
		RouterFunction<ServerResponse> authenticationHandlerRouter = RouterFunctions.route().POST("/login", accept(MediaType.APPLICATION_JSON_UTF8), handler::attemptAuthentication).build();
		authenticationWebClient = WebTestClient.bindToRouterFunction(authenticationHandlerRouter).handlerStrategies(HandlerStrategies.withDefaults()).build();
	}

	@BeforeEach
	void initUserInfoWebClient() {
		UserInfoHandler handler = new UserInfoHandler(tokenFactory, userDetailsService);
		RouterFunction<ServerResponse> userInfoHandlerRouter = RouterFunctions.route().GET("/api/info", handler::getUserInfo).build();
		userInfoWebClient = WebTestClient.bindToRouterFunction(userInfoHandlerRouter).handlerStrategies(HandlerStrategies.withDefaults()).build();
	}

	@BeforeEach
	void initSecuredMethodWebClient() {
		securedMethodWebClient = WebTestClient.bindToRouterFunction(new SecuredMethodRouter(new SecuredMethodHandler()).securedMethodRouterBean()).handlerStrategies(HandlerStrategies.withDefaults()).build();
	}
}
