public class Despose extends Element {
    private double nextClientEndTime;
    private double nextClientStartTime;
    private int nextClientFailures;
    private double meanTime = 0.0;
    private int failures;

    public Despose() {
        super();
        super.setTnext(Double.MAX_VALUE);
        setName("Despose");
    }

    public void inAct(double startTime, int clientFailures) {
        nextClientStartTime = startTime;
        nextClientFailures = clientFailures;

        super.inAct();
        if (super.getNextElement() != null)
            super.getNextElement().inAct();

        this.outAct();
    }

    public void outAct() {
        super.outAct();
        nextClientEndTime = getTcurr();
        meanTime += nextClientEndTime - nextClientStartTime;
        failures += nextClientFailures;
    }

    public double getMeanTime() {
        return meanTime;
    }
}