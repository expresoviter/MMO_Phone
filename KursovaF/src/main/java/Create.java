import javafx.util.Pair;

public class Create extends Element {
    private double minTime, maxTime;
    private Pair<Double, Double> nextClient;
    private Line1 line1;

    public Create(double delay, double minTime, double maxTime) {
        super(delay);
        this.minTime = minTime;
        this.maxTime = maxTime;
        setName("Create");
        setTnext(0.0);
        nextClient = new Pair<>(0.0, 0.0);
    }

    public void setLine1(Line1 line1) {
        this.line1 = line1;
    }

    @Override
    public void outAct() {
        super.outAct();
        line1.inAct(nextClient.getKey(), 0);
        nextClient = getNextClient();
        setTnext(nextClient.getKey());
    }

    public Pair<Double, Double> getNextClient() {
        double time = super.getDelay();
        if (time < minTime) {
            time = minTime;
        } else if (time > maxTime) {
            time = maxTime;
        }
        return new Pair<>(getTcurr() + time, 0.0);
    }
}