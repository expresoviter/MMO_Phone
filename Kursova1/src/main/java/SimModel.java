import java.util.ArrayList;


public class SimModel {
    public static void main(String[] args) {
        Create create = new Create(1.0, 0.0, 60.0);
        create.setDistribution("exp");

        Line1 line1 = new Line1(10.0, 11, 3.0, 30.0);
        line1.setDistribution("exp");

        Line2 line2 = new Line2(0.2, 1, 0.1, 0.5);
        line2.setDistribution("exp");

        Wait w = new Wait(10.0, 2.0, 0.0, 60.0);
        w.setDistribution("norm");

        Despose d = new Despose();

        create.setLine1(line1);

        line1.setLine2(line2);
        line1.setWait(w);
        line1.setDespose(d);

        line2.setLine1(line1);

        w.setLine1(line1);

        ArrayList<Element> list = new ArrayList<>();
        list.add(create);
        list.add(line1);
        list.add(line2);
        list.add(w);
        list.add(d);
        Model model = new Model(list);
        model.simulate(4000.0);
    }
}