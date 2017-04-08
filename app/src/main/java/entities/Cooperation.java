package entities;

public class Cooperation {
    private long id;
    private ProjectRole role;

    private Person person;
    private Project project;

    public Cooperation() {
        this.role = ProjectRole.COWORKER;
        this.person = new Person();
        this.project = new Project();
    }

    public Cooperation(ProjectRole role, Person person, Project project) {
        this.role = role;
        this.person = person;
        this.project = project;
    }

    @Override
    public String toString() {
        return "Cooperation{" + "id=" + id + ", role=" + role + ", person=" + person + ", project=" + project + '}';
    }

    public long getId() {
        return id;
    }

    public ProjectRole getRole() {
        return role;
    }

    public void setRole(ProjectRole role) {
        this.role = role;
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
