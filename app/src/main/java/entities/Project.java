package entities;

import java.util.LinkedList;
import java.util.List;

public class Project {
    private long id;
    private String name;
    private String description;
    private List<Category> categories;
    private List<Cooperation> cooperations;

    public Project() {
    }

    public Project(long id, String name, String description, List<Category> categories, List<Cooperation> cooperations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.cooperations = cooperations;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Cooperation> getCooperations() {
        return cooperations;
    }

    public void setCooperations(List<Cooperation> cooperations) {
        this.cooperations = cooperations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (id != project.id) return false;
        if (name != null ? !name.equals(project.name) : project.name != null) return false;
        if (description != null ? !description.equals(project.description) : project.description != null)
            return false;
        if (categories != null ? !categories.equals(project.categories) : project.categories != null)
            return false;
        return cooperations != null ? cooperations.equals(project.cooperations) : project.cooperations == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        result = 31 * result + (cooperations != null ? cooperations.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categories=" + categories +
                ", cooperations=" + cooperations +
                '}';
    }
}
