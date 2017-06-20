package services;

import entities.Category;
import entities.Cooperation;
import entities.Measurement;
import entities.Person;
import entities.Project;
import entities.ProjectRole;
import entities.TimeEntry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XMLMapping {

    private final String  header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    public XMLMapping() {
    }

    public List<Person> xmlToPersons(String xml) {
        List<Person> persons = new LinkedList<>();
        if (xml.contains("<person>")) {
            String[] xmlProjects = xml.split("<person>");
            for (int i = 1; i < xmlProjects.length; i++) {
                persons.add(xmlToPerson("<person>" + xmlProjects[i]));
            }
        }
        return persons;
    }

    public Person xmlToPerson(String xml) {
        Person person = new Person();
        if (xml.contains("<person>")) {
            person.setId(Long.valueOf(xml.substring(xml.indexOf("<id>") + 4, xml.indexOf("</id>"))));
            person.setEmail(xml.substring(xml.indexOf("<email>") + 7, xml.indexOf("</email>")));
            person.setFirstname(xml.substring(xml.indexOf("<firstname>") + 11, xml.indexOf("</firstname>")));
            person.setLastname(xml.substring(xml.indexOf("<lastname>") + 10, xml.indexOf("</lastname>")));
            person.setNickname(xml.substring(xml.indexOf("<nickname>") + 10, xml.indexOf("</nickname>")));
            person.setPassword(xml.substring(xml.indexOf("<password>") + 10, xml.indexOf("</password>")));
            person.setQuickstart(Boolean.valueOf(xml.substring(xml.indexOf("<quickstart>") + 12, xml.indexOf("</quickstart>"))));
            return person;
        } else {
            return null;
        }
    }

    public String personToXml(Person person) {
        if (person == null) {
            return header;
        }
        return header
                + "<person>"
                + "<id>" + person.getId() + "</id>"
                + "<firstname>" + person.getFirstname() + "</firstname>"
                + "<lastname>" + person.getLastname() + "</lastname>"
                + "<nickname>" + person.getNickname() + "</nickname>"
                + "<email>" + person.getEmail() + "</email>"
                + "<password>" + person.getPassword() + "</password>"
                + "<quickstart>" + person.getQuickstart() + "</quickstart>"
                + "</person>";
    }

    public List<Project> xmlToProjects(String xml) {
        List<Project> projects = new LinkedList<>();
        if (xml.contains("<project>")) {
            String[] xmlProjects = xml.split("<project>");
            for (int i = 1; i < xmlProjects.length; i++) {
                projects.add(xmlToProject("<project>" + xmlProjects[i]));
            }
        }
        return projects;
    }

    public Project xmlToProject(String xml) {
        Project project = new Project();
        if (xml.contains("<project>")) {
            project.setId(Long.valueOf(xml.substring(xml.indexOf("<id>") + 4, xml.indexOf("</id>"))));
            project.setName(xml.substring(xml.indexOf("<name>") + 6, xml.indexOf("</name>")));
            project.setDescription(xml.substring(xml.indexOf("<description>") + 13, xml.indexOf("</description>")));
            return project;
        } else {
            return null;
        }
    }

    public String projectToXml(Project project) {
        if (project == null) {
            return header;
        }
        return header
                + "<project>"
                + "<id>" + project.getId() + "</id>"
                + "<name>" + project.getName() + "</name>"
                + "<description>" + project.getDescription() + "</description>"
                + "</project>";
    }

    public List<Category> xmlToCategories(String xml) {
        List<Category> categories = new LinkedList<>();
        if (xml.contains("<category>")) {
            String[] xmlProjects = xml.split("<category>");
            for (int i = 1; i < xmlProjects.length; i++) {
                categories.add(xmlToCategory("<category>" + xmlProjects[i]));
            }
        }
        return categories;
    }

    public Category xmlToCategory(String xml) {
        Category category = new Category();
        if (xml.contains("<category>")) {
            category.setId(Long.valueOf(xml.substring(xml.indexOf("<id>") + 4, xml.indexOf("</id>"))));
            category.setName(xml.substring(xml.indexOf("<name>") + 6, xml.indexOf("</name>")));
            category.setEstimatedTime(Double.valueOf(xml.substring(xml.indexOf("<estimatedTime>") + 15, xml.indexOf("</estimatedTime>"))));
            category.setProject(xmlToProject(xml.substring(xml.indexOf("<project>"), xml.indexOf("</project>"))));
            return category;
        } else {
            return null;
        }
    }

    public String categoryToXml(Category category) {
        if (category == null) {
            return header;
        }
        return header
                + "<category>"
                + "<id>" + category.getId() + "</id>"
                + "<name>" + category.getName() + "</name>"
                + "<estimatedTime>" + category.getEstimatedTime() + "</estimatedTime>"
                + projectToXml(category.getProject())
                        .replace(header, "")
                + "</category>";
    }

    public List<Cooperation> xmlToCooperations(String xml) {
        List<Cooperation> cooperations = new LinkedList<>();
        if (xml.contains("<cooperation>")) {
            String[] xmlCooperations = xml.split("<cooperation>");
            for (int i = 1; i < xmlCooperations.length; i++) {
                cooperations.add(xmlToCooperation("<cooperation>" + xmlCooperations[i]));
            }
        }
        return cooperations;
    }

    public Cooperation xmlToCooperation(String xml) {
        Cooperation cooperation = new Cooperation();
        if (xml.contains("<cooperation>")) {
            cooperation.setId(Long.valueOf(xml.substring(xml.indexOf("<id>") + 4, xml.indexOf("</id>"))));
            cooperation.setProjectRole(ProjectRole.valueOf(xml.substring(xml.indexOf("<projectRole>") + 13, xml.indexOf("</projectRole>"))));
            cooperation.setPerson(xmlToPerson(xml.substring(xml.indexOf("<person>"), xml.indexOf("</person>"))));
            cooperation.setProject(xmlToProject(xml.substring(xml.indexOf("<project>"), xml.indexOf("</project>"))));
            return cooperation;
        } else {
            return null;
        }
    }

    public String cooperationToXml(Cooperation cooperation) {
        if (cooperation == null) {
            return header;
        }
        return header
                + "<cooperation>"
                + "<id>" + cooperation.getId() + "</id>"
                + "<projectRole>" + cooperation.getProjectRole() + "</projectRole>"
                + personToXml(cooperation.getPerson())
                        .replace(header, "")
                + projectToXml(cooperation.getProject())
                        .replace(header, "")
                + "</cooperation>";
    }

    public List<TimeEntry> xmlToTimeEntries(String xml) {
        List<TimeEntry> timeEntries = new LinkedList<>();
        if (xml.contains("<timeEntry>")) {
            String[] xmlTimeEntries = xml.split("<timeEntry>");
            for (int i = 1; i < xmlTimeEntries.length; i++) {
                timeEntries.add(xmlToTimeEntry("<timeEntry>" + xmlTimeEntries[i]));
            }
        }
        return timeEntries;
    }

    public TimeEntry xmlToTimeEntry(String xml) {
        TimeEntry timeEntry = new TimeEntry();
        if (xml.contains("<timeEntry>")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String[] ids = xml.split("<id>");
                if (xml.contains("<category>")) {
                    timeEntry.setId(Long.valueOf(ids[3].substring(0, ids[3].indexOf("</id>"))));
                } else {
                    timeEntry.setId(Long.valueOf(ids[1].substring(0, ids[1].indexOf("</id>"))));
                }
                String from = xml.substring(xml.indexOf("<fromDate>") + 10, xml.indexOf("</fromDate>")).replace("T", " ");
                timeEntry.setFrom(sdf.parse(from));
                String to = xml.substring(xml.indexOf("<toDate>") + 8, xml.indexOf("</toDate>")).replace("T", " ");
                timeEntry.setTo(sdf.parse(to));
                timeEntry.setNote(xml.substring(xml.indexOf("<note>") + 6, xml.indexOf("</note>")));
                timeEntry.setMeasurement(Measurement.valueOf(xml.substring(xml.indexOf("<measurement>") + 13, xml.indexOf("</measurement>"))));
                timeEntry.setPerson(xmlToPerson(xml.substring(xml.indexOf("<person>"), xml.indexOf("</person>"))));
                if (xml.contains("<category>")) {
                    timeEntry.setCategory(xmlToCategory(xml.substring(xml.indexOf("<category>"), xml.indexOf("</category>"))));
                    if (timeEntry.getCategory().getName().equals("NULL") && timeEntry.getCategory().getProject().getName().equals("NULL")) {
                        timeEntry.setCategory(null);
                    }
                }
                return timeEntry;
            } catch (ParseException ex) {
                Logger.getLogger(XMLMapping.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public String timeEntryToXml(TimeEntry timeEntry) {
        if (timeEntry == null) {
            return header;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (timeEntry.getCategory() == null) {
            return header
                    + "<timeEntry>"
                    + "<id>" + timeEntry.getId() + "</id>"
                    + "<fromDate>" + dateFormat.format(timeEntry.getFrom()).replace(" ", "T") + "</fromDate>"
                    + "<toDate>" + dateFormat.format(timeEntry.getTo()).replace(" ", "T") + "</toDate>"
                    + "<note>" + timeEntry.getNote() + "</note>"
                    + "<measurement>" + timeEntry.getMeasurement() + "</measurement>"
                    + personToXml(timeEntry.getPerson())
                            .replace(header, "")
                    + categoryToXml(new CategoryService().getByProjectName(new ProjectService().getByName("NULL"), "NULL"))
                            .replace(header, "")
                    + "</timeEntry>";
        }
        return header
                + "<timeEntry>"
                + "<id>" + timeEntry.getId() + "</id>"
                + "<fromDate>" + dateFormat.format(timeEntry.getFrom()).replace(" ", "T") + "</fromDate>"
                + "<toDate>" + dateFormat.format(timeEntry.getTo()).replace(" ", "T") + "</toDate>"
                + "<note>" + timeEntry.getNote() + "</note>"
                + "<measurement>" + timeEntry.getMeasurement() + "</measurement>"
                + personToXml(timeEntry.getPerson())
                        .replace(header, "")
                + categoryToXml(timeEntry.getCategory())
                        .replace(header, "")
                + "</timeEntry>";
    }
}
