package enteties;

import java.util.LinkedList;
import java.util.List;

public class User {
    protected int id;
    protected String firstName;
    protected String lasName;
    protected String nickName;
    protected String email;
    protected String password;
    protected int quickstart;
    protected List<Cooperation> Cooperations = new LinkedList<Cooperation>();
    protected List<TimeEntry> TimeEntries = new LinkedList<TimeEntry>();

    public User(int id, String firstName, String lasName, String nickName, String email, String password, int quickstart, List<Cooperation> cooperations, List<TimeEntry> timeEntries) {
        this.id = id;
        this.firstName = firstName;
        this.lasName = lasName;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.quickstart = quickstart;
        Cooperations = cooperations;
        TimeEntries = timeEntries;
    }

    public User(){

    }

    public User(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLasName() {
        return lasName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getQuickstart() {
        return quickstart;
    }

    public List<Cooperation> getCooperations() {
        return Cooperations;
    }

    public List<TimeEntry> getTimeEntries() {
        return TimeEntries;
    }
}
