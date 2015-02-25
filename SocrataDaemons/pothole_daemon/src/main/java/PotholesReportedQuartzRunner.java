import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;

/**
 * Created by adampodraza on 2/18/15.
 * This class schedules a Quartz Job. Uses an
 * int hour and int minute parameter to schedule one time jobs.
 */
public class PotholesReportedQuartzRunner {
    private static SchedulerFactory schedFact;
    private static Scheduler sched;
    //Hour and minute parameters
    private static int runAtHour;
    private static int runAtMinute;

    PotholesReportedQuartzRunner(int runAtHour, int runAtMinute){
        this.runAtHour = runAtHour;
        this.runAtMinute = runAtMinute;
    }

    public static int getRunAtHour() {
        return runAtHour;
    }

    public static void setRunAtHour(int runAtHour) {
        PotholesReportedQuartzRunner.runAtHour = runAtHour;
    }

    public static int getRunAtMinute() {
        return runAtMinute;
    }

    public static void setRunAtMinute(int runAtMinute) {
        PotholesReportedQuartzRunner.runAtMinute = runAtMinute;
    }

    //Handles overhead of quartz job
    public static void start() {
        schedFact = new StdSchedulerFactory();

        try {
            sched = schedFact.getScheduler();

            sched.start();

            //creates job for the scheduler
            JobDetail job = JobBuilder.newJob(PotholesReportedJob.class)
                    .withIdentity("myTrigger", "group1")
                    .build();

            //creates trigger for the scheduler
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .startNow()
                    .withSchedule(dailyAtHourAndMinute(runAtHour, runAtMinute))
                    .build();

            //binds job and trigger to the scheduler
            sched.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args) {
        PotholesReportedQuartzRunner foodInspectionQuartzRunner = new PotholesReportedQuartzRunner(9, 54);
        PotholesReportedQuartzRunner.start();
    }
}
