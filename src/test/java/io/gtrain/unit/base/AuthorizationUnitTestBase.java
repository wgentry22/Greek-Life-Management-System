package io.gtrain.unit.base;

import io.gtrain.domain.model.GlmsAuthenticationToken;
import io.gtrain.domain.model.GlmsMember;
import io.gtrain.security.AuthorizationUtils;
import io.gtrain.security.GlmsPermissionEvaluator;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class AuthorizationUnitTestBase extends TokenUnitTestBase {

	protected final GlmsPermissionEvaluator permissionEvaluator = new GlmsPermissionEvaluator(new AuthorizationUtils(getRoleHierarchy()));


	@Test
	public void permissionEvaluator_ShouldReturnFalse_ForNullAuthorities() {
		GlmsMember member = generateValidMember();
		member.setAuthorities(null);
		final Authentication authentication = new GlmsAuthenticationToken(member);
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_EVENT)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_EVENTS)).isFalse();
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
	public void permissionEvaluator_ShouldReturnFalse_ForEmptyAuthorities() {
		GlmsMember member = generateValidMember();
		member.setAuthorities(Collections.emptyList());
		final Authentication authentication = new GlmsAuthenticationToken(member);
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_VIEW_EVENT)).isFalse();
		assertThat(permissionEvaluator.hasPermission(authentication, new Object(), AuthorizationUtils.CAN_LIST_EVENTS)).isFalse();
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

	private final RoleHierarchy getRoleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("security.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load [security.properties]", e);
		}
		roleHierarchy.setHierarchy(properties.getProperty("security.roles"));
		return roleHierarchy;
	}
}
