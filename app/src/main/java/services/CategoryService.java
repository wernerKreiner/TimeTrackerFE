package services;

import entities.Category;

import java.util.List;

public class CategoryService extends Service<Category> {
    public CategoryService() {
    }

    @Override
    public List<Category> get() {
        return this.data.getCategories();
    }

    @Override
    public Category getById(long id) {
        return this.data.getCategory(id);
    }

    @Override
    public Category create(Category object) {
        return this.data.createCategory(object);
    }

    @Override
    public boolean edit(Category object) {
        return this.data.editCategory(object);
    }

    @Override
    public boolean remove(long id) {
        return this.data.removeCategory(id);
    }
}
