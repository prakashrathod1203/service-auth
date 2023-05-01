package in.om.constants;

public class CacheableCacheKey {
    private CacheableCacheKey(){}

    public static final String ORGANIZATION_RES = "#result.id";
    public static final String ORGANIZATION = "#id";

    public static final String GROUP_RES = "#result.organizationId.concat('-').concat(#result.id)";
    public static final String GROUP = "#organizationId.concat('-').concat(#id)";
    public static final String GROUPS = "#organizationId";

    public static final String SUB_GROUP_RES = "#result.groupId.concat('-').concat(#result.id)";
    public static final String SUB_GROUP = "#groupId.concat('-').concat(#id)";
    public static final String SUB_GROUPS = "#groupId";

    public static final String ROLE_RES = "#result.groupId.concat('-').concat(#result.id)";
    public static final String ROLE = "#groupId.concat('-').concat(#id)";
    public static final String ROLES = "#groupId";

}
