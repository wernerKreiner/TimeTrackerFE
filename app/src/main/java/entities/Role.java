package entities;

import java.util.LinkedList;
import java.util.List;

public class Role {
    private long id;
    private String name;
    private List<Cooperation> cooperations;

    public Role() {
    }

    public Role(long id, String name, List<Cooperation> cooperations) {
        this.id = id;
        this.name = name;
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

        Role role = (Role) o;

        if (id != role.id) return false;
        if (name != null ? !name.equals(role.name) : role.name != null) return false;
        return cooperations != null ? cooperations.equals(role.cooperations) : role.cooperations == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (cooperations != null ? cooperations.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cooperations=" + cooperations +
                '}';
    }
}
