package enteties;

import java.util.Date;

public class TimeEntry {

    protected int id;
    protected String name;
    protected Date from;
    protected Date until;
    protected String note;
    protected Measurment measurmentId;
    protected User userId;
    protected Category categoryId;

    public TimeEntry(int id, String name, Date from, Date until, String note, Measurment measurmentId, User userId, Category categoryId) {
        this.id = id;
        this.name = name;
        this.from = from;
        this.until = until;
        this.note = note;
        this.measurmentId = measurmentId;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Date getFrom() {
        return from;
    }

    public Date getUntil() {
        return until;
    }

    public String getNote() {
        return note;
    }

    public Measurment getMeasurmentId() {
        return measurmentId;
    }

    public User getUserId() {
        return userId;
    }

    public Category getCategoryId() {
        return categoryId;
    }
}
