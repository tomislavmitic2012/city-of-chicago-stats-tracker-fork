package com.depaul.edu.se491.global;

import com.depaul.edu.se491.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Created by Tom Mitic on 2/14/15.
 */
public class AppConstants {

    private static final Logger logger = LoggerFactory.getLogger(AppConstants.class);

    private AppConstants() {

    }

    private static Properties prop;
    static {
        prop = new Properties();
        InputStream in = AppConstants.class.getClassLoader().getResourceAsStream("app.properties");
        try {
            prop.load(in);
            in.close();
        } catch (IOException e) {
            logger.error("Enable to get app.properties", e);
        }
        try {
            AppConfig.setUpMongo();
        } catch (UnknownHostException e) {
            logger.error("Unable to connect to mongo", e);
        }
    }

    public static final Integer MAX_RESULTS = new Integer(prop.getProperty("app.max_results"));
    public static final String UNAUTHORIZED_MESSAGE = prop.getProperty("app.unauthorized");
    public static final String SECRET_KEY = prop.getProperty("app.secret");
    public static final String UNAUTHORIZED_USER_NOT_FOUND = prop.getProperty("app.user_not_found");
    public static final String UNAUTHORIZED_USER_DISABLED = prop.getProperty("app.user_disabled");
    public static final String UNAUTHORIZED_USER_GENERAL_AUTHENTICATION_PROBLEM =
            prop.getProperty("app.user_general_authentication_problem");

    /**
     * CORS Header Constats
     */
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN_KEY = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_ALLOW_METHODS_KEY = "Access-Control-Allow-Methods";
    public static final String ACCESS_CONTROL_ALLOW_METHODS_VALUE = prop.getProperty("app.access_control_allow_methods");
    public static final String ACCESS_CONTROL_ALLOW_HEADERS_KEY = "Access-Control-Allow-Headers";
    public static final String ACCESS_CONTROL_ALLOW_HEADERS_VALUE = prop.getProperty("app.access_control_allow_headers");

    public static final String INCORRECT_OLD_PASSWORD = prop.getProperty("app.incorrect_password_message");
    public static final String SIGNIN_END_POINT = prop.getProperty("app.login_link");
    public static final String CREATEACCOUNT_END_POINT = prop.getProperty("app.createaccount");
    public static final String VERIFY_EMAIL_MESSAGE = prop.getProperty("app.verify_email_message");
    public static final String VERIFY_PASSWORD = prop.getProperty("app.verify_email_message");
    public static final String VERIFY_USER_ROLE_MESSAGE = prop.getProperty("app.verify_user_role_message");
    public static final String VERIFY_USER_ID_MESSAGE = prop.getProperty("app.verify_user_id_message");
    public static final String VERIFY_PASSWORD_MESSAGE = prop.getProperty("app.verify_password_message");
    public static final String VERIFY_FIRST_NAME_MESSAGE = prop.getProperty("app.verify_first_name_message");
    public static final String VERIFY_LAST_NAME_MESSAGE = prop.getProperty("app.verify_last_name_message");
    public static final String VERIFY_UUID_MESSAGE = prop.getProperty("app.verify_uuid_message");
    public static final String PROVIDED_DATA_INSUFFICIENT = prop.getProperty("app.provided_data_insufficient");
    public static final String USER_ALREADY_EXISTS = prop.getProperty("app.user_already_exists");
    public static final String USER_ALREADY_EXISTS_MESSAGE = prop.getProperty("app.user_already_exists_message");
    public static final String USER_DOESNT_EXIST = prop.getProperty("app.user_doesnt_exist");
    public static final String USER_DOESNT_EXIST_MESSAGE = prop.getProperty("app.user_doesnt_exist_message");
}
