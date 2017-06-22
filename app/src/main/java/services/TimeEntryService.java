package services;

import entities.Category;
import entities.Person;
import entities.TimeEntry;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimeEntryService extends Service<TimeEntry> {
    
    List<TimeEntry> timeEntries = new LinkedList<>();
    TimeEntry timeEntry = new TimeEntry();
    boolean success = true;

    public TimeEntryService() {
        uri += "timeentry/";
    }

    @Override
    public List<TimeEntry> get() {
        timeEntries = new LinkedList<>();
        Thread thread = new Thread() {
            public void run() {
                try {
                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "application/xml");
                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    String output = br.readLine();
                    if (output != null) {
                        timeEntries = xmlMapper.xmlToTimeEntries(output);
                    }
                    conn.getInputStream().close();
                    conn.disconnect();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return timeEntries;
    }

    @Override
    public TimeEntry getById(long id) {
        timeEntry = new TimeEntry();
        Thread thread = new Thread() {
            public void run() {
                try {
                    URL url = new URL(uri + id);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "application/xml");
                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    String output = br.readLine();
                    if (output != null) {
                        timeEntry = xmlMapper.xmlToTimeEntry(output);
                    }
                    conn.getInputStream().close();
                    conn.disconnect();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (timeEntry.getPerson().getEmail().equals("")) {
            return null;
        } else {
            return timeEntry;
        }
    }

    public List<TimeEntry> getByPerson(Person person) {
        List<TimeEntry> timeEntryList = new LinkedList<>();
        get().stream().filter((te) -> (te.getPerson().getEmail().equals(person.getEmail()))).forEachOrdered((te) -> {
            timeEntryList.add(te);
        });
        return timeEntryList;
    }

    public List<TimeEntry> getByCategory(Category category) {
        List<TimeEntry> timeEntryList = new LinkedList<>();
        get().stream().filter((te) -> (te.getCategory() != null && te.getCategory().getName().equals(category.getName()) 
                && te.getCategory().getProject().getName().equals(category.getProject().getName()))).forEachOrdered((te) -> {
                    timeEntryList.add(te);
        });
        return timeEntryList;
    }

    @Override
    public TimeEntry create(TimeEntry object) {
        success = true;
        Thread thread = new Thread() {
            public void run() {
                try {
                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestProperty("Content-Type", "application/xml");
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                    writer.write(xmlMapper.timeEntryToXml(object));
                    writer.flush();
                    writer.close();
                    conn.getOutputStream().close();
                    if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
                        success = false;
                    }
                    conn.disconnect();
                    timeEntry = getByPerson(object.getPerson()).get(getByPerson(object.getPerson()).size() - 1);
                } catch (MalformedURLException | ProtocolException ex) {
                    Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!success) {
            return null;
        }
        return timeEntry;
    }

    @Override
    public boolean edit(TimeEntry object) {
        success = true;
        Thread thread = new Thread() {
            public void run() {
                try {
                    URL url = new URL(uri + object.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestProperty("Content-Type", "application/xml");
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                    writer.write(xmlMapper.timeEntryToXml(object));
                    writer.flush();
                    writer.close();
                    conn.getOutputStream().close();
                    if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
                        success = false;
                    }
                    conn.disconnect();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    @Override
    public boolean remove(long id) {
        success = true;
        Thread thread = new Thread() {
            public void run() {
                try {
                    URL url = new URL(uri + id);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("DELETE");
                    conn.setRequestProperty("Content-Type", "application/xml");
                    conn.setDoOutput(true);
                    if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT || getById(id) != null) {
                        success = false;
                    }
                    conn.disconnect();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean removeByPerson(Person person) {
        getByPerson(person).forEach((te) -> {
            remove(te.getId());
        });
        return true;
    }

    public boolean removeByCategory(Category category) {
        getByCategory(category).forEach((te) -> {
            remove(te.getId());
        });
        return true;
    }
}
