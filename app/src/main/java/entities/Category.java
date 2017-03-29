package entities;

import java.util.LinkedList;
import java.util.List;

public class Category {
    private long id;
    private String name;
    private int estimatedTime;
    private Project project;
    private List<TimeEntry> timeEntries;

    public Category() {
    }

    public Category(long id, String name, int estimatedTime, Project project, List<TimeEntry> timeEntries) {
        this.id = id;
        this.name = name;
        this.estimatedTime = estimatedTime;
        this.project = project;
        this.timeEntries = timeEntries;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != category.id) return false;
        if (estimatedTime != category.estimatedTime) return false;
        if (name != null ? !name.equals(category.name) : category.name != null) return false;
        if (project != null ? !project.equals(category.project) : category.project != null)
            return false;
        return timeEntries != null ? timeEntries.equals(category.timeEntries) : category.timeEntries == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + estimatedTime;
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (timeEntries != null ? timeEntries.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", estimatedTime=" + estimatedTime +
                ", project=" + project +
                ", timeEntries=" + timeEntries +
                '}';
    }
}
