package services;

import java.util.List;

public abstract class Service<T> {
    public static Data data = new Data();

    public abstract List<T> get();

    public abstract T getById(long id);

    public abstract T create(T object);

    public abstract boolean edit(T object);

    public abstract boolean remove(long id);
}
