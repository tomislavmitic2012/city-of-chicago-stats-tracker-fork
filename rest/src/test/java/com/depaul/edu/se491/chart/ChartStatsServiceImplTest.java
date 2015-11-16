package com.depaul.edu.se491.chart;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.bson.BasicBSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.depaul.edu.se491.service.mongo.MongoService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:/spring/applicationContext-Test.xml")
public class ChartStatsServiceImplTest {
	
	@Autowired
	private MongoService ser;
	
	private static List<DBObject> dataList;
	private static BasicDBObject data;
	static {
		data = new BasicDBObject();
		data.put("ARREST", "N");
		data.put("CASE#", "HX263802");
		data.put("DATE OF OCCURRENCE", "05/05/2014 07:30:00 PM");
		data.put("DOMESTIC", "N");		
	}

	@Test
	public void testDateFormat() {
		
		int year = 0;
        String date = data.getString("DATE OF OCCURRENCE");			
      
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aaa", Locale.ENGLISH);        
        Calendar cal = Calendar.getInstance();        
         
         try {
        	Date d = null;
			d = format.parse(date);			
			cal.setTime(d);
			year = cal.get(Calendar.YEAR);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
         assertEquals(2014, year);
         System.out.println(year);
		
	}

}
