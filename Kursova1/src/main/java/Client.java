public class Client
{
    private double startTime, endTime;
    private int failures = 0;

    public Client(double startTime)
    {
        this.startTime = startTime;
        this.endTime = 0.0;
    }

    public void setEndTime(double endTime)
    {
        this.endTime = endTime;
    }

    public void setStartTime(double startTime)
    {
        this.startTime = startTime;
    }

    public void addFailure()
    {
        failures++;
    }

    public double getEndTime()
    {
        return endTime;
    }

    public double getStartTime()
    {
        return startTime;
    }

    public int getFailures()
    {
        return failures;
    }
}