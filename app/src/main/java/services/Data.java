package services;

import entities.Category;
import entities.Cooperation;
import entities.Measurement;
import entities.Person;
import entities.Project;
import entities.ProjectRole;
import entities.TimeEntry;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class Data {

    private final List<Person> persons = new LinkedList<>();
    private final List<Project> projects = new LinkedList<>();
    private final List<Category> categories = new LinkedList<>();
    private final List<Cooperation> cooperations = new LinkedList<>();
    private final List<TimeEntry> timeEntries = new LinkedList<>();

    public Data() {
        generateData();
    }

    private void generateData() {
        Person person1 = createPerson(new Person("Sebastian", "Gruber", "basti", "basti@jku.at", "12345", true));
        Person person2 = createPerson(new Person("Werner", "Kreiner", "werni", "werni@jku.at", "12345", false));
        Person person3 = createPerson(new Person("Anna", "Angelopoulou", "anni", "anni@jku.at", "12345", false));
        Person person4 = createPerson(new Person("Dominik", "Lamprecht", "domi", "domi@jku.at", "12345", false));
        Person person5 = createPerson(new Person("Antonia", "Grillmair", "antoni", "antoni@jku.at", "12345", true));
        Person person6 = createPerson(new Person("Test", "Muster", "test", "test@jku.at", "12345", true));

        Project project1 = createProject(new Project("PR SE Prototyp", "Erstellen eines Prototypen"));
        Project project2 = createProject(new Project("UE IM", "Ausarbeiten von Aufgabenstellungen"));
        Project project3 = createProject(new Project("PR SE Release 1", "Erstellen des Release 1 von Timetracker"));

        Category category1 = createCategory(new Category("Planung", 30.0, project1));
        Category category2 = createCategory(new Category("Implementierung", 50.0, project1));
        Category category3 = createCategory(new Category("Testen", 10.0, project1));
        Category category4 = createCategory(new Category("Planung", 3.0, project2));
        Category category5 = createCategory(new Category("Erarbeiten", 10.0, project2));
        Category category6 = createCategory(new Category("Planung", 50.0, project3));
        Category category7 = createCategory(new Category("Implementierung", 200.0, project3));
        Category category8 = createCategory(new Category("Testen und Zusammenführen der Komponenten", 65.0, project3));

        Cooperation cooperation1 = createCooperation(new Cooperation(ProjectRole.COWORKER, person1, project1));
        Cooperation cooperation2 = createCooperation(new Cooperation(ProjectRole.COWORKER, person1, project2));
        Cooperation cooperation3 = createCooperation(new Cooperation(ProjectRole.ADMIN, person1, project3));
        Cooperation cooperation4 = createCooperation(new Cooperation(ProjectRole.ADMIN, person2, project1));
        Cooperation cooperation5 = createCooperation(new Cooperation(ProjectRole.ADMIN, person2, project2));
        Cooperation cooperation6 = createCooperation(new Cooperation(ProjectRole.COWORKER, person2, project3));
        Cooperation cooperation7 = createCooperation(new Cooperation(ProjectRole.ADMIN, person3, project1));
        Cooperation cooperation8 = createCooperation(new Cooperation(ProjectRole.COWORKER, person3, project2));
        Cooperation cooperation9 = createCooperation(new Cooperation(ProjectRole.ADMIN, person3, project3));
        Cooperation cooperation10 = createCooperation(new Cooperation(ProjectRole.ADMIN, person4, project1));
        Cooperation cooperation11 = createCooperation(new Cooperation(ProjectRole.COWORKER, person4, project2));
        Cooperation cooperation12 = createCooperation(new Cooperation(ProjectRole.ADMIN, person4, project3));
        Cooperation cooperation13 = createCooperation(new Cooperation(ProjectRole.ADMIN, person5, project1));
        Cooperation cooperation14 = createCooperation(new Cooperation(ProjectRole.COWORKER, person5, project3));
        Cooperation cooperation15 = createCooperation(new Cooperation(ProjectRole.COWORKER, person6, project1));
        Cooperation cooperation16 = createCooperation(new Cooperation(ProjectRole.ADMIN, person6, project2));
        Cooperation cooperation17 = createCooperation(new Cooperation(ProjectRole.ADMIN, person6, project3));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            TimeEntry timeEntry1 = createTimeEntry(new TimeEntry(dateFormat.parse("09.04.2017 08:00:00"), dateFormat.parse("09.04.2017 12:00:00"), "Besprechung", Measurement.MANUALLY, person1, category6));
            TimeEntry timeEntry2 = createTimeEntry(new TimeEntry(dateFormat.parse("09.04.2017 08:00:00"), dateFormat.parse("09.04.2017 12:00:00"), "Besprechung", Measurement.MANUALLY, person2, category6));
            TimeEntry timeEntry3 = createTimeEntry(new TimeEntry(dateFormat.parse("09.04.2017 08:00:00"), dateFormat.parse("09.04.2017 12:00:00"), "Besprechung", Measurement.MANUALLY, person3, category6));
            TimeEntry timeEntry4 = createTimeEntry(new TimeEntry(dateFormat.parse("09.04.2017 08:00:00"), dateFormat.parse("09.04.2017 12:00:00"), "Besprechung", Measurement.MANUALLY, person4, category6));
            TimeEntry timeEntry5 = createTimeEntry(new TimeEntry(dateFormat.parse("09.04.2017 08:00:00"), dateFormat.parse("09.04.2017 12:00:00"), "Besprechung", Measurement.MANUALLY, person5, category6));
            TimeEntry timeEntry6 = createTimeEntry(new TimeEntry(dateFormat.parse("09.04.2017 08:00:00"), dateFormat.parse("09.04.2017 12:00:00"), "Besprechung", Measurement.MANUALLY, person6, category6));

            TimeEntry timeEntry7 = createTimeEntry(new TimeEntry(dateFormat.parse("09.04.2017 13:00:00"), dateFormat.parse("09.04.2017 14:30:00"), "Service", Measurement.MANUALLY, person6, category7));
            TimeEntry timeEntry8 = createTimeEntry(new TimeEntry(dateFormat.parse("09.04.2017 15:00:00"), dateFormat.parse("09.04.2017 18:00:00"), "", Measurement.MANUALLY, person6, null));

            TimeEntry timeEntry9 = createTimeEntry(new TimeEntry(dateFormat.parse("09.03.2017 08:00:00"), dateFormat.parse("09.03.2017 12:00:00"), "Pair Programming", Measurement.AUTOMATICALLY, person1, category2));
            TimeEntry timeEntry10 = createTimeEntry(new TimeEntry(dateFormat.parse("09.03.2017 08:00:00"), dateFormat.parse("09.03.2017 12:00:00"), "Pair Programming", Measurement.AUTOMATICALLY, person2, category2));

            TimeEntry timeEntry11 = createTimeEntry(new TimeEntry(dateFormat.parse("09.03.2017 08:00:00"), dateFormat.parse("09.03.2017 12:00:00"), "Pair Programming", Measurement.AUTOMATICALLY, person3, category2));
            TimeEntry timeEntry12 = createTimeEntry(new TimeEntry(dateFormat.parse("09.03.2017 08:00:00"), dateFormat.parse("09.03.2017 12:00:00"), "Pair Programming", Measurement.AUTOMATICALLY, person4, category2));

            TimeEntry timeEntry13 = createTimeEntry(new TimeEntry(dateFormat.parse("09.03.2017 08:00:00"), dateFormat.parse("09.03.2017 12:00:00"), "Pair Programming", Measurement.AUTOMATICALLY, person5, category2));
            TimeEntry timeEntry14 = createTimeEntry(new TimeEntry(dateFormat.parse("09.03.2017 08:00:00"), dateFormat.parse("09.03.2017 12:00:00"), "Pair Programming", Measurement.AUTOMATICALLY, person6, category2));

            TimeEntry timeEntry15 = createTimeEntry(new TimeEntry(dateFormat.parse("11.03.2017 08:00:00"), dateFormat.parse("11.03.2017 12:00:00"), "Chart", Measurement.AUTOMATICALLY, person4, category1));
            TimeEntry timeEntry16 = createTimeEntry(new TimeEntry(dateFormat.parse("10.03.2017 08:00:00"), dateFormat.parse("10.03.2017 12:00:00"), "Chart", Measurement.AUTOMATICALLY, person4, category3));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Person> getPersons() {
        return this.persons;
    }

    public Person getPerson(long id) {
        for (Person p : this.persons) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public Person createPerson(Person object) {
        long id;
        if (this.getPersons().size() < 1) {
            id = 1;
        } else {
            id = 0;
        }
        for (Person p : this.getPersons()) {
            if (p.getId() >= id) {
                id = p.getId() + 1;
            }
        }
        object.setId(id);
        this.persons.add(object);
        return object;
    }

    public boolean removePerson(long id) {
        for (int i = 0; i < this.persons.size(); i++) {
            if (this.persons.get(i).getId() == id) {
                this.persons.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean editPerson(Person object) {
        for (int i = 0; i < this.persons.size(); i++) {
            if (this.persons.get(i).getId() == object.getId()) {
                this.persons.set(i, object);
                return true;
            }
        }
        return false;
    }

    public List<Project> getProjects() {
        return this.projects;
    }

    public Project getProject(long id) {
        for (Project p : this.projects) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public Project createProject(Project object) {
        long id;
        if (this.getProjects().size() < 1) {
            id = 1;
        } else {
            id = 0;
        }
        for (Project p : this.projects) {
            if (p.getId() >= id) {
                id = p.getId() + 1;
            }
        }
        object.setId(id);
        this.projects.add(object);
        return object;
    }

    public boolean removeProject(long id) {
        for (int i = 0; i < this.projects.size(); i++) {
            if (this.projects.get(i).getId() == id) {
                this.projects.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean editProject(Project object) {
        for (int i = 0; i < this.projects.size(); i++) {
            if (this.projects.get(i).getId() == object.getId()) {
                this.projects.set(i, object);
                return true;
            }
        }
        return false;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public Category getCategory(long id) {
        for (Category c : this.categories) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public Category createCategory(Category object) {
        long id;
        if (this.getCategories().size() < 1) {
            id = 1;
        } else {
            id = 0;
        }
        for (Category c : this.categories) {
            if (c.getId() >= id) {
                id = c.getId() + 1;
            }
        }
        object.setId(id);
        object.getProject().getCategories().add(object);
        this.categories.add(object);
        return object;
    }

    public boolean removeCategory(long id) {
        for (int i = 0; i < this.categories.size(); i++) {
            if (this.categories.get(i).getId() == id) {
                this.categories.get(i).getProject().getCategories().remove(this.categories.get(i));
                this.categories.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean editCategory(Category object) {
        for (int i = 0; i < this.categories.size(); i++) {
            if (this.categories.get(i).getId() == object.getId()) {
                this.categories.set(i, object);
                return true;
            }
        }
        return false;
    }

    public List<Cooperation> getCooperations() {
        return this.cooperations;
    }

    public Cooperation getCooperation(long id) {
        for (Cooperation c : this.cooperations) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public Cooperation createCooperation(Cooperation object) {
        long id;
        if (this.cooperations.size() < 1) {
            id = 1;
        } else {
            id = 0;
        }
        for (Cooperation c : this.cooperations) {
            if (c.getId() >= id) {
                id = c.getId() + 1;
            }
        }
        object.setId(id);
        object.getProject().getCooperations().add(object);
        object.getPerson().getCooperations().add(object);
        this.cooperations.add(object);
        return object;
    }

    public boolean removeCooperation(long id) {
        for (int i = 0; i < this.cooperations.size(); i++) {
            if (this.cooperations.get(i).getId() == id) {
                this.cooperations.get(i).getProject().getCooperations().remove(this.cooperations.get(i));
                this.cooperations.get(i).getPerson().getCooperations().remove(this.cooperations.get(i));
                this.cooperations.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean editCooperation(Cooperation object) {
        for (int i = 0; i < this.cooperations.size(); i++) {
            if (this.cooperations.get(i).getId() == object.getId()) {
                this.cooperations.set(i, object);
                return true;
            }
        }
        return false;
    }

    public List<TimeEntry> getTimeEntries() {
        return this.timeEntries;
    }

    public TimeEntry getTimeEntry(long id) {
        for (TimeEntry t : this.timeEntries) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public TimeEntry createTimeEntry(TimeEntry object) {
        long id;
        if (this.timeEntries.size() < 1) {
            id = 1;
        } else {
            id = 0;
        }
        for (TimeEntry t : this.timeEntries) {
            if (t.getId() >= id) {
                id = t.getId() + 1;
            }
        }
        object.setId(id);
        if (object.getCategory() != null) object.getCategory().getTimeEntries().add(object);
        object.getPerson().getTimeEntries().add(object);
        this.timeEntries.add(object);
        return object;
    }

    public boolean removeTimeEntry(long id) {
        for (int i = 0; i < this.timeEntries.size(); i++) {
            if (this.timeEntries.get(i).getId() == id) {
                if (this.timeEntries.get(i).getCategory() != null)
                    this.timeEntries.get(i).getCategory().getTimeEntries().remove(this.timeEntries.get(i));
                this.timeEntries.get(i).getPerson().getTimeEntries().remove(this.timeEntries.get(i));
                this.timeEntries.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean editTimeEntry(TimeEntry object) {
        for (int i = 0; i < this.timeEntries.size(); i++) {
            if (this.timeEntries.get(i).getId() == object.getId()) {
                this.timeEntries.set(i, object);
                return true;
            }
        }
        return false;
    }
}
