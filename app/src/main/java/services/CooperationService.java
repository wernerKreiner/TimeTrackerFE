package services;

import entities.Cooperation;
import entities.Person;
import entities.Project;
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

public class CooperationService extends Service<Cooperation> {

    List<Cooperation> cooperations = new LinkedList<>();
    Cooperation cooperation = new Cooperation();
    boolean success = true;
    
    public CooperationService() {
        uri += "cooperation/";
    }

    @Override
    public List<Cooperation> get() {
        cooperations = new LinkedList<>();
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
                        cooperations = xmlMapper.xmlToCooperations(output);
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
            Logger.getLogger(CooperationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cooperations;
    }

    @Override
    public Cooperation getById(long id) {
        cooperation = new Cooperation();
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
                        cooperation = xmlMapper.xmlToCooperation(output);
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
        if (cooperation.getProject().getName().equals("")) {
            return null;
        } else {
            return cooperation;
        }
    }

    public List<Cooperation> getByPerson(Person person) {
        List<Cooperation> cooperationList = new LinkedList<>();
        get().stream().filter((coop) -> (coop.getPerson().getEmail().equals(person.getEmail()))).forEachOrdered((coop) -> {
            cooperationList.add(coop);
        });
        return cooperationList;
    }

    public List<Cooperation> getByProject(Project project) {
        List<Cooperation> cooperationList = new LinkedList<>();
        get().stream().filter((coop) -> (coop.getProject().getName().equals(project.getName()))).forEachOrdered((coop) -> {
            cooperationList.add(coop);
        });
        return cooperationList;
    }

    public Cooperation getByPersonProject(Person person, Project project) {
        for (Cooperation c : getByPerson(person)) {
            if (c.getProject().getName().equals(project.getName())) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Cooperation create(Cooperation object) {
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
                    writer.write(xmlMapper.cooperationToXml(object));
                    writer.flush();
                    writer.close();
                    conn.getOutputStream().close();
                    if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
                        success = false;
                    }
                    conn.disconnect();
                    cooperation = getByPersonProject(object.getPerson(), object.getProject());
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
        return cooperation;
    }

    @Override
    public boolean edit(Cooperation object) {
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
                    writer.write(xmlMapper.cooperationToXml(object));
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
    
    public boolean removeByPerson(Person person){
        getByPerson(person).forEach((coop) -> {
            remove(coop.getId());
        });
        return true;
    }
    
    public boolean removeByProject(Project project){
        getByProject(project).forEach((coop) -> {
            remove(coop.getId());
        });
        return true;
    }
}
