package om.auth.config;

public class UserContext {

    private static final ThreadLocal<Long> createdByIdThread = new ThreadLocal<>();
    private static final ThreadLocal<Long> modifiedByIdThread = new ThreadLocal<>();
    private static final ThreadLocal<String> createdByThread = new ThreadLocal<>();
    private static final ThreadLocal<String> modifiedByThread = new ThreadLocal<>();

    public static void setUserContext(Long createdById, Long modifiedById, String createdBy,
            String modifiedBy) {
        createdByIdThread.set(createdById);
        modifiedByIdThread.set(modifiedById);
        createdByThread.set(createdBy);
        modifiedByThread.set(modifiedBy);
    }

    public static void clear() {
        createdByIdThread.remove();
        modifiedByIdThread.remove();
        createdByThread.remove();
        modifiedByThread.remove();
    }

    public static Long getCreatedById() {
        return createdByIdThread.get();
    }

    public static Long getModifiedById() {
        return modifiedByIdThread.get();
    }

    public static String getCreatedBy() {
        return createdByThread.get();
    }

    public static String getModifiedBy() {
        return modifiedByThread.get();
    }

}
