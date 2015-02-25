package org.employee_salary_daemon;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;


import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class EmployeeSalaryQuartzRunner {
	
	 private static SchedulerFactory schedFact;
	    private static Scheduler sched;
	    //Hour and minute parameters
	    private static int runAtHour;
	    private static int runAtMinute;

	    EmployeeSalaryQuartzRunner(int runAtHour, int runAtMinute){
	    	EmployeeSalaryQuartzRunner.runAtHour = runAtHour;
	    	EmployeeSalaryQuartzRunner.runAtMinute = runAtMinute;
	    }

	    public static int getRunAtHour() {
	        return runAtHour;
	    }

	    public static void setRunAtHour(int runAtHour) {
	    	EmployeeSalaryQuartzRunner.runAtHour = runAtHour;
	    }

	    public static int getRunAtMinute() {
	        return runAtMinute;
	    }

	    public static void setRunAtMinute(int runAtMinute) {
	    	EmployeeSalaryQuartzRunner.runAtMinute = runAtMinute;
	    }

	    //Start method handles all the overhead of the Quartz job
	    public static void start() {
	        schedFact = new StdSchedulerFactory();

	        try {
	            sched = schedFact.getScheduler();

	            sched.start();

	            // define the job and tie it to CrimeJob class
	            JobDetail job = JobBuilder.newJob(EmployeeSalaryJob.class)
	                    .withIdentity("job1", "group1")
	                    .build();

	            // Trigger the job to run on runAthHour and run AtMinute param
	            Trigger trigger = TriggerBuilder.newTrigger()
	                    .withIdentity("trigger1", "group1")
	                    .startNow()
	                    .withSchedule(dailyAtHourAndMinute(runAtHour, runAtMinute))
	                    .build();

	            //schedules job with the job and the trigger
	            sched.scheduleJob(job, trigger);

	        } catch (SchedulerException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void main(String [] args) {
	    	
	    	new EmployeeSalaryQuartzRunner (23,36);
	    	EmployeeSalaryQuartzRunner.start();
	    }

}
