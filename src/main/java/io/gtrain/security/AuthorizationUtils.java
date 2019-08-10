package io.gtrain.security;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author William Gentry
 */
public class AuthorizationUtils {

    private final RoleHierarchy roleHierarchy;

    public AuthorizationUtils(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }

    public static final String CAN_CREATE_EVENTS = "canCreateEvents"; //
    public static final String CAN_UPDATE_EVENTS = "canUpdateEvents"; //
    public static final String CAN_LIST_EVENTS = "canListEvents"; //
    public static final String CAN_VIEW_EVENT = "canViewEvent"; //
    public static final String CAN_LIST_MEMBERS = "canListMembers"; //
    public static final String CAN_LIST_MEMBER_PROFILES = "canListMemberProfiles"; //
    public static final String CAN_VIEW_MEMBER_PROFILE = "canViewMemberProfile"; //
    public static final String CAN_CREATE_MEMBER_PROFILE = "canCreateMemberProfile"; //
    public static final String CAN_EDIT_OWN_PROFILE = "canEditOwnProfile"; //
    public static final String CAN_LIST_ASSOCIATE_MEMBER_PROFILES = "canListAssociateMemberProfiles"; //
    public static final String CAN_VIEW_ASSOCIATE_MEMBER_PROFILE = "canViewAssociateMemberProfile"; //
    public static final String CAN_DELETE_MEMBER_PROFILE = "canDeleteMemberProfile"; //
    public static final String CAN_DELETE_EVENT = "canDeleteEvent"; //

    public boolean isRoleReachableFromAuthority(Role role, Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null || authorities.isEmpty())
            return false;
        return roleHierarchy.getReachableGrantedAuthorities(authorities).stream()
                        .map(GrantedAuthority::getAuthority)
                        .anyMatch(authority -> role.toString().equals(authority));
    }
}
