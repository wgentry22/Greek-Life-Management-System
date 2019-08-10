package io.gtrain.security.annotations;

import io.gtrain.security.AuthorizationUtils;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * @author William Gentry
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("isAuthenticated() && hasPermission(authentication, '" + AuthorizationUtils.CAN_DELETE_MEMBER_PROFILE + "')")
public @interface CanDeleteMemberProfile {
}
