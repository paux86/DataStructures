import java.text.*;
import java.util.*;

/**
 * @author Matt Brierley
 * @version 3/24/19
 */

public class SchedulingSimulation
{
    private PriorityQueue<Job> waitingJobs;
    private ArrayDeque<Job> allJobs;
    private Job currentJob;
    private final int TIME_SLICE_PER_JOB = 3;
    private final int SIMULATION_DURATION = 100;
    private final int JOB_PROBABILITY = 30;
    private final int JOB_PRIORITY = 4;
    private final int JOB_MIN_TIME = 1;
    private final int JOB_MAX_TIME = 5;
    private static final int SORT_BY_PRIORITY = 0;
    private static final int SORT_BY_LENGTH = 1;

    public SchedulingSimulation(int sortBy)
    {
        if(sortBy == SORT_BY_PRIORITY)
        {
            this.waitingJobs = new PriorityQueue<Job>();
        }
        else if(sortBy == SORT_BY_LENGTH)
        {
            this.waitingJobs = new PriorityQueue<Job>(new Comparator<Job>()
                {
                    public int compare(Job job1,Job job2)
                    {
                        return job1.getTimeLeft() - job2.getTimeLeft();
                    }
                });
        }
        this.currentJob = null;
        this.allJobs = new ArrayDeque<Job>();
    }

    public void runSimulation()
    {
        int jobStarted = 0;
        int tick = 1;
        for (int clock = 0; clock < SIMULATION_DURATION; clock+=tick)
        {
            reportAtTimeMarker(clock);

            System.out.printf("\texecuting: %s\n", (this.currentJob != null ?  this.currentJob.toString() : "NONE"));
            generateJob(clock);

            //if a job is currently being processed, continue processing
            if(this.currentJob != null)
            {
                this.currentJob.update(tick);

                if(this.currentJob.isFinished())
                {
                    System.out.printf("\tcompleted: Current job at time %d\n", clock);
                    this.allJobs.addFirst(this.currentJob);
                    this.currentJob = null;
                }
                else if(clock - jobStarted > TIME_SLICE_PER_JOB)
                {
                    this.waitingJobs.offer(this.currentJob);
                    currentJob = null;
                }
            }

            //if there is no current job and there is a job waiting, begin processing top of queue
            if(this.currentJob == null && !this.waitingJobs.isEmpty())
            {
                this.currentJob = waitingJobs.poll();
                jobStarted = clock;
            }
        }
        finalSimulationReport();
    }

    public void generateJob(int createdAt)
    {
        Random random = new Random();
        if(random.nextInt(100) < JOB_PROBABILITY)
        {
            Job newJob = new Job(random.nextInt(JOB_PRIORITY) + 1, createdAt, random.nextInt(JOB_MAX_TIME - JOB_MIN_TIME + 1) + 1);
            this.waitingJobs.offer(newJob);
            System.out.printf("\tcreated: %s\n", newJob.toString());
        }
    }

    public void reportAtTimeMarker(int simulationTimer)
    {
        System.out.println("Time marker " + simulationTimer + "\twaiting: " + this.waitingJobs.size());
    }

    public void finalSimulationReport()
    {
        System.out.println("*****************  Final Report  ***************");
        System.out.println("\tActive jobs:");

        int numUnfinished = 0;
        int unfinishedTime = 0;
        while(!this.waitingJobs.isEmpty())
        {
            Job current = this.waitingJobs.poll();
            numUnfinished++;
            unfinishedTime += current.getTimeLeft();
            System.out.println(current.toString());
        }
        double averageTimeLeft = (unfinishedTime > 0 ? (double)unfinishedTime / numUnfinished : 0);
        System.out.println("\n\tThe number of jobs currently executing is " + numUnfinished);
        System.out.println("\tThe number of completed jobs is " + (Job.getJobsCreated() - numUnfinished));
        System.out.println("\tThe total number of jobs is " + Job.getJobsCreated());
        System.out.println("\tThe average time left for unfinished jobs is " + averageTimeLeft);
    }

    public static void main(String args[])
    {
        //int priorityMode = SORT_BY_PRIORITY;
        int priorityMode = SORT_BY_LENGTH;
        SchedulingSimulation simulator = new SchedulingSimulation(priorityMode);

        System.out.printf("***STARTING THE SIMULATION WITH PRIORITY MODE SET TO %s***\n", (priorityMode == SORT_BY_PRIORITY ? "SORT_BY_PRIORITY" : "SORT_BY_LENGTH"));
        simulator.runSimulation();
    }
}
