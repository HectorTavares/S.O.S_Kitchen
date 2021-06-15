package model.dao.impl;

import db.DB;
import junit.framework.TestCase;
import model.dao.DaoFactory;
import model.dao.SolicitacaoReceitaDao;
import model.entities.Receita;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class SolicitacaoReceitaDaoJDBCTest extends TestCase {

    public void testInsert(){

    }

    public void testFindAll(){
        Connection conn = DB.getConnection();
        SolicitacaoReceitaDao solicitacaoReceitaDao = DaoFactory.createSolicitacaoReceitaDao(conn);
        List<Receita> a = solicitacaoReceitaDao.findAll();
        for (Receita receita : a) {
            System.out.println(receita.getIngredientes());
        }
    }
}