package in.om.constants;

/**
 * @author Prakash Rathod
 */
public class CentralAuthResourceEndpoint {
    private CentralAuthResourceEndpoint() {}
    public static final String BASE_ROUTE = "/api/om";
    public static final String VERSION_V1 = "/v1";

    // AUTH
    public static final String AUTH = BASE_ROUTE + "/auth";
    public static final String AUTH_LOGIN = VERSION_V1 + "/login";

    // MASTER
    public static final String MASTER = BASE_ROUTE + "/master";

    // ORGANIZATION
    public static final String ORG = MASTER + "/organization";
    public static final String ORG_V1_FETCH = VERSION_V1 + "/{id}";
    public static final String ORG_V1_FETCH_ALL = VERSION_V1;
    public static final String ORG_V1_CREATE = VERSION_V1;
    public static final String ORG_V1_UPDATE = VERSION_V1 + "/{id}";
    public static final String ORG_V1_DELETE = VERSION_V1 + "/{id}";

    // GROUP
    public static final String GROUP = MASTER + "/group/{organizationId}";
    public static final String GROUP_V1_FETCH = VERSION_V1 + "/{id}";
    public static final String GROUP_V1_FETCH_ALL = VERSION_V1;
    public static final String GROUP_V1_CREATE = VERSION_V1;
    public static final String GROUP_V1_UPDATE = VERSION_V1 + "/{id}";
    public static final String GROUP_V1_DELETE = VERSION_V1 + "/{id}";

    // SUB GROUP
    public static final String SUB_GROUP = MASTER + "/subgroup/{groupId}";
    public static final String SUB_GROUP_V1_FETCH = VERSION_V1 + "/{id}";
    public static final String SUB_GROUP_V1_FETCH_ALL = VERSION_V1;
    public static final String SUB_GROUP_V1_CREATE = VERSION_V1;
    public static final String SUB_GROUP_V1_UPDATE = VERSION_V1 + "/{id}";
    public static final String SUB_GROUP_V1_DELETE = VERSION_V1 + "/{id}";

    // ROLE
    public static final String ROLE = MASTER + "/role/{groupId}";
    public static final String ROLE_V1_FETCH = VERSION_V1 + "/{id}";
    public static final String ROLE_V1_FETCH_ALL = VERSION_V1;
    public static final String ROLE_V1_CREATE = VERSION_V1;
    public static final String ROLE_V1_UPDATE = VERSION_V1 + "/{id}";
    public static final String ROLE_V1_DELETE = VERSION_V1 + "/{id}";

    // USER
    public static final String USER = BASE_ROUTE + "/v1/user";
}
