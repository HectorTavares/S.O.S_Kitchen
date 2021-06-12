package model.dao;

import model.entities.Administrador;
import model.entities.Usuario;

public interface AdministradorDao {
    void insert(Administrador obj);
    Administrador findById(Integer id); //read
    void update(Usuario obj);
    void deleteById(Integer id);
    void analyzeRecipe();
    Administrador findByLoginAndSenha(String login, String senha);
}
