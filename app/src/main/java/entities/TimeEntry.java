package entities;

import java.util.Date;

public class TimeEntry {
    private long id;
    private Date from;
    private Date to;
    private String note;
    private Measurement measurement;

    private Person person;
    private Category category;

    public TimeEntry() {
        this.from = new Date();
        this.to = new Date();
        this.note = new String();
        this.measurement = Measurement.MANUALLY;
        this.person = new Person();
        this.category = new Category();
    }

    public TimeEntry(Date from, Date to, String note, Measurement measurement, Person person, Category category) {
        this.from = from;
        this.to = to;
        this.note = note;
        this.measurement = measurement;
        this.person = person;
        this.category = category;
    }

    @Override
    public String toString() {
        return "TimeEntry{" + "id=" + id + ", from=" + from + ", to=" + to + ", note=" + note + ", measurement=" + measurement + ", person=" + person + ", category=" + category + '}';
    }

    public long getId() {
        return id;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


}
