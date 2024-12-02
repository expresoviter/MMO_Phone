import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Line2 extends Element {
    private Line1 line1;
    private double minTime, maxTime;
    private ArrayList<Double> queue = new ArrayList<>();
    private final PriorityQueue<Pair<Double, Double>> nextClients;
    private int maxWorkers;
    private double meanState = 0.0, meanTime = 0.0;
    private double timesGenerated = 0;

    public Line2(double delay, int maxWorkers, double minTime, double maxTime) {
        super(delay);
        super.setTnext(Double.MAX_VALUE);
        setName("Line2");
        nextClients = new PriorityQueue<>(Comparator.comparingDouble(Pair::getKey));
        this.maxWorkers = maxWorkers;
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    public void setLine1(Line1 line1) {
        this.line1 = line1;
    }

    public void inAct(double startTime, int clientFailures) {
        if (getState() < maxWorkers) {
            double time = getDelay();
            meanTime += time;
            timesGenerated++;
            this.nextClients.add(new Pair<>(getTcurr() + time, startTime));
            setState(getState() + 1);
            setTnext(nextClients.peek().getKey());
        } else {
            queue.add(startTime);
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
        line1.inAct(startTime, 0);
        if (!queue.isEmpty()) {
            startTime = queue.remove(0);
            double time = getDelay();
            setState(getState() + 1);
            meanTime += time;
            timesGenerated++;
            this.nextClients.add(new Pair<>(getTcurr() + time, startTime));
            setTnext(nextClients.peek().getKey());
        }
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

    @Override
    public void doStatistics(double delta) {
        meanState = meanState + getState() * delta;
    }

    @Override
    public void printInfo() {
        System.out.println(getName() + " state= " + getState() +
                " queue = " + queue.size() +
                " quantity = " + getQuantity() +
                " tnext= " + getTnext());
    }
}