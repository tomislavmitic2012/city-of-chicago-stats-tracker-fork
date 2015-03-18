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
}
