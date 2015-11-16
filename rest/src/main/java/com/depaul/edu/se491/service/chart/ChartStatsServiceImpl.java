package com.depaul.edu.se491.service.chart;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.depaul.edu.se491.service.mongo.MongoService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ChartStatsServiceImpl implements ChartStatsService {

	@Autowired
	public MongoService ser;

	@Override
	public List<DBObject> CaculateCrimesStats(String collection) {

		HashMap<Integer, Integer> hmap = new HashMap<Integer, Integer>();
		DBCursor result = ser.findCollection(collection);
		String collStr = "district";
		
		while (result.hasNext()) {
			BasicDBObject obj = (BasicDBObject) result.next();
			String disStr = obj.getString(collStr);

			try {
				int dist = Integer.parseInt(disStr);
				if (hmap.containsKey(dist)) {
					hmap.put(dist, hmap.get(dist) + 1);
				} else
					hmap.put(dist, 1);
			}catch (NumberFormatException ex) {
				continue;
			}
		}
		//sort arrayList from least to most
		Map<Integer, Integer> map = new TreeMap<Integer, Integer>(
				new Comparator<Integer>() {
					@Override
					public int compare(Integer o1, Integer o2) {
						return o1.compareTo(o2);
					}
				});

		map.putAll(hmap);
		
		List<DBObject> listObj = new ArrayList<DBObject>();
		List<Integer> arrayKey = new ArrayList<Integer>(map.keySet());
		List<Integer> arrayVal = new ArrayList<Integer>(map.values());		
		List<String> arrayKeyStr = convertIntToString(arrayKey,collStr);
		
		BasicDBObject bo = new BasicDBObject();
		bo.append("Yaxis", arrayVal);
		bo.append("Xaxis", arrayKeyStr);

		listObj.add(bo);

		return listObj;
	}
	
	@Override
	public List<DBObject> CaculatePotholesStats(String collection) {
		DBCursor result = ser.findCollection(collection);
		HashMap<Integer, Integer> hmap = new HashMap<Integer, Integer>();
		String collStr = "community_area";
		
		while (result.hasNext()) {
			BasicDBObject obj = (BasicDBObject) result.next();
			String collStrResult = obj.getString(collStr);

			try {
				int dist = Integer.parseInt(collStrResult);

				if (hmap.containsKey(dist)) {
					hmap.put(dist, hmap.get(dist) + 1);
				} else
					hmap.put(dist, 1);
			} catch (NumberFormatException ex) {
				continue;
			}
		}
		
		/**sort district form low to high*/
		List<DBObject> listObj = new ArrayList<DBObject>();
		Map<Integer, Integer> map = new TreeMap<Integer, Integer>(
				new Comparator<Integer>() {
					@Override
					public int compare(Integer o1, Integer o2) {
						return o1.compareTo(o2);
					}
				});

		map.putAll(hmap);

		List<Integer> arrayKey = new ArrayList<Integer>(map.keySet());
		List<Integer> arrayVal = new ArrayList<Integer>(map.values());
		List<String> arrayKeyStr = convertIntToString(arrayKey, String.join(" ", collStr.split("_")));

		BasicDBObject bo = new BasicDBObject();
		bo.append("Yaxis", arrayVal);
		bo.append("Xaxis", arrayKeyStr);

		listObj.add(bo);

		return listObj;
	}

	@Override
	public List<DBObject> CaculateSalariesStats(String collection) {
		DBCursor result = ser.findCollection(collection);
		HashMap<String, Integer> mapEmpNum = new HashMap<String, Integer>();
		HashMap<String, Integer> mapSalSum = new HashMap<String, Integer>();
		HashMap<String, Integer> mapResult = new HashMap<String, Integer>();
		List<DBObject> listObj = new ArrayList<DBObject>();

		while (result.hasNext()) {

			BasicDBObject obj = (BasicDBObject) result.next();
			String dep = obj.getString("department");
			String sal = obj.getString("employee_annual_salary");

			//StringBuilder sb = new StringBuilder(sal);
			//String sal1 = sb.deleteCharAt(0).toString();

			int salary = (int) Double.parseDouble(sal);
			try {
				if (mapEmpNum.containsKey(dep)) {
					// get number of employee
					mapEmpNum.put(dep, (mapEmpNum.get(dep) + 1));
					mapSalSum.put(dep, (mapSalSum.get(dep) + salary));
				} else {
					// insert salary and department into value()
					mapEmpNum.put(dep, 1);
					mapSalSum.put(dep, salary);
				}
			} catch (NumberFormatException ex) {
				continue;
			}
			// get sum of salary for each department and place into hashmap
			int averageSalary = mapSalSum.get(dep) / mapEmpNum.get(dep);
			mapResult.put(dep, averageSalary);
		}
		// sort mashMap by alphabet
		Map<String, Integer> map = new TreeMap<String, Integer>(mapResult);

		List<String> arrayKey = new ArrayList<String>(map.keySet());
		List<Integer> arrayVal = new ArrayList<Integer>(map.values());

		BasicDBObject bo = new BasicDBObject();
		bo.append("Yaxis", arrayVal);
		bo.append("Xaxis", arrayKey);

		listObj.add(bo);
		return listObj;
	}

	@Override
	public List<DBObject> CaculateViolationsStats(String collection) {
		DBCursor result = ser.findCollection(collection);
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();

		while (result.hasNext()) {
			BasicDBObject obj = (BasicDBObject) result.next();
			String collectStr = obj.getString("results");

			try {

				if (hmap.containsKey(collectStr)) {
					hmap.put(collectStr, hmap.get(collectStr) + 1);
				} else
					hmap.put(collectStr, 1);
			} catch (NumberFormatException ex) {
				continue;
			}
		}
		
		List<DBObject> listObj = new ArrayList<DBObject>();
	
		List<String> arrayKey = new ArrayList<String>(hmap.keySet());
		List<Integer> arrayVal = new ArrayList<Integer>(hmap.values());

		BasicDBObject bo = new BasicDBObject();
		bo.append("Yaxis", arrayVal);
		bo.append("Xaxis", arrayKey);

		listObj.add(bo);

		return listObj;
	}
	
	public List<String> convertIntToString (List<Integer> listIn, String str){
		
		List<String> arrayKeyStr = new ArrayList<String>(listIn.size());
		for (Integer myInt : listIn) {
			arrayKeyStr.add(String.valueOf(str+ " "+ myInt)); 
		}
		return arrayKeyStr;
	}
}
