package org.utils;

import com.mongodb.*;
import com.mongodb.util.JSON;
import org.daemon.CrimeJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Tomislav S. Mitic on 6/2/15.
 */
public class JobUtils {

    private static final Logger logger = LoggerFactory.getLogger(CrimeJob.class);

    public static String[] getDate() {

        String[] s = new String[2];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal1 = Calendar.getInstance(), cal2 = Calendar.getInstance();
        cal1.add(Calendar.DAY_OF_YEAR, -1);
        s[0] = dateFormat.format(cal1.getTime());

        cal2.add(Calendar.DAY_OF_YEAR, -2);
        s[1] = dateFormat.format(cal2.getTime());

        return s;
    }

    public static Properties getAppPropertyValues() {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = CrimeJob.class.getClassLoader().getResourceAsStream("app.properties");
            prop.load(input);

        } catch (IOException ex) {
            logger.error("IOException", ex);

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error("IOException", e);

                }
            }
        }

        return prop;
    }

    public static void writeToMongoDB(String jsonStr, String col, String id_key) {
        try {

            Properties props = getAppPropertyValues();
            String dbHost = props.getProperty("mongo_host");

            MongoClient mongoClient = new MongoClient(dbHost);

            // get select database
            DB db = mongoClient.getDB(props.getProperty("mongo_db"));
            DBCollection collection = db.getCollection(props.getProperty(col));

            // parsing JSON
            BasicDBList data = (BasicDBList) JSON.parse(jsonStr);

            //insert to collection
            Date updatedTS = new Date();
            for (int i = 0; i < data.size(); i++) {
                ((BasicDBObject) data.get(i)).put("updated_ts", updatedTS);
                DBCursor cursor = collection.find(new BasicDBObject(props.getProperty(id_key), (
                        (BasicDBObject) data.get(i)).get(props.getProperty(id_key))));
                if (cursor.size() == 0) {
                    logger.info(String.format("---------- Inserting %s Document ----------", col));
                    collection.insert((DBObject) data.get(i));
                } else {
                    logger.info(String.format("---------- Updating %s Document ----------", col));
                    collection.update(new BasicDBObject(props.getProperty(id_key),
                                    ((BasicDBObject) data.get(i)).get(props.getProperty(id_key))),
                            (DBObject) data.get(i));
                }
            }

        } catch (IOException e) {
            logger.error("IOException", e);

        }

    }
}
