package io.gtrain.unit.security.authorization;

import io.gtrain.domain.model.GlmsAuthenticationToken;
import io.gtrain.domain.model.GlmsAuthority;
import io.gtrain.domain.model.GlmsMember;
import io.gtrain.security.AuthorizationUtils;
import io.gtrain.unit.base.AuthorizationUnitTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class GlmsPermissionEvaluatorTest extends AuthorizationUnitTestBase {

	@Test
	public void viewerToken_ShouldOnlyHavePermission_ToViewEvents() {
		GlmsMember member = generateValidMember();
		member.setAuthorities(Arrays.asList(new GlmsAuthority("ROLE_VIEWER")));
		final Authentication authentication = new GlmsAuthenticationToken(member);
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_EVENT)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_EVENTS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_CREATE_EVENTS)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_UPDATE_EVENTS)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_MEMBERS)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_MEMBER_PROFILES)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_MEMBER_PROFILE)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_CREATE_MEMBER_PROFILE)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_EDIT_OWN_PROFILE)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_ASSOCIATE_MEMBER_PROFILES)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_ASSOCIATE_MEMBER_PROFILE)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_DELETE_EVENT)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_DELETE_MEMBER_PROFILE)).isFalse();
	}

	@Test
	public void associateMember_ShouldHavePermissionToListAndEditOwnProfile() {
		GlmsMember member = generateValidMember();
		member.setAuthorities(Arrays.asList(new GlmsAuthority("ROLE_ASSOCIATE_MEMBER")));
		final Authentication authentication = new GlmsAuthenticationToken(member);
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_EVENT)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_EVENTS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_CREATE_EVENTS)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_UPDATE_EVENTS)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_MEMBERS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_MEMBER_PROFILES)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_MEMBER_PROFILE)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_CREATE_MEMBER_PROFILE)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_EDIT_OWN_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_ASSOCIATE_MEMBER_PROFILES)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_ASSOCIATE_MEMBER_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_DELETE_EVENT)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_DELETE_MEMBER_PROFILE)).isFalse();
	}

	@Test
	public void member_ShouldHavePermissionToListEditOwnProfileAndOtherMemberProfiles() {
		GlmsMember member = generateValidMember();
		member.setAuthorities(Arrays.asList(new GlmsAuthority("ROLE_MEMBER")));
		final Authentication authentication = new GlmsAuthenticationToken(member);
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_EVENT)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_EVENTS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_CREATE_EVENTS)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_UPDATE_EVENTS)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_MEMBERS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_MEMBER_PROFILES)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_MEMBER_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_CREATE_MEMBER_PROFILE)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_EDIT_OWN_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_ASSOCIATE_MEMBER_PROFILES)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_ASSOCIATE_MEMBER_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_DELETE_EVENT)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_DELETE_MEMBER_PROFILE)).isFalse();
	}

	@Test
	public void departmentHead_ShouldHavePermissionToListEditOwnProfileAndOtherMemberProfiles_AndCreateEvents() {
		GlmsMember member = generateValidMember();
		member.setAuthorities(Arrays.asList(new GlmsAuthority("ROLE_DEPT_HEAD")));
		final Authentication authentication = new GlmsAuthenticationToken(member);
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_EVENT)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_EVENTS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_CREATE_EVENTS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_UPDATE_EVENTS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_MEMBERS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_MEMBER_PROFILES)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_MEMBER_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_CREATE_MEMBER_PROFILE)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_EDIT_OWN_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_ASSOCIATE_MEMBER_PROFILES)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_ASSOCIATE_MEMBER_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_DELETE_EVENT)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_DELETE_MEMBER_PROFILE)).isFalse();
	}

	@Test
	public void executive_ShouldHavePermissionToListEditOwnProfileAndOtherMemberProfiles_AndCreateEventsAndProfiles() {
		GlmsMember member = generateValidMember();
		member.setAuthorities(Arrays.asList(new GlmsAuthority("ROLE_EXECUTIVE")));
		final Authentication authentication = new GlmsAuthenticationToken(member);
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_EVENT)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_EVENTS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_CREATE_EVENTS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_UPDATE_EVENTS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_MEMBERS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_MEMBER_PROFILES)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_MEMBER_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_CREATE_MEMBER_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_EDIT_OWN_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_ASSOCIATE_MEMBER_PROFILES)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_ASSOCIATE_MEMBER_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_DELETE_EVENT)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_DELETE_MEMBER_PROFILE)).isFalse();
	}

	@Test
	public void admin_ShouldHaveFullPrivilege() {
		GlmsMember member = generateValidMember();
		member.setAuthorities(Arrays.asList(new GlmsAuthority("ROLE_ADMIN")));
		final Authentication authentication = new GlmsAuthenticationToken(member);
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_EVENT)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_EVENTS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_CREATE_EVENTS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_UPDATE_EVENTS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_MEMBERS)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_MEMBER_PROFILES)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_MEMBER_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_CREATE_MEMBER_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_EDIT_OWN_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_ASSOCIATE_MEMBER_PROFILES)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_ASSOCIATE_MEMBER_PROFILE)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_DELETE_EVENT)).isTrue();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_DELETE_MEMBER_PROFILE)).isTrue();
	}
}
