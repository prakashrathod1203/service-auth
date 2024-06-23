package tech.sarthee.auth.library.constant;

public class ResourceEndpoint {

    private ResourceEndpoint() {
    }

    public static final String BASE_ROUTE_V1 = "/api/qrcode/v1";

    public static final String URL = BASE_ROUTE_V1 + "/url";

    public static final String USER = BASE_ROUTE_V1 + "/user";

    public static final String QR = BASE_ROUTE_V1 + "/qr";
    public static final String QR_LIST = "/list";
    public static final String QR_COPY = "/copy";

    public static final String USER_QR = USER + "/qr";
    public static final String USER_QR_LIST = "/list";
    public static final String VIEW_USER_DETAIL = "/view";
    public static final String FETCH_QR_DETAIL = "/detail";

    public static final String OTP = BASE_ROUTE_V1 + "/otp";

    public static final String FILE = BASE_ROUTE_V1 + "/file";
    public static final String FILE_UPLOAD = "/upload";
    public static final String FILE_DOWNLOAD = "/download/{id}";

}

