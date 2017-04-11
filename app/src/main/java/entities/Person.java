package entities;

import java.util.LinkedList;
import java.util.List;

public class Person {
    private long id;
    private String firstname;
    private String lastname;
    private String nickname;
    private String email;
    private String password;
    private boolean quickstart;

    private List<Cooperation> cooperations;
    private List<TimeEntry> timeEntries;

    public Person() {
        this.firstname = new String();
        this.lastname = new String();
        this.nickname = new String();
        this.email = new String();
        this.password = new String();
        this.quickstart = false;
        this.cooperations = new LinkedList<>();
        this.timeEntries = new LinkedList<>();
    }

    public Person(String firstname, String lastname, String nickname, String email, String password, boolean quickstart) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.quickstart = quickstart;
        this.cooperations = new LinkedList<>();
        this.timeEntries = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", nickname=" + nickname + ", email=" + email + ", password=" + password + ", quickstart=" + quickstart + ", cooperations=" + cooperations + '}';
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public boolean getQuickstart() {
        return quickstart;
    }

    public void setQuickstart(boolean quickstart) {
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
}
