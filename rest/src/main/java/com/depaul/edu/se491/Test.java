package com.depaul.edu.se491;

import com.socrata.api.HttpLowLevel;
import com.socrata.api.Soda2Consumer;
import com.socrata.exceptions.LongRunningQueryException;
import com.socrata.exceptions.SodaError;
import com.socrata.model.soql.SoqlQuery;
import com.sun.jersey.api.client.ClientResponse;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {
    public final static Logger LOGGER = Logger.getLogger(Test.class.getName());

    public static void main(String[] args) {
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/resources/app.properties");
            prop.load(fis);
            fis.close();
        } catch(Throwable x) {
            LOGGER.log(Level.INFO, String.format("Problem with loading properties: %s", x));
        }

        Soda2Consumer consumer = Soda2Consumer.newConsumer("https://sandbox.demo.socrata.com",
                "testuser@gmail.com",
                "OpenData",
                "D8Atrg62F2j017ZTdkMpuZ9vY");
        Soda2Consumer consumer2 = Soda2Consumer.newConsumer("https://data.cityofchicago.org",
                prop.getProperty("app.socrata_username"),
                prop.getProperty("app.socrata_password"),
                prop.getProperty("app.socrata_app_token"));

        //To get a raw String of the results
        ClientResponse response = null;
        try {
            response = consumer2.query("z8bn-74gv",
                    HttpLowLevel.JSON_TYPE,
                    SoqlQuery.SELECT_ALL);
        } catch (LongRunningQueryException e) {
            e.printStackTrace();
        } catch (SodaError sodaError) {
            sodaError.printStackTrace();
        }
        String payload = response.getEntity(String.class);
        System.out.println(payload);

        //Get get this automatcally serialized into a set of Java Beans annotated with Jackson JOSN annotations
        /*List<Station> stations = null;
        try {
            stations = consumer2.query("z8bn-74gv",
                    SoqlQuery.SELECT_ALL,
                    Station.LIST_TYPE);
        } catch (SodaError sodaError) {
            sodaError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TestCase.assertTrue(stations.size() > 0);
        System.out.println(stations.size());
        */
    }
}
