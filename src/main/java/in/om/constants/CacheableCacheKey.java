package in.om.constants;

public class CacheableCacheKey {
    private CacheableCacheKey(){}

    public static final String ORGANIZATION = "#result.id";

    public static final String GROUP = "#result.organizationId.concat('-').concat(#result.id)";
    public static final String GROUPS = "#organizationId";

}
