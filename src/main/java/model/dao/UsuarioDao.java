package model.dao;

import model.entities.Usuario;

public interface UsuarioDao {
    void insert(Usuario obj);
    void update(Usuario obj);
    void deleteById(Integer id);
    Usuario findById(Integer id);
    Usuario findByLoginAndSenha(String login, String senha);
}
