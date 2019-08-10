package io.gtrain.integration.authorization;

import io.gtrain.domain.model.GlmsAuthenticationToken;
import io.gtrain.domain.model.GlmsAuthority;
import io.gtrain.domain.model.GlmsMember;
import io.gtrain.integration.base.SecuredMethodsBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Arrays;

/**
 * @author William Gentry
 */
public class RoleAssociateMemberAuthorizationTestTest extends SecuredMethodsBase {

	@BeforeEach
	void initViewerToken() {
		GlmsMember member = VALID_MEMBER;
		member.setAuthorities(Arrays.asList(new GlmsAuthority("ROLE_ASSOCIATE_MEMBER")));
		token = tokenFactory.generateToken(new GlmsAuthenticationToken(member));
	}

	@Test
	public void viewerToken_ShouldBeOk_AtListEventsEndpoint() {
		securedMethodsWebClient.get()
				.uri(URI.create("/api/events"))
				.cookie("api_token", token)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	public void viewerToken_ShouldBeOk_AtViewEventEndpoint() {
		securedMethodsWebClient.get()
				.uri(URI.create("/api/events/blah"))
				.cookie("api_token", token)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	public void viewerToken_ShouldBeUnauthorized_AtCreateEventsEndpoint() {
		securedMethodsWebClient.post()
				.uri(URI.create("/api/events"))
				.cookie("api_token", token)
				.exchange()
				.expectStatus()
				.isForbidden();
	}

	@Test
	public void viewerToken_ShouldBeUnauthorized_AtCreateMemberProfileEndpoint() {
		securedMethodsWebClient.post()
				.uri(URI.create("/api/profiles"))
				.cookie("api_token", token)
				.exchange()
				.expectStatus()
				.isForbidden();
	}

	@Test
	public void viewerToken_ShouldBeUnauthorized_AtDeleteEventsEndpoint() {
		securedMethodsWebClient.delete()
				.uri(URI.create("/api/events"))
				.cookie("api_token", token)
				.exchange()
				.expectStatus()
				.isForbidden();
	}

	@Test
	public void viewerToken_ShouldBeUnauthorized_AtDeleteMemberProfileEndpoint() {
		securedMethodsWebClient.delete()
				.uri(URI.create("/api/profiles"))
				.cookie("api_token", token)
				.exchange()
				.expectStatus()
				.isForbidden();
	}

	@Test
	public void viewerToken_ShouldBeOk_AtEditProfileEndpoint() {
		securedMethodsWebClient.put()
				.uri(URI.create("/api/profiles"))
				.cookie("api_token", token)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	public void viewerToken_ShouldBeOk_AtListAssociateMemberProfileEndpoint() {
		securedMethodsWebClient.get()
				.uri(URI.create("/api/profiles/am"))
				.cookie("api_token", token)
				.exchange()
				.expectStatus()
					.isOk();
	}

	@Test
	public void viewerToken_ShouldBeUnauthorized_AtListMemberProfileEndpoint() {
		securedMethodsWebClient.get()
				.uri(URI.create("/api/profiles/mem"))
				.cookie("api_token", token)
				.exchange()
				.expectStatus()
					.isForbidden();
	}

	@Test
	public void viewerToken_ShouldBeOk_AtListMembersEndpoint() {
		securedMethodsWebClient.get()
				.uri(URI.create("/api/members"))
				.cookie("api_token", token)
				.exchange()
				.expectStatus()
					.isOk();
	}

	@Test
	public void viewerToken_ShouldBeUnauthorized_AtUpdateEventsEndpoint() {
		securedMethodsWebClient.put()
				.uri(URI.create("/api/events"))
				.cookie("api_token", token)
				.exchange()
				.expectStatus()
				.isForbidden();
	}

	@Test
	public void viewerToken_ShouldBeOk_AtViewAssociateMemberProfileEndpoint() {
		securedMethodsWebClient.get()
				.uri(URI.create("/api/profiles/am/blah"))
				.cookie("api_token", token)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	public void viewerToken_ShouldBeUnauthorized_AtViewMemberProfileEndpoint() {
		securedMethodsWebClient.post()
				.uri(URI.create("/api/events"))
				.cookie("api_token", token)
				.exchange()
				.expectStatus()
				.isForbidden();
	}
}
