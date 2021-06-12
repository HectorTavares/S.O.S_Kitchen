package model.dao;

import model.entities.Ingrediente;

import java.util.List;

public interface IngredienteDao {
    void insert(Ingrediente obj); //create
    void update(Ingrediente obj); //update
    void deleteById(Integer id);  //delete
    List<Ingrediente> findAll();  //read
    Ingrediente findById(Integer id); //read
}
