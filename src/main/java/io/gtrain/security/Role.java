package io.gtrain.security;

/**
 * @author William Gentry
 */
public enum Role {

    ADMIN, EXECUTIVE, DEPT_HEAD, MEMBER, ASSOCIATE_MEMBER, VIEWER;

    @Override
    public String toString() {
        return "ROLE_" + super.toString();
    }
}
