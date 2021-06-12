package model.dao;

import model.entities.Ingrediente;
import model.entities.Receita;
import model.entities.Usuario;

import java.util.List;

public interface ReceitaDao {
    void insert(Receita obj); //create
    void insertToAvaliation(Receita obj); //create
    void update(Receita obj); //update
    void deleteById(Integer id);  //delete
    List<Receita> findAll();  //read
    Receita findById(Integer id); //read
    List<Receita> findByAllIngredientes(List<Ingrediente> listaIngredientes);
    List<Receita> findByIngredientes(List<Ingrediente> listaIngredientes);
    List<Receita> findByName(String nome);
    List<Receita> findAllByAutor(Usuario autor);
}
