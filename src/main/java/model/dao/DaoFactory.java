package model.dao;

import model.dao.impl.*;

import java.sql.Connection;

public class DaoFactory {
    public static AdministradorDao createAdministradorDao(Connection conn){
       return new AdministadorDaoJDBC(conn);
    }
    public static ReceitaDao createReceitaDao(Connection conn) {
        return new ReceitaDaoJDBC(conn);
    }
    public static UsuarioDao createUsuarioDao(Connection conn){
        return new UsuarioDaoJDBC(conn);
    }
    public static IngredienteDao createIngredienteDao(Connection conn){
        return new IngredienteDaoJDBC(conn);
    }
    public static AvaliacaoDao createAvaliacaoDao(Connection conn){
        return new AvaliacaoDaoJDBC(conn);
    }

}
