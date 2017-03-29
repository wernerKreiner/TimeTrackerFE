package entities;

public class Cooperation {
    private long id;
    private User user;
    private Project project;
    private Role role;

    public Cooperation() {
    }

    public Cooperation(long id, User user, Project project, Role role) {
        this.id = id;
        this.user = user;
        this.project = project;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cooperation that = (Cooperation) o;

        if (id != that.id) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;
        return role != null ? role.equals(that.role) : that.role == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cooperation{" +
                "id=" + id +
                ", user=" + user +
                ", project=" + project +
                ", role=" + role +
                '}';
    }
}
