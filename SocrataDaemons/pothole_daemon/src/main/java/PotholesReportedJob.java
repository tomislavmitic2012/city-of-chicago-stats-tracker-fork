import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by adampodraza on 2/18/15.
 */
public class PotholesReportedJob implements Job {

    private CloseableHttpClient httpClient = HttpClients.createDefault();


    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        HttpGet httpGet = new HttpGet("https://data.cityofchicago.org/resource/7as2-ds3y.json?");

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();


            BufferedHttpEntity buf = new BufferedHttpEntity(entity);

            if (buf != null) {
                writeToFile(buf);

            } else
                System.out.println("Entity was empty");


            response.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeToFile(HttpEntity entity) {
        try {

            //creates new file in the base directory to hold returned json
            File foodInspections = new File("PotholesReported.txt");
            FileOutputStream os = new FileOutputStream(foodInspections);

            entity.writeTo(os);
            while(entity.isStreaming()) {
                entity.writeTo(os);
            }

            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
