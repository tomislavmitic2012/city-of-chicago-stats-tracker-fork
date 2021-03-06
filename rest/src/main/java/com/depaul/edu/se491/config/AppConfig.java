package com.depaul.edu.se491.config;

import com.depaul.edu.se491.dao.mongo.MongoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Created by adampodraza on 2/13/15.
 */
@Component
@ComponentScan
public class AppConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private static final Properties prop = new Properties();

    private AppConfig() {

    }

    static {
        InputStream in = AppConfig.class.getClassLoader().getResourceAsStream("app.properties");
        try {
            prop.load(in);
            in.close();
        } catch (IOException e) {
            logger.error("Unable to get app.properties", e);
        }
    }

    public static void setUpMongo() throws UnknownHostException {
        MongoDao.setDefaultMongo(prop.getProperty("app.mongo_uri"));
    }

    public static final StandardPasswordEncoder ec = new StandardPasswordEncoder(prop.getProperty("app.secret"));

    public static String getReferer(ContainerRequestContext requestContext) {
        String[] refererArr = requestContext.getHeaders().get("Origin").get(0).split("/");
        return String.format("%s//%s", refererArr[0], refererArr[2]);
    }

    public static String getReferer(HttpServletRequest httpServletRequest) {
        String[] refererArr = httpServletRequest.getHeader("Origin").split("/");
        return String.format("%s//%s", refererArr[0], refererArr[2]);
    }
}
