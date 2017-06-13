package services;

import entities.Person;
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

public class PersonService extends Service<Person> {

    List<Person> persons = new LinkedList<>();
    Person person = new Person();
    boolean success = true;

    public PersonService() {
        this.uri += "person/";
    }

    @Override
    public List<Person> get() {
        persons = new LinkedList<>();
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
                        persons = xmlMapper.xmlToPersons(output);
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
        return persons;
    }

    @Override
    public Person getById(long id) {
        person = new Person();
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
                        person = xmlMapper.xmlToPerson(output);
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
        if (person.getEmail().equals("")) {
            return null;
        } else {
            return person;
        }
    }

    public Person getByEmail(String email) {
        for (Person p : get()) {
            if (p.getEmail().equals(email)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public Person create(Person object) {
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
                    writer.write(xmlMapper.personToXml(object));
                    writer.flush();
                    writer.close();
                    conn.getOutputStream().close();
                    if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
                        success = false;
                    }
                    conn.disconnect();
                    person = getByEmail(object.getEmail());
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
        return person;
    }

    @Override
    public boolean edit(Person object) {
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
                    writer.write(xmlMapper.personToXml(object));
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
                    person = getById(id);
                    new TimeEntryService().removeByPerson(person);
                    new CooperationService().removeByPerson(person);
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
}
