package org.daemon;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.utils.JobUtils;

import java.util.Properties;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;

/**
 * Created by Tu Vo and Adam Pordraza.
 */
public class QuartzRunner {
    private static final Logger logger = LoggerFactory.getLogger(QuartzRunner.class);
    private static SchedulerFactory schedFact;
    private static Scheduler sched;

    //Hour and minute parameters
    private static int runAtHour;
    private static int runCrimeAtMinute;
    private static int runPotholesAtMinute;
    private static int runFoodInspectionsAtMinute;
    private static int runSalariesAtMinute;

    private static Properties props = JobUtils.getAppPropertyValues();

    QuartzRunner(int runAtHour, int runCrimeAtMinute,
                 int runPotholesAtMinute, int runFoodInspectionsAtMinute,
                 int runSalariesAtMinute){
        QuartzRunner.runAtHour = runAtHour;
        QuartzRunner.runCrimeAtMinute = runCrimeAtMinute;
        QuartzRunner.runPotholesAtMinute = runPotholesAtMinute;
        QuartzRunner.runFoodInspectionsAtMinute = runFoodInspectionsAtMinute;
        QuartzRunner.runSalariesAtMinute = runSalariesAtMinute;
    }

    //Start method handles all the overhead of the Quartz job
    public static void start() {
        schedFact = new StdSchedulerFactory();

        try {
            // define the job1 and tie it to CrimeJob class
            JobDetail job1 = JobBuilder.newJob(CrimeJob.class)
                    .withIdentity("job1", "group1")
                    .build();
            // define the job2 and tie it to PotholesReportedJob class
            JobDetail job2 = JobBuilder.newJob(PotholesReportedJob.class)
                    .withIdentity("job2", "group1")
                    .build();
            // define the job3 and tie it to FoodInspectionJob class
            JobDetail job3 = JobBuilder.newJob(FoodInspectionJob.class)
                    .withIdentity("job3", "group1")
                    .build();
            // define the job4 and tie it to FoodInspectionJob class
            JobDetail job4 = JobBuilder.newJob(EmployeeSalaryJob.class)
                    .withIdentity("job4", "group1")
                    .build();

            // Trigger the job1 to run on runAthHour and runCrimeAtMinute param
            Trigger trigger1 = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(dailyAtHourAndMinute(runAtHour, runCrimeAtMinute))
                    .build();
            // Trigger the job2 to run on runAthHour and runPotholesAtMinute param
            Trigger trigger2 = TriggerBuilder.newTrigger()
                    .withIdentity("trigger2", "group1")
                    .startNow()
                    .withSchedule(dailyAtHourAndMinute(runAtHour, runPotholesAtMinute))
                    .build();
            // Trigger the job3 to run on runAthHour and runFoodInspectionsAtMinute param
            Trigger trigger3 = TriggerBuilder.newTrigger()
                    .withIdentity("trigger3", "group1")
                    .startNow()
                    .withSchedule(dailyAtHourAndMinute(runAtHour, runFoodInspectionsAtMinute))
                    .build();
            // Trigger the job3 to run on runAthHour and runSalariesAtMinute param
            Trigger trigger4 = TriggerBuilder.newTrigger()
                    .withIdentity("trigger4", "group1")
                    .startNow()
                    .withSchedule(dailyAtHourAndMinute(runAtHour, runSalariesAtMinute))
                    .build();

            sched = schedFact.getScheduler();
            sched.start();

            //schedules job1 with the job1 and the trigger1
            sched.scheduleJob(job1, trigger1);
            sched.scheduleJob(job2, trigger2);
            sched.scheduleJob(job3, trigger3);
            sched.scheduleJob(job4, trigger4);
        } catch (SchedulerException e) {
            logger.error("SchedulerException", e);
        }
    }

    public static void main(String [] args) {

        // Crime Job
        new QuartzRunner(
                Integer.parseInt(props.getProperty("quartz_job_hour")),
                Integer.parseInt(props.getProperty("quartz_crime_job_minute")),
                Integer.parseInt(props.getProperty("quartz_pothole_job_minute")),
                Integer.parseInt(props.getProperty("quartz_foodinspection_job_minute")),
                Integer.parseInt(props.getProperty("quartz_salaries_job_minute")));
        QuartzRunner.start();
    }
}
