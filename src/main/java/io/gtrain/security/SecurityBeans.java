package io.gtrain.security;

import io.gtrain.properties.KeyStoreProperties;
import io.gtrain.properties.SecurityProperties;
import io.gtrain.security.token.TokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author William Gentry
 */
@Configuration
public class SecurityBeans {

    private SecurityProperties securityProperties;
    private KeyStoreProperties keyStoreProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(13);
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roles = new RoleHierarchyImpl();
        roles.setHierarchy(securityProperties.getRoles());
        return roles;
    }

    @Bean
    public AuthorizationUtils authorityUtils(RoleHierarchy roleHierarchy) {
        return new AuthorizationUtils(roleHierarchy);
    }

    @Bean
    public GlmsPermissionEvaluator glmsPermissionEvaluator(AuthorizationUtils authorizationUtils) {
        return new GlmsPermissionEvaluator(authorizationUtils);
    }

    @Bean
    public TokenFactory tokenFactory() {
        return new TokenFactory(keyStoreProperties);
    }

    @Autowired
    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Autowired
    public void setKeyStoreProperties(KeyStoreProperties keyStoreProperties) {
        this.keyStoreProperties = keyStoreProperties;
    }
}
