package org.employee_salary_daemon;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class EmployeeSalaryJob implements Job{
	
	private CloseableHttpClient httpClient = HttpClients.createDefault();

	public void execute(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		HttpGet httpGet = new HttpGet(
				"https://data.cityofchicago.org/resource/xzkq-xp2w.json");

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
			DB db = mongoClient.getDB("test");
			DBCollection collection = db.getCollection("salaryData");

			// parsing JSON
			BasicDBList data = (BasicDBList) JSON.parse(jsonStr);
			
			//insert to collection
			for (int i = 0; i < data.size(); i++) {
				collection.insert((DBObject) data.get(i));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

}
