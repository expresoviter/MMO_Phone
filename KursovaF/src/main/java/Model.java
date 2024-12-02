import java.util.ArrayList;

public class Model
{
    private ArrayList<Element> list;
    double tnext, tcurr;
    int event;

    public Model(ArrayList<Element> elements)
    {
        list = elements;
        tnext = 0.0;
        event = 0;
        tcurr = tnext;
    }

    public void simulate(double time)
    {
        while (tcurr < time)
        {
            tnext = Double.MAX_VALUE;
            for (Element e : list)
            {
                if (e.getTnext() < tnext)
                {
                    tnext = e.getTnext();
                    event = e.getId();
                }
            }
            for (Element e : list)
            {
                e.doStatistics(tnext - tcurr);
            }
            tcurr = tnext;
            for (Element e : list)
            {
                e.setTcurr(tcurr);
            }
            list.get(event).outAct();
            for (Element e : list)
            {
                if (e.getTnext() == tcurr)
                {
                    e.outAct();
                }
            }
        }
        printResult();
    }

    public void printInfo()
    {
        for (Element e : list)
        {
            e.printInfo();
        }
    }

    public void printResult()
    {
        System.out.println("\nРезультат моделювання:");
        for (Element e : list)
        {
            e.printResult();
            if (e instanceof Wait)
            {
                Wait w = (Wait) e;
                System.out.println("    Середнє завантаження Wait: " + w.getMeanState() /
                        tcurr);
            }

            if (e instanceof Line1)
            {
                Line1 line1 = (Line1) e;
                System.out.println("    Середнє завантаження лінії Л1:  " + line1.getMeanState() /
                        tcurr);
                System.out.println("    Середня тривалість телефонної розмови: " + line1.getMeanTime() /
                        line1.getTimesGenerated());
                System.out.println("    Частота невдалих спроб здійснення зв’язку: " + line1.getFailures()
                        / (0.0 + line1.getFailures() + line1.getQuantity()));
            }

            if (e instanceof Line2)
            {
                Line2 line2 = (Line2) e;
                System.out.println("    Середнє завантаження лінії Л2: " + line2.getMeanState() /
                        tcurr);
                System.out.println("   Середня тривалість набору номеру: " +
                        line2.getMeanTime() / line2.getTimesGenerated());
            }

            if (e instanceof Despose)
            {
                Despose d = (Despose) e;
                System.out.println("    Середній час здійснення зв’язку: " + d.getMeanTime()
                        / d.getQuantity());

            }
        }
    }
}