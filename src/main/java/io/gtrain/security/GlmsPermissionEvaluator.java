package io.gtrain.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.io.Serializable;

/**
 * @author William Gentry
 */
public class GlmsPermissionEvaluator implements PermissionEvaluator {

    private final AuthorizationUtils utils;
    private final Logger logger = Loggers.getLogger(getClass());

    public GlmsPermissionEvaluator(AuthorizationUtils utils) {
        this.utils = utils;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication != null && permission instanceof String) {
            logger.info("{} attempting to access permission {}", authentication.getName(), permission);
            switch ((String) permission) {
                case AuthorizationUtils.CAN_CREATE_EVENTS:
                    return utils.isRoleReachableFromAuthority(Role.DEPT_HEAD, authentication.getAuthorities());
                case AuthorizationUtils.CAN_LIST_EVENTS:
                    return utils.isRoleReachableFromAuthority(Role.VIEWER, authentication.getAuthorities());
                case AuthorizationUtils.CAN_VIEW_EVENT:
                    return utils.isRoleReachableFromAuthority(Role.VIEWER, authentication.getAuthorities());
                case AuthorizationUtils.CAN_LIST_MEMBERS:
                    return utils.isRoleReachableFromAuthority(Role.ASSOCIATE_MEMBER, authentication.getAuthorities());
                case AuthorizationUtils.CAN_UPDATE_EVENTS:
                    return utils.isRoleReachableFromAuthority(Role.DEPT_HEAD, authentication.getAuthorities());
                case AuthorizationUtils.CAN_LIST_MEMBER_PROFILES:
                    return utils.isRoleReachableFromAuthority(Role.MEMBER, authentication.getAuthorities());
                case AuthorizationUtils.CAN_VIEW_MEMBER_PROFILE:
                    return utils.isRoleReachableFromAuthority(Role.MEMBER, authentication.getAuthorities());
                case AuthorizationUtils.CAN_CREATE_MEMBER_PROFILE:
                    return utils.isRoleReachableFromAuthority(Role.EXECUTIVE, authentication.getAuthorities());
                case AuthorizationUtils.CAN_EDIT_OWN_PROFILE:
                    return utils.isRoleReachableFromAuthority(Role.ASSOCIATE_MEMBER, authentication.getAuthorities());
                case AuthorizationUtils.CAN_LIST_ASSOCIATE_MEMBER_PROFILES:
                    return utils.isRoleReachableFromAuthority(Role.ASSOCIATE_MEMBER, authentication.getAuthorities());
                case AuthorizationUtils.CAN_VIEW_ASSOCIATE_MEMBER_PROFILE:
                    return utils.isRoleReachableFromAuthority(Role.ASSOCIATE_MEMBER, authentication.getAuthorities());
                case AuthorizationUtils.CAN_DELETE_EVENT:
                    return utils.isRoleReachableFromAuthority(Role.ADMIN, authentication.getAuthorities());
                case AuthorizationUtils.CAN_DELETE_MEMBER_PROFILE:
                    return utils.isRoleReachableFromAuthority(Role.ADMIN, authentication.getAuthorities());
                default:
                    return false;
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new IllegalArgumentException("ID and Class permissions not available");
    }
}
