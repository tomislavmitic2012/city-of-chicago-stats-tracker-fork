package com.depaul.edu.se491.config;

import com.depaul.edu.se491.dao.mongo.MongoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

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

    public static void setUpMongo() throws UnknownHostException {
        Properties prop = new Properties();
        InputStream in = AppConfig.class.getClassLoader().getResourceAsStream("app.properties");
        try {
            prop.load(in);
            in.close();
        } catch (IOException e) {
            logger.error("Unable to get app.properties", e);
        }

        MongoDao.setDefaultMongo(prop.getProperty("app.mongo_uri"));
    }
}
