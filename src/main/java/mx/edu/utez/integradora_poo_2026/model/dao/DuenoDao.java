package mx.edu.utez.integradora_poo_2026.model.dao;

import mx.edu.utez.integradora_poo_2026.model.Dueno;

import java.util.List;

public class DuenoDao implements Dao<Dueno, Integer>{
    @Override
    public boolean create(Dueno entidad) {
        return false;
    }

    @Override
    public List<Dueno> getAll() {
        return List.of();
    }

    @Override
    public Dueno getById(Integer id) {
        return null;
    }

    @Override
    public boolean update(Dueno entidad) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
