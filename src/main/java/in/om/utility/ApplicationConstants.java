package in.om.utility;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ApplicationConstants {
	
	public static Properties queryProps;
    
    public static final long TOKEN_EXPIRATION_MSEC = 180000000;
    public static final String SIGNING_SECRET = "926D96C90030DD58429D2751AC1BDBBC";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
    public static final long MAX_AGE_SECS = 3600;
    public static final String ENCRYPTED_SUFIX = ".enc";
    public static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    public static final String GOOGLE_RECAPTCHA_SECRE_KEY = "6LeJpMIUAAAAABO9Xmz0f6doRfwwodHZXyeBM8mc";
    public static final String JWT_TOKEN_COOKIES_NAME = "JWT-TOKEN";
    public static List<Locale> LOCALES = Arrays.asList(new Locale("en"),
            new Locale("gu"));
    
    //Payment Option
    public static final String BORROW = "Borrow";
    public static final String CASH = "Cash";
    
    //Message Key
    public static final String NOT_FOUND_KEY = "NotFoundKey";
    public static final String UNAUTHORIZED_ERROR_KEY = "UnauthorizedErrorKey";
    public static final String SERVER_ERROR_KEY = "ServerErrorKey";
    public static final String VALIDATION_FAILED_KEY = "ValidationFailedKey";
    public static final String ADDED_KEY = "AddedKey";
    public static final String UPDATED_KEY = "UpdatedKey";
    public static final String EXIST_KEY = "ExistKey";
    public static final String DELETED_KEY = "DeletedKey";
    public static final String GET_KEY = "GetKey";
    public static final String DOWNLOAD_KEY = "DownloadKey";
    public static final String REFERENCE_USED_KEY = "ReferenceUsedKey";
    public static final String PROPERTY_NOTFOUND_KEY = "PropertyNotFoundKey";
    public static final String IS_EXIST = "isExist";
    
    // Language Alphabet Constants
    public static final String GUJ_ALPHA[] = {"ક","ખ","ગ","ઘ","ચ","છ","જ","ઝ","ટ","ઠ","ડ","ઢ","ણ","ત","થ","દ","ધ","ન","પ","ફ","બ","ભ","મ","ય","ર","લ","વ","શ","ષ","સ","હ","ળ","ક્ષ","જ્ઞ","અ","ઈ","ઉ"};
    public static final String EN_ALPHA[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    public static final String META_FLAG_NAME = "N";
    public static final String META_FLAG_SERNAME = "S";
    public static final String LANG_EN = "en";
    public static final String LANG_GU = "gu";
    
    //Status Code
    public static final String STATUS_ACTIVE = "A";
    public static final String STATUS_INACTIVE = "I";
    public static final String STATUS_ALL = "AL";
    
    //Filter
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    
    //Role
    public static final String ROLE_SUPERVISOR = "SUPERVISOR";
    public static final String ROLE_COORDINATOR = "COORDINATOR";
    public static final String ROLE_CUSTOMER = "CUSTOMER";
    
    //API
    public static final String USER_API_PRIFIX = "/user/api/v1";
    public static final String SALES_API_PRIFIX = "/sales/api/v1";
    
    //Swagger Document 
    public static final String GROUP_USER = "USER";
    public static final String USER_API_BASEPACKAGR = "com.dgs.controller";
    public static final String GROUP_SALES = "SALES";
    public static final String SALES_API_BASEPACKAGR = "com.dgs.sales.controller";
    
    //Web Socket
    public static final String SOCKET_ENDPOINT = "/ws";
    public static final String SOCKET_BROKER = "/notification";
    public static final String SOCKET_PREFIX = "/app";
    public static final String SOCKET_USER_DEST_PREFIX = "/dgs";
    public static final String SOCKET_DASHBOARD_URL = SOCKET_BROKER + "/dashboard";
    
    // Query
    public static final String METADATA_NAME;
    public static final String USER_BORROW_WITH_CAT;
    public static final String USER_BORROW;
    public static final String USER_SALES_FOOD;
    public static final String USER_SALES_MILK;
    public static final String USER_SALES_BORROW;
    public static final String USER_BORROW_BY_USERID;
    
    public static void loadSQLProperties() {
    	String sqlFilePath = "/query.properties";
        try {
            log.info("Attempting to load query properties from: " + sqlFilePath);
            Resource resource = new ClassPathResource(sqlFilePath);
            queryProps = PropertiesLoaderUtils.loadProperties(resource);
            log.info("Loaded tenant specific properties");
        } catch (Exception ee) {
            log.error("Problem query properties from: " + sqlFilePath, ee);
        }
    }
    
    static {
    	loadSQLProperties();
    }
    
    static {
    	METADATA_NAME = queryProps.getProperty("metadata.name");
    	USER_BORROW = queryProps.getProperty("user.borrow");
    	USER_BORROW_BY_USERID = queryProps.getProperty("user.borrow.by.userid");
    	USER_BORROW_WITH_CAT = queryProps.getProperty("user.borrow.with.category");
    	USER_SALES_FOOD = queryProps.getProperty("user.sale.food");
    	USER_SALES_MILK = queryProps.getProperty("user.sale.milk");
    	USER_SALES_BORROW = queryProps.getProperty("user.sale.borrow");
    }
}
