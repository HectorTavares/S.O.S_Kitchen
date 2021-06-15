package model.dao.impl;

import db.DB;
import model.dao.AdministradorDao;
import model.dao.DaoFactory;
import model.entities.Administrador;
import org.junit.Test;

import java.sql.Connection;

public class AdministradorDaoJDBCTest {
    @Test
    public void deveEncontrarUmAdministradorQuandoInformadoId(){
        Connection conn = DB.getConnection();
        AdministradorDao usuarioDao = DaoFactory.createAdministradorDao(conn);
        Administrador teste = usuarioDao.findById(3);
        System.out.println(teste);
    }

    @Test
    public void deveModificarRegistroDaTabelaAdministradorPeloId(){
        Connection conn = DB.getConnection();
        AdministradorDao admDao = DaoFactory.createAdministradorDao(conn);
        Administrador teste = admDao.findById(3);
        System.out.println(teste);
        teste.setNome("Remor");
        teste.setLogin("remormelhorprofessor");
        teste.setSenha("cabecudos");
        admDao.update(teste);
        System.out.println(teste);
    }

    @Test
    public void deveInserirUmAdministradorCorretamente(){
        Connection conn = DB.getConnection();
        Administrador a = new Administrador();
        a.setLogin("Tavares");
        a.setNome("Hector");
        a.setSenha("hectortavares");
        AdministradorDao admDao = DaoFactory.createAdministradorDao(conn);
        admDao.insert(a);
        System.out.println("Adm Cadastrado!");

    }

    @Test
    public void deveExcluirUmUsuarioCorretamente(){
        Connection conn = DB.getConnection();
        AdministradorDao admDao = DaoFactory.createAdministradorDao(conn);
        admDao.deleteById(3);
        System.out.println("Usuario Deletado Corretamente!");
    }



}
