package services;

import entities.Cooperation;

import java.util.List;

public class CooperationService extends Service<Cooperation> {

    @Override
    public List<Cooperation> get() {
        return this.data.getCooperations();
    }

    @Override
    public Cooperation getById(long id) {
        return this.data.getCooperation(id);
    }

    @Override
    public Cooperation create(Cooperation object) {
        return this.data.createCooperation(object);
    }

    @Override
    public boolean edit(Cooperation object) {
        return this.data.editCooperation(object);
    }

    @Override
    public boolean remove(long id) {
        return this.data.removeCooperation(id);
    }

}
