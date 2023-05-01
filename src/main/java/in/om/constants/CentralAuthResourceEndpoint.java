package in.om.constants;

/**
 * @author Prakash Rathod
 */
public class CentralAuthResourceEndpoint {
    private CentralAuthResourceEndpoint() {}
    public static final String BASE_ROUTE_V1 = "/api/om/v1";

    public static final String AUTH = BASE_ROUTE_V1 + "/auth";

    public static final String MASTER = BASE_ROUTE_V1 + "/master";
    public static final String ORG = MASTER + "/organization";
    public static final String GROUP = MASTER + "/group/{organizationId}";
}
