package org.daemon;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.utils.JobUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by Tu Vo on 2/20/15.
 */
public class CrimeJob implements Job {

    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final Logger logger = LoggerFactory.getLogger(CrimeJob.class);

    public void execute(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        Properties props = JobUtils.getAppPropertyValues();
        String[] dates = JobUtils.getDate();
        HttpGet httpGet = new HttpGet(String.format(props.getProperty("socrata_crime_url"),
                dates[0], dates[1]));
        httpGet.setHeader("X-App-Token", props.getProperty("socrata_app_token"));

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);

            BufferedReader buf = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = buf.readLine()) != null) {
                builder.append(str);
            }

            String jsonStr = builder.toString();

            JobUtils.writeToMongoDB(jsonStr, "socrata_crime_collection", "crime_id_key");

            response.close();

        } catch (IOException e) {
            logger.error("IOException", e);

        }

    }
}
