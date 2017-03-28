package enteties;

import java.util.LinkedList;
import java.util.List;

public class Category {
    protected int id;
    protected String name;
    protected Number estimatedTime;
    protected Project projectId;
    protected List<TimeEntry> TimeEntries = new LinkedList<TimeEntry>();

    public Category(int id, String name, Number estimatedTime, Project projectId, List<TimeEntry> timeEntries) {
        this.id = id;
        this.name = name;
        this.estimatedTime = estimatedTime;
        this.projectId = projectId;
        TimeEntries = timeEntries;
    }

    public Category() {
    }
}
