package entities;

import java.util.LinkedList;
import java.util.List;

public class Category {
    private long id;
    private String name;
    private double estimatedTime;

    private Project project;
    private List<TimeEntry> timeEntries;

    public Category() {
        this.name = new String();
        this.estimatedTime = 0.0;
        this.project = new Project();
        this.timeEntries = new LinkedList<>();
    }

    public Category(String name, double estimatedTime, Project project) {
        this.name = name;
        this.estimatedTime = estimatedTime;
        this.project = project;
        this.timeEntries = new LinkedList<>();
    }

    @Override
    public String toString() {
        //return "Category{" + "id=" + id + ", name=" + name + ", estimatedTime=" + estimatedTime + ", project=" + project.getId() + '}';
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<TimeEntry> getTimeEntries() {
        return timeEntries;
    }

    public void setTimeEntries(List<TimeEntry> timeEntries) {
        this.timeEntries = timeEntries;
    }


}
