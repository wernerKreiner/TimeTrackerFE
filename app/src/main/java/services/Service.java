package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.List;

public abstract class Service<T> {
    
    protected BufferedReader br;
    protected BufferedWriter bw;
    protected int code;
    protected XMLMapping xmlMapper = new XMLMapping();
    protected String uri = "http://10.0.0.26:8080/TimeTrackerBackend/webresources/";
    
    public abstract List<T> get();
    public abstract T getById(long id);
    public abstract T create(T object);
    public abstract boolean edit(T object);
    public abstract boolean remove(long id);
}
