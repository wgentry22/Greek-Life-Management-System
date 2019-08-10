package io.gtrain.integration.authorization;

import io.gtrain.domain.model.GlmsAuthenticationToken;
import io.gtrain.domain.model.GlmsAuthority;
import io.gtrain.domain.model.GlmsMember;
import io.gtrain.integration.base.SecuredMethodsBase;
import io.gtrain.util.verifier.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class RoleViewerAuthorizationTest extends SecuredMethodsBase {

	private ListEventsRequestVerifier listEventsRequestVerifier;
	private CreateEventRequestVerifier createEventRequestVerifier;
	private ViewSingleEventRequestVerifier viewSingleEventRequestVerifier;
	private UpdateEventRequestVerifier updateEventRequestVerifier;
	private DeleteEventRequestVerifier deleteEventRequestVerifier;

	private CreateProfileRequestVerifier createProfileRequestVerifier;
	private DeleteProfileRequestVerifier deleteProfileRequestVerifier;
	private EditProfileRequestVerifier editProfileRequestVerifier;

	private ViewAssociateMemberProfileRequestVerifier viewAssociateMemberProfileRequestVerifier;
	private ListAssociateMemberProfileRequestVerifier listAssociateMemberProfileRequestVerifier;

	private ListMemberProfileRequestVerifier listMemberProfileRequestVerifier;
	private ListMemberRequestVerifier listMemberRequestVerifier;
	private ViewMemberProfileRequestVerifier viewMemberProfileRequestVerifier;

	@BeforeEach
	void initRequestVerifiers() {
		GlmsMember member = VALID_MEMBER;
		member.setAuthorities(Arrays.asList(new GlmsAuthority("ROLE_VIEWER")));
		token = tokenFactory.generateToken(new GlmsAuthenticationToken(member));

		listEventsRequestVerifier = new ListEventsRequestVerifier(securedMethodsWebClient, token);
		createEventRequestVerifier = new CreateEventRequestVerifier(securedMethodsWebClient, token);
		viewSingleEventRequestVerifier = new ViewSingleEventRequestVerifier(securedMethodsWebClient, token);
		updateEventRequestVerifier = new UpdateEventRequestVerifier(securedMethodsWebClient, token);
		deleteEventRequestVerifier = new DeleteEventRequestVerifier(securedMethodsWebClient, token);

		createProfileRequestVerifier = new CreateProfileRequestVerifier(securedMethodsWebClient, token);
		deleteProfileRequestVerifier = new DeleteProfileRequestVerifier(securedMethodsWebClient, token);
		editProfileRequestVerifier = new EditProfileRequestVerifier(securedMethodsWebClient, token);

		viewAssociateMemberProfileRequestVerifier = new ViewAssociateMemberProfileRequestVerifier(securedMethodsWebClient, token);
		listAssociateMemberProfileRequestVerifier = new ListAssociateMemberProfileRequestVerifier(securedMethodsWebClient, token);
		viewMemberProfileRequestVerifier = new ViewMemberProfileRequestVerifier(securedMethodsWebClient, token);

		listMemberProfileRequestVerifier = new ListMemberProfileRequestVerifier(securedMethodsWebClient, token);
		listMemberRequestVerifier = new ListMemberRequestVerifier(securedMethodsWebClient, token);
	}

	@Test
	public void viewerToken_ShouldBeOk_AtListEventsEndpoint() {
		assertThat(listEventsRequestVerifier.isOk()).isTrue();
		assertThat(listEventsRequestVerifier.isForbidden()).isFalse();
	}

	@Test
	public void viewerToken_ShouldBeOk_AtViewEventEndpoint() {
		assertThat(viewSingleEventRequestVerifier.isOk()).isTrue();
		assertThat(viewSingleEventRequestVerifier.isForbidden()).isFalse();
	}

	@Test
	public void viewerToken_ShouldBeForbidden_AtCreateEventsEndpoint() {
		assertThat(createEventRequestVerifier.isOk()).isFalse();
		assertThat(createEventRequestVerifier.isForbidden()).isTrue();
	}

	@Test
	public void viewerToken_ShouldBeForbidden_AtDeleteEventsEndpoint() {
		assertThat(deleteEventRequestVerifier.isOk()).isFalse();
		assertThat(deleteEventRequestVerifier.isForbidden()).isTrue();
	}

	@Test
	public void viewerToken_ShouldBeForbidden_AtCreateMemberProfileEndpoint() {
		assertThat(createProfileRequestVerifier.isOk()).isFalse();
		assertThat(createProfileRequestVerifier.isForbidden()).isTrue();
	}

	@Test
	public void viewerToken_ShouldBeForbidden_AtDeleteMemberProfileEndpoint() {
		assertThat(deleteProfileRequestVerifier.isOk()).isFalse();
		assertThat(deleteProfileRequestVerifier.isForbidden()).isTrue();
	}

	@Test
	public void viewerToken_ShouldBeForbidden_AtEditProfileEndpoint() {
		assertThat(editProfileRequestVerifier.isOk()).isFalse();
		assertThat(editProfileRequestVerifier.isForbidden()).isTrue();
	}

	@Test
	public void viewerToken_ShouldBeForbidden_AtListAssociateMemberProfileEndpoint() {
		assertThat(listAssociateMemberProfileRequestVerifier.isOk()).isFalse();
		assertThat(listAssociateMemberProfileRequestVerifier.isForbidden()).isTrue();
	}

	@Test
	public void viewerToken_ShouldBeForbidden_AtListMemberProfileEndpoint() {
		assertThat(listMemberProfileRequestVerifier.isOk()).isFalse();
		assertThat(listMemberProfileRequestVerifier.isForbidden()).isTrue();
	}

	@Test
	public void viewerToken_ShouldBeForbidden_AtListMembersEndpoint() {
		assertThat(listMemberRequestVerifier.isOk()).isFalse();
		assertThat(listMemberRequestVerifier.isForbidden()).isTrue();
	}

	@Test
	public void viewerToken_ShouldBeForbidden_AtUpdateEventsEndpoint() {
		assertThat(updateEventRequestVerifier.isOk()).isFalse();
		assertThat(updateEventRequestVerifier.isForbidden()).isTrue();
	}

	@Test
	public void viewerToken_ShouldBeForbidden_AtViewAssociateMemberProfileEndpoint() {
		assertThat(viewAssociateMemberProfileRequestVerifier.isOk()).isFalse();
		assertThat(viewAssociateMemberProfileRequestVerifier.isForbidden()).isTrue();
	}

	@Test
	public void viewerToken_ShouldBeForbidden_AtViewMemberProfileEndpoint() {
		assertThat(viewMemberProfileRequestVerifier.isOk()).isFalse();
		assertThat(viewMemberProfileRequestVerifier.isForbidden()).isTrue();
	}
}
