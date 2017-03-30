package entities;

import java.util.LinkedList;
import java.util.List;

public class User {
    private long id;
    private String firstname;
    private String surname;
    private String nickname;
    private String email;
    private String password;
    private Integer quickstart;
    private List<Cooperation> cooperations;
    private List<TimeEntry> timeEntries;

    public User() {

    }

    public User(String nickname) {
        this.nickname = nickname;
    }

    public User(long id, String firstname, String surname, String nickname, String email, String password, Integer quickstart, List<Cooperation> cooperations, List<TimeEntry> timeEntries) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.quickstart = quickstart;
        this.cooperations = cooperations;
        this.timeEntries = timeEntries;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getQuickstart() {
        return quickstart;
    }

    public void setQuickstart(Integer quickstart) {
        this.quickstart = quickstart;
    }

    public List<Cooperation> getCooperations() {
        return cooperations;
    }

    public void setCooperations(List<Cooperation> cooperations) {
        this.cooperations = cooperations;
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

        User user = (User) o;

        if (id != user.id) return false;
        if (firstname != null ? !firstname.equals(user.firstname) : user.firstname != null)
            return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (nickname != null ? !nickname.equals(user.nickname) : user.nickname != null)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null)
            return false;
        if (quickstart != null ? !quickstart.equals(user.quickstart) : user.quickstart != null)
            return false;
        if (cooperations != null ? !cooperations.equals(user.cooperations) : user.cooperations != null)
            return false;
        return timeEntries != null ? timeEntries.equals(user.timeEntries) : user.timeEntries == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (quickstart != null ? quickstart.hashCode() : 0);
        result = 31 * result + (cooperations != null ? cooperations.hashCode() : 0);
        result = 31 * result + (timeEntries != null ? timeEntries.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", quickstart=" + quickstart +
                ", cooperations=" + cooperations +
                ", timeEntries=" + timeEntries +
                '}';
    }
}
