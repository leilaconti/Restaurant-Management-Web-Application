package team14.arms.backend.data.model;

/**
 * Represents the role of a {@link User} and thus the permissions granted to said user.
 */
public class Role {

    public static final String ADMIN = "admin";
    public static final String CHEF = "chef";
    public static final String MANAGER = "manager";
    public static final String WAITER = "waiter";

    private Role() {}

    public static String[] getAllRoles() {
        return new String[] { WAITER, CHEF, MANAGER, ADMIN };
    }

}
