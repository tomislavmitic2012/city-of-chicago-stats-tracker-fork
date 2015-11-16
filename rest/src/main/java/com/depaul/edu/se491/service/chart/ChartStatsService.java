package com.depaul.edu.se491.service.chart;

import java.util.List;

import com.mongodb.DBObject;

public interface ChartStatsService {
	
	
	/** 
	 *return arrayList of crimes per district
	 */
	public List<DBObject> CaculateCrimesStats(String collection);

	/** 
	 *return arrayList of pot holes per community area
	 */
	public List<DBObject> CaculatePotholesStats(String collection);

	/** 
	 *return arrayList of average salary of personnel by department
	 */
	public List<DBObject> CaculateSalariesStats(String collection);

	/** 
	 *return arrayList of violations per Facility Type
	 */
	public List<DBObject> CaculateViolationsStats(String collection);

}
