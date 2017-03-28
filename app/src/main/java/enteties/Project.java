package enteties;

import java.util.LinkedList;
import java.util.List;

public class Project {
    protected int id;
    protected String name;
    protected String description;
    protected List<Category> Categories = new LinkedList<Category>();
    protected List<Cooperation> Cooperations = new LinkedList<Cooperation>();
}
