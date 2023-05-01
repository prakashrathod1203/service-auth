package in.om.component;

public class TenantContext {

	  private static ThreadLocal<String> currentOrganizationId = new ThreadLocal<>();

	  public static void setCurrentOrganizationId(String organizationId) {
		  currentOrganizationId.set(organizationId);
	  }

	  public static String getCurrentOrganizationId() {
	    return currentOrganizationId.get();
	  }

	  public static void clear() {
		  currentOrganizationId.remove();
	  }
}
