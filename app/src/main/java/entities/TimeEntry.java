package entities;

import java.util.Date;

public class TimeEntry {

    private long id;
    private String name;
    private Date from;
    private Date to;
    private String note;
    private Measurement measurement;
    private User user;
    private Category category;

    public TimeEntry() {
    }

    public TimeEntry(long id, String name, Date from, Date to, String note, Measurement measurement, User user, Category category) {
        this.id = id;
        this.name = name;
        this.from = from;
        this.to = to;
        this.note = note;
        this.measurement = measurement;
        this.user = user;
        this.category = category;
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

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeEntry timeEntry = (TimeEntry) o;

        if (id != timeEntry.id) return false;
        if (name != null ? !name.equals(timeEntry.name) : timeEntry.name != null) return false;
        if (from != null ? !from.equals(timeEntry.from) : timeEntry.from != null) return false;
        if (to != null ? !to.equals(timeEntry.to) : timeEntry.to != null) return false;
        if (note != null ? !note.equals(timeEntry.note) : timeEntry.note != null) return false;
        if (measurement != timeEntry.measurement) return false;
        if (user != null ? !user.equals(timeEntry.user) : timeEntry.user != null) return false;
        return category != null ? category.equals(timeEntry.category) : timeEntry.category == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (measurement != null ? measurement.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TimeEntry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", note='" + note + '\'' +
                ", measurement=" + measurement +
                ", user=" + user +
                ", category=" + category +
                '}';
    }
}
