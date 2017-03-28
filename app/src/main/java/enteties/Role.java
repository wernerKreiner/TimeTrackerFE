package enteties;

import java.util.LinkedList;
import java.util.List;

public class Role {
    protected int id;
    protected String name;
    protected List<Cooperation> Cooperations = new LinkedList<Cooperation>();
}
