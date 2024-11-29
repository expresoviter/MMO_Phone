public class Despose extends Element
{
    private Client nextClient;
    private double meanTime = 0.0;
    private int failures;

    public Despose()
    {
        super();
        super.setTnext(Double.MAX_VALUE);
        setName("Despose");
    }

    public void inAct(Client client)
    {
        nextClient = client;

        super.inAct();
        if (super.getNextElement() != null)
            super.getNextElement().inAct();

        this.outAct();
    }

    public void outAct()
    {
        super.outAct();
        nextClient.setEndTime(getTcurr());
        meanTime += nextClient.getEndTime() - nextClient.getStartTime();
        failures += nextClient.getFailures();
    }

    public double getMeanTime()
    {
        return meanTime;
    }

    public int getFailures()
    {
        return failures;
    }
}