package entities;

public class Cooperation {
    private long id;
    private ProjectRole projectRole;
    
    private Person person;
    private Project project;

    public Cooperation() {
        this.projectRole = ProjectRole.COWORKER;
        this.person = new Person();
        this.project = new Project();
    }

    public Cooperation(ProjectRole projectRole, Person person, Project project) {
        this.projectRole = projectRole;
        this.person = person;
        this.project = project;
    }

    @Override
    public String toString() {
        return "Cooperation{" + "id=" + id + ", projectRole=" + projectRole + ", person=" + person + ", project=" + project + '}';
    }
    
    public void setId(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public ProjectRole getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(ProjectRole projectRole) {
        this.projectRole = projectRole;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    
}
