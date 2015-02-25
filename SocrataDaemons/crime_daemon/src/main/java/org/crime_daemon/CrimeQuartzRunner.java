package org.crime_daemon;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;

/**
 * Created by Tu Vo on 2/20/15.
 */
public class CrimeQuartzRunner {
	
	  private static SchedulerFactory schedFact;
	    private static Scheduler sched;
	    //Hour and minute parameters
	    private static int runAtHour;
	    private static int runAtMinute;

	    CrimeQuartzRunner(int runAtHour, int runAtMinute){
	        CrimeQuartzRunner.runAtHour = runAtHour;
	        CrimeQuartzRunner.runAtMinute = runAtMinute;
	    }

	    public static int getRunAtHour() {
	        return runAtHour;
	    }

	    public static void setRunAtHour(int runAtHour) {
	    	CrimeQuartzRunner.runAtHour = runAtHour;
	    }

	    public static int getRunAtMinute() {
	        return runAtMinute;
	    }

	    public static void setRunAtMinute(int runAtMinute) {
	    	CrimeQuartzRunner.runAtMinute = runAtMinute;
	    }

	    //Start method handles all the overhead of the Quartz job
	    public static void start() {
	        schedFact = new StdSchedulerFactory();

	        try {
	            sched = schedFact.getScheduler();

	            sched.start();

	            // define the job and tie it to CrimeJob class
	            JobDetail job = JobBuilder.newJob(CrimeJob.class)
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
	    	
	    	new CrimeQuartzRunner (23,39);
	    	CrimeQuartzRunner.start();
	    }

	}
