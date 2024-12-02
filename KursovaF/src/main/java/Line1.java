import javafx.util.Pair;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Line1 extends Element {
    private int maxWorkers;
    private Despose d;
    private Line2 line2;
    private Wait w;
    private final PriorityQueue<Pair<Double, Double>> nextClients;
    private double minTime, maxTime;
    private double meanState = 0.0, meanTime = 0.0;
    private int failures = 0;
    private double timesGenerated = 0;

    public Line1(double delay, int maxWorkers, double minTime, double maxTime) {
        super(delay);
        super.setTnext(Double.MAX_VALUE);
        setName("Line1");
        nextClients = new PriorityQueue<>(Comparator.comparingDouble(Pair::getKey));
        this.maxWorkers = maxWorkers;
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    public void setLine2(Line2 line2) {
        this.line2 = line2;
    }

    public void setDespose(Despose d) {
        this.d = d;
    }

    public void setWait(Wait w) {
        this.w = w;
    }

    public void inAct(double startTime, int clientFailures) {
        for (Pair<Double, Double> pair : nextClients) {
            if (pair.getValue() == startTime) {
                nextClients.removeIf((p) -> p.getValue().equals(startTime));
                double time = getDelay();
                meanTime += time;
                timesGenerated++;
                nextClients.add(new Pair<>(getTcurr() + time, startTime));
                setTnext(nextClients.peek().getKey());
                return;
            }
        }

        if (getState() < maxWorkers) {
            setState(getState() + 1);
            this.nextClients.add(new Pair<>(Double.MAX_VALUE, startTime));
            setTnext(nextClients.peek().getKey());
            line2.inAct(startTime, clientFailures);
        } else {
            failures++;
            clientFailures++;
            w.inAct(startTime);
        }
    }

    public void outAct() {
        super.outAct();
        setState(getState() - 1);
        double startTime = nextClients.poll().getValue();
        setTnext(Double.MAX_VALUE);
        if (nextClients.peek() != null) {
            setTnext(nextClients.peek().getKey());
        }
        d.inAct(startTime, failures);
    }

    public double getDelay() {
        double time = super.getDelay();
        if (time > maxTime) {
            time = maxTime;
        }

        if (time < minTime) {
            time = minTime;
        }

        return time;
    }

    public double getMeanTime() {
        return meanTime;
    }

    public double getTimesGenerated() {
        return timesGenerated;
    }

    public double getMeanState() {
        return meanState;
    }

    public int getFailures() {
        return failures;
    }

    @Override
    public void doStatistics(double delta) {
        meanState = meanState + getState() * delta;
    }
}