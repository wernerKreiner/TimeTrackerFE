package services;

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

public class ProjectService extends Service<Project> {

    List<Project> projects = new LinkedList<>();
    Project project = new Project();
    boolean success = true;

    public ProjectService() {
        this.uri += "project/";
    }

    @Override
    public List<Project> get() {
        projects = new LinkedList<>();
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
                        projects = xmlMapper.xmlToProjects(output);
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
        return projects;
    }

    @Override
    public Project getById(long id) {
        project = new Project();
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
                        project = xmlMapper.xmlToProject(output);
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
        if (project.getName().equals("")) {
            return null;
        } else {
            return project;
        }
    }

    public Project getByName(String name) {
        for (Project p : get()) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public List<Project> getByPerson(Person person) {
        List<Project> projectList = new LinkedList<>();
        get().stream().filter((p) -> (new CooperationService().getByPersonProject(person, p) != null)).forEachOrdered((p) -> {
            projectList.add(p);
        });
        return projectList;
    }

    @Override
    public Project create(Project object) {
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
                    writer.write(xmlMapper.projectToXml(object));
                    writer.flush();
                    writer.close();
                    conn.getOutputStream().close();
                    if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
                        success = false;
                    }
                    conn.disconnect();
                    project = getByName(object.getName());
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
        return project;
    }

    @Override
    public boolean edit(Project object) {
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
                    writer.write(xmlMapper.projectToXml(object));
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
                    project = getById(id);
                    new CategoryService().removeByProject(project);
                    new CooperationService().removeByProject(project);
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
