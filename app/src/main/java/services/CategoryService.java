package services;

import entities.Category;
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

public class CategoryService extends Service<Category> {

    List<Category> categories = new LinkedList<>();
    Category category = new Category();
    boolean success = true;

    public CategoryService() {
        uri += "category/";
    }

    @Override
    public List<Category> get() {
        categories = new LinkedList<>();
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
                        categories = xmlMapper.xmlToCategories(output);
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
        return categories;
    }

    @Override
    public Category getById(long id) {
        category = new Category();
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
                        category = xmlMapper.xmlToCategory(output);
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
        if (category.getName().equals("")) {
            return null;
        } else {
            return category;
        }
    }

    public List<Category> getByProject(Project project) {
        List<Category> categoryList = new LinkedList<>();
        get().stream().filter((cat) -> (cat.getProject().getName().equals(project.getName()))).forEachOrdered((cat) -> {
            categoryList.add(cat);
        });
        return categoryList;
    }

    public List<Category> getByName(String name) {
        List<Category> categoryList = new LinkedList<>();
        get().stream().filter((cat) -> (cat.getName().equals(name))).forEachOrdered((cat) -> {
            categoryList.add(cat);
        });
        return categoryList;
    }

    public Category getByProjectName(Project project, String name) {
        for (Category cat : getByProject(project)) {
            if (cat.getName().equals(name)) {
                return cat;
            }
        }
        return null;
    }

    @Override
    public Category create(Category object) {
        if (getByProjectName(object.getProject(), object.getName()) != null) {
            return null;
        }
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
                    writer.write(xmlMapper.categoryToXml(object));
                    writer.flush();
                    writer.close();
                    conn.getOutputStream().close();
                    if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
                        success = false;
                    }
                    conn.disconnect();
                    category = getByProjectName(object.getProject(), object.getName());
                } catch (MalformedURLException | ProtocolException ex) {
                    Logger.getLogger(CategoryService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(CategoryService.class.getName()).log(Level.SEVERE, null, ex);
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
        return category;
    }

    @Override
    public boolean edit(Category object) {
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
                    writer.write(xmlMapper.categoryToXml(object));
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
                    category = getById(id);
                    new TimeEntryService().removeByCategory(category);
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

    public boolean removeByProject(Project project) {
        getByProject(project).forEach((cat) -> {
            remove(cat.getId());
        });
        return true;
    }

}
