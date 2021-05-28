import java.util.*;

/**
 * @author Matt Brierley
 * @version 3/24/19
 */

public class Job implements Comparable<Job>
{
    private int jobNo;
    private int priority;
    private int createdAtTime;
    private int timeLeft;
    private static int jobsCreated = 0;

    public Job(int prio, int time, int duration)
    {
        jobsCreated++;
        this.jobNo = jobsCreated;
        this.priority = prio;
        this.createdAtTime = time;
        this.timeLeft = duration;
    }

    public int compareTo(Job other)
    {
        /*
        if(this.priority == other.priority)
        {
            return 0;
        }
        else if(this.priority > other.priority)
        {
            return 1;
        }
        else
        {
            return -1;
        }
        */
        return (this.priority - other.priority);
    }

    public int getTimeLeft()
    {
        return this.timeLeft;
    }

    public static int getJobsCreated()
    {
        return jobsCreated;
    }

    public void update(int time)
    {
        this.timeLeft -= time;
    }

    public boolean isFinished()
    {
        return this.timeLeft <= 0;
    }

    public String toString()
    {
        return "Job #" + this.jobNo + " priority(" + this.priority
                + ") created at " + this.createdAtTime + ", time left " + this.timeLeft;
    }
}
