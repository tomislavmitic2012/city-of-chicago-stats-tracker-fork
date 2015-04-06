import com.mongodb.MongoClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Created by adampodraza on 2/18/15.
 */
public class PotholesReportedJob implements Job {

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    private String getDate() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -1);

        return dateFormat.format(cal.getTime());

    }


    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        HttpGet httpGet = new HttpGet("https://data.cityofchicago.org/resource/7as2-ds3y.json?$where=creation_date%3c%27" + getDate() + "%27");

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);

            BufferedReader buf = new BufferedReader(new InputStreamReader(
                    (response.getEntity().getContent())));

            StringBuilder builder = new StringBuilder();
            String str = "";

            while ((str = buf.readLine()) != null) {
                builder.append(str);
            }

            String jsonStr = builder.toString();
            writeToMongoDB(jsonStr);

            response.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Properties getMongoPropertyValue() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("src/main/resources/mongoDB.properties");
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return prop;
    }

    private void writeToMongoDB(String jsonStr) {

        try {

            Properties mongoProperty = getMongoPropertyValue();
            String dbHost = mongoProperty.getProperty("mongo_host");

            MongoClient mongoClient = new MongoClient(dbHost);

            // get select database
            com.mongodb.DB db = mongoClient.getDB("test");
            com.mongodb.DBCollection collection = db.getCollection("potholes");

            // parsing JSON
            com.mongodb.BasicDBList data = (com.mongodb.BasicDBList) com.mongodb.util.JSON.parse(jsonStr);

            //insert to collection
            for (int i = 0; i < data.size(); i++) {
                collection.insert((com.mongodb.DBObject) data.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
