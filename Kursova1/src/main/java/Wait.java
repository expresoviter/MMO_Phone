import javafx.util.Pair;


import java.util.Comparator;
import java.util.PriorityQueue;


public class Wait extends Element {
    private double minTime, maxTime;
    private double meanState;
    private final PriorityQueue<Pair<Double, Client>> nextClients;
    private Line1 line1;

    public Wait(double delay, double delayDev, double minTime, double maxTime) {
        super(delay);
        setTnext(Double.MAX_VALUE);
        setName("Wait");
        nextClients = new
                PriorityQueue<>(Comparator.comparingDouble(Pair::getKey));
        this.minTime = minTime;
        this.maxTime = maxTime;
        setDelayDev(delayDev);
    }

    public void setLine1(Line1 line1) {
        this.line1 = line1;
    }

    public void inAct(Client client) {
        setTnext(getTcurr() + getDelay());
        this.nextClients.add(new Pair<>(getTcurr() + getDelay(), client));
        setState(getState() + 1);
        super.setTnext(nextClients.peek().getKey());
    }

    public void outAct() {
        super.outAct();
        setState(getState() - 1);
        Client client = nextClients.poll().getValue();
        line1.inAct(client);

        setTnext(Double.MAX_VALUE);
        if (nextClients.peek() != null) {
            setTnext(nextClients.peek().getKey());
        }
    }

    public double getDelay() {
        double t = super.getDelay();
        if (t < minTime) {
            t = minTime;
        }

        if (t > maxTime) {
            t = maxTime;
        }
        return t;
    }


    public double getMeanState() {

        return meanState;
    }

    @Override
    public void doStatistics(double delta) {
        meanState = meanState + getState() * delta;
    }
} 