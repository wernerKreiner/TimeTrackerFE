package services;

import entities.TimeEntry;

import java.util.List;

public class TimeEntryService extends Service<TimeEntry> {

    @Override
    public List<TimeEntry> get() {
        return this.data.getTimeEntries();
    }

    @Override
    public TimeEntry getById(long id) {
        return this.data.getTimeEntry(id);
    }

    @Override
    public TimeEntry create(TimeEntry object) {
        return this.data.createTimeEntry(object);
    }

    @Override
    public boolean edit(TimeEntry object) {
        return this.data.editTimeEntry(object);
    }

    @Override
    public boolean remove(long id) {
        return this.data.removeTimeEntry(id);
    }

}
