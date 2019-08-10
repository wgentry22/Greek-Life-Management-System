package io.gtrain.integration.base;

import io.gtrain.domain.model.*;
import io.gtrain.domain.model.dto.RegistrationForm;
import io.gtrain.domain.model.enums.Major;
import io.gtrain.domain.model.enums.Minor;
import io.gtrain.domain.model.enums.Year;
import io.gtrain.domain.repository.MemberRepositoryImpl;
import io.gtrain.security.token.TokenFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;

/**
 * @author William Gentry
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WebIntegrationTestBase {

	@Autowired
	private ApplicationContext context;

	@Autowired
	protected TokenFactory tokenFactory;

	@MockBean
	private MemberRepositoryImpl memberRepository;

	@Autowired
	protected ReactiveMongoTemplate mongoTemplate;

	protected WebTestClient webClient;

	protected String token;

	protected final String ENCODED_PASSWORD = "$2a$13$l3iT7lZ/LbeGZrCL1LGMV.Q8AyHbnim5.uKASwcj06rA0RpwZk6mS";
	protected final GlmsMember VALID_MEMBER = new GlmsMember("test", "william.gentry02@gmail.com", new PhoneNumber("555", "555", "5555"),
			ENCODED_PASSWORD, Arrays.asList(new GlmsAuthority("ROLE_VIEWER")), true, true, true, true,
			new ScholasticInfo(Major.MATH, Minor.STATISTICS, Year.FRESHMAN), new Name("Test", "User"));

	protected final RegistrationForm VALID_REGISTRATION_FORM = new RegistrationForm("Test", "User", "test", "william.gentry02@gmail.com", "Password123!", "Password123!", new PhoneNumber("555", "555", "5555"), Major.MATH, Minor.STATISTICS, Year.FRESHMAN);

	@BeforeEach
	public void initWebTestClient() {
		webClient = WebTestClient.bindToApplicationContext(context)
					.configureClient()
					.baseUrl("http://localhost:8080")
					.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.build();
	}

	@BeforeEach
	public void initMemberRepository() {
		Mockito.lenient().when(memberRepository.findMemberByUsername("test")).thenReturn(Mono.just(VALID_MEMBER));
		Mockito.lenient().when(memberRepository.findMemberByUsername(not(eq("test")))).thenReturn(Mono.defer(() -> Mono.error(() -> new BadCredentialsException("Invalid Credentials"))));

		Mockito.lenient().when(memberRepository.findMemberByEmail("test@email.com")).thenReturn(Mono.just(VALID_MEMBER));
		Mockito.lenient().when(memberRepository.findMemberByEmail(not(eq("test@email.com")))).thenReturn(Mono.defer(() -> Mono.error(() -> new BadCredentialsException("Invalid Credentials"))));
	}
}
