package io.gtrain.integration.registration;

import com.mongodb.client.result.DeleteResult;
import io.gtrain.domain.model.Verification;
import io.gtrain.domain.model.dto.RegistrationForm;
import io.gtrain.domain.model.dto.message.SuccessfulRegistrationMessage;
import io.gtrain.integration.base.WebIntegrationTestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class WebRegistrationTest extends WebIntegrationTestBase {

	@Test
	public void validRegistrationForm_ShouldCreateMember_AndSendVerificationEmail() {
		webClient.post()
				.uri(URI.create("/register"))
				.body(Mono.just(VALID_REGISTRATION_FORM), RegistrationForm.class)
				.exchange()
				.expectStatus()
					.isOk()
				.expectBody(SuccessfulRegistrationMessage.class)
					.isEqualTo(new SuccessfulRegistrationMessage(VALID_MEMBER.getEmail()));
	}

	@AfterEach
	void cleanUpDB() {
		DeleteResult result = mongoTemplate.remove(Verification.class).all().block();
		assertThat(result.getDeletedCount()).isGreaterThan(0L);
	}
}
