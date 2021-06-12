package model.entities;

import db.DB;
import model.dao.AdministradorDao;
import model.dao.DaoFactory;
import model.dao.UsuarioDao;

import java.sql.Connection;

public class Administrador extends Usuario {
    public Administrador(int id, String nome, String senha, String login) {
        super(id, nome, senha, login);
    }
    public Administrador(){
        super();
    }

    public static boolean consegueLogar(String login, String senha){
        Connection conn = DB.getConnection();
        AdministradorDao admDao = DaoFactory.createAdministradorDao(conn);
        Administrador usuario = admDao.findByLoginAndSenha(login, senha);

        return usuario != null;
    }
}
