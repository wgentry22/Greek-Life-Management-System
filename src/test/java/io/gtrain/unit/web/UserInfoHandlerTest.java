package io.gtrain.unit.web;

import io.gtrain.domain.model.GlmsAuthenticationToken;
import io.gtrain.domain.model.dto.ContactableMemberDTO;
import io.gtrain.domain.model.enums.Major;
import io.gtrain.domain.model.enums.Minor;
import io.gtrain.domain.model.enums.Year;
import io.gtrain.unit.base.WebUnitTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class UserInfoHandlerTest extends WebUnitTestBase {

	private String token;

	@BeforeEach
	void initToken() {
		token = tokenFactory.generateToken(new GlmsAuthenticationToken(generateValidMember()));
	}

	@Test
	public void userInfoHandler_ShouldProduce_ContactableMemberDTO_WithJWT() {
		userInfoWebClient.get()
					.uri(URI.create("/api/info"))
					.cookie("api_token", token)
					.exchange()
					.expectStatus()
						.isOk()
					.expectBody(ContactableMemberDTO.class)
						.consumeWith(result -> {
							ContactableMemberDTO dto = result.getResponseBody();

							assertThat(dto.getEmail()).isNotBlank();

							assertThat(dto.getPhoneNumber().getAreaCode()).isNotBlank();
							assertThat(dto.getPhoneNumber().getAreaCode().length()).isEqualTo(3);

							assertThat(dto.getPhoneNumber().getPrefix()).isNotBlank();
							assertThat(dto.getPhoneNumber().getPrefix().length()).isEqualTo(3);

							assertThat(dto.getPhoneNumber().getLineNumber()).isNotBlank();
							assertThat(dto.getPhoneNumber().getLineNumber().length()).isEqualTo(4);

							assertThat(dto.getScholasticInfo().getMajor()).isEqualTo(Major.MATH);
							assertThat(dto.getScholasticInfo().getMinor()).isEqualTo(Minor.STATISTICS);
							assertThat(dto.getScholasticInfo().getYear()).isEqualTo(Year.FRESHMAN);

							assertThat(dto.getName().getFirstname()).isNotBlank();
							assertThat(dto.getName().getLastname()).isNotBlank();
						});
	}

	@Test
	public void userInfoHandler_ShouldProduceUnauthorized_ForMissingJWT() {
		userInfoWebClient.get()
					.uri(URI.create("/api/info"))
					.exchange()
					.expectStatus()
						.isUnauthorized();
	}
}
