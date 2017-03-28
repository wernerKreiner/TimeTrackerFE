package enteties;

import java.util.LinkedList;
import java.util.List;

public class Category {
    protected int id;
    protected String name;
    protected Number estimatedTime;
    protected Project projectId;
    protected List<TimeEntry> TimeEntries = new LinkedList<TimeEntry>();
}
