package model.dao.impl;

import db.DB;

import model.dao.DaoFactory;
import model.dao.IngredienteDao;
import model.entities.Ingrediente;
import org.junit.Test;

import java.sql.Connection;

public class IngredienteDaoJDBCTest {
    @Test
    public void deveInserirIngredienteNoBancoEDepoisPegarPeloId(){
        Connection conn = DB.getConnection();
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNome("Ovo");
        IngredienteDao ingredienteDao = DaoFactory.createIngredienteDao(conn);
        ingredienteDao.insert(ingrediente);
        System.out.println(ingredienteDao.findById(ingrediente.getIdIngrediente()));
    }

    @Test
    public void deveExcluirIngredienteDoBanco(){
        Connection conn = DB.getConnection();
        IngredienteDao ingredienteDao = DaoFactory.createIngredienteDao(conn);
        ingredienteDao.deleteById(1);
    }

    @Test
    public void deveModificarONomeDeUmIngredientePostoAnteiriormenteEmVossoBancoDeDados(){
        Connection conn = DB.getConnection();
        IngredienteDao ingredienteDao = DaoFactory.createIngredienteDao(conn);
        Ingrediente ingrediente =  new Ingrediente(1,"Alecrim");
        ingredienteDao.update(ingrediente);
    }

    @Test
    public void devePegarTodosOsIngredientes(){
        Connection conn = DB.getConnection();
        IngredienteDao ingredienteDao = DaoFactory.createIngredienteDao(conn);
        System.out.println(ingredienteDao.findAll());
    }

}
