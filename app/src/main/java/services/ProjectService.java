package services;

import entities.Project;

import java.util.List;

public class ProjectService extends Service<Project> {

    public ProjectService() {

    }

    @Override
    public List<Project> get() {
        return ProjectService.data.getProjects();
    }

    @Override
    public Project getById(long id) {
        return ProjectService.data.getProject(id);
    }

    @Override
    public Project create(Project object) {
        return ProjectService.data.createProject(object);
    }

    @Override
    public boolean edit(Project object) {
        return ProjectService.data.editProject(object);
    }

    @Override
    public boolean remove(long id) {
        return ProjectService.data.removeProject(id);
    }


}
