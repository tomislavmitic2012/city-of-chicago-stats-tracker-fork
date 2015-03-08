import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
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
import java.util.Properties;

/**
 * Created by adampodraza on 2/16/15.
 */
public class FoodInspectionJob implements Job {

    private CloseableHttpClient httpClient = HttpClients.createDefault();



 
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        HttpGet httpGet = new HttpGet("https://data.cityofchicago.org/resource/4ijn-s7e5.json?");

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();

            //System.out.println(entity.getContent().read());
            BufferedHttpEntity buf = new BufferedHttpEntity(entity);
            System.out.println(buf == null);

            if (buf != null) {
                String jsonString = buf.toString();


                } else
                    System.out.println("Entity was empty");


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
            com.mongodb.DBCollection collection = db.getCollection("crimesData");

            // parsing JSON
            com.mongodb.BasicDBList data = (BasicDBList) com.mongodb.util.JSON.parse(jsonStr);

            //insert to collection
            for (int i = 0; i < data.size(); i++) {
                collection.insert((DBObject) data.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
