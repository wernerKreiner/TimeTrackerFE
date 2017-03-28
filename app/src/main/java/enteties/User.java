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
}
