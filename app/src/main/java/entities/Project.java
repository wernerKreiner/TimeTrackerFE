package entities;

import java.util.LinkedList;
import java.util.List;

public class Project {
    private long id;
    private String name;
    private String description;

    private List<Cooperation> cooperations;
    private List<Category> categories;

    public Project() {
        this.name = new String();
        this.description = new String();
        this.cooperations = new LinkedList<>();
        this.categories = new LinkedList<>();
    }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        this.cooperations = new LinkedList<>();
        this.categories = new LinkedList<>();
    }

    @Override
    public String toString() {
       // return "Project{" + "id=" + id + ", name=" + name + ", description=" + description + ", categories=" + categories + ", cooperations=" + cooperations + '}';
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Cooperation> getCooperations() {
        return cooperations;
    }

    public void setCooperations(List<Cooperation> cooperations) {
        this.cooperations = cooperations;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
