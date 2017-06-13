package entities;

public class Category {
    private long id;
    private String name;
    private double estimatedTime;
    
    private Project project;

    public Category() {
        this.name = new String();
        this.estimatedTime = 0.0;
        this.project = new Project();
    }

    public Category(String name, double estimatedTime, Project project) {
        this.name = name;
        this.estimatedTime = estimatedTime;
        this.project = project;
    }

    @Override
    public String toString() {
        return name;
    }
    
    public void setId(long id){
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
}
