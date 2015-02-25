import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.MutableTrigger;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;

/**
 * Created by adampodraza on 2/16/15.
 * This class schedules a Quartz Job. Uses an
 * int hour and int minute parameter to schedule one time jobs.
 */
public class FoodInspectionQuartzRunner {

    private static SchedulerFactory schedFact;
    private static Scheduler sched;
    //Hour and minute parameters
    private static int runAtHour;
    private static int runAtMinute;

    FoodInspectionQuartzRunner(int runAtHour, int runAtMinute){
        this.runAtHour = runAtHour;
        this.runAtMinute = runAtMinute;
    }

    public static int getRunAtHour() {
        return runAtHour;
    }

    public static void setRunAtHour(int runAtHour) {
        FoodInspectionQuartzRunner.runAtHour = runAtHour;
    }

    public static int getRunAtMinute() {
        return runAtMinute;
    }

    public static void setRunAtMinute(int runAtMinute) {
        FoodInspectionQuartzRunner.runAtMinute = runAtMinute;
    }

    //Start method handles all the overhead of the Quartz job
    public static void start() {
        schedFact = new StdSchedulerFactory();

        try {
            sched = schedFact.getScheduler();

            sched.start();

            //creates a job for the scheduler
            JobDetail job = JobBuilder.newJob(FoodInspectionJob.class)
                    .withIdentity("myTrigger", "group1")
                    .build();

            //creates a trigger for the scheduler
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .startNow()
                    .withSchedule(dailyAtHourAndMinute(runAtHour, runAtMinute))
                    .build();

            //schedules job with the job and the trigger
            sched.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    //basic main method
    public static void main(String [] args) {
        FoodInspectionQuartzRunner foodInspectionQuartzRunner = new FoodInspectionQuartzRunner(23, 59);
        foodInspectionQuartzRunner.start();
    }

}
