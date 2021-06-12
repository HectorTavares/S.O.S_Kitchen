package model.dao;

import db.DB;
import model.entities.Usuario;
import org.junit.Test;

import java.sql.Connection;

public class UsuarioDaoJDBCTest {
    @Test
    public void deveAcharUsuarioPeloId() {
        Connection conn = DB.getConnection();
        Usuario a = new Usuario();
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao(conn);
        Usuario teste = usuarioDao.findById(1);
        System.out.println(teste);
    }

    @Test
    public void deveModificarRegistroDaTabelaUsuarioPeloId(){
        Connection conn = DB.getConnection();
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao(conn);
        Usuario teste = usuarioDao.findById(2);
        System.out.println(teste);
        teste.setNome("Jobson");
        teste.setLogin("Usguri"); //pra ter ctz que está funcionando tem que olhar a tabela no MySQL
        teste.setSenha("12");
        usuarioDao.update(teste);
        System.out.println(teste);
    }

    @Test
    public void deveInserirUmUsuarioCorretamente(){
        Connection conn = DB.getConnection();
        Usuario a = new Usuario("Tavares","hector","hectortavares");
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao(conn);
        usuarioDao.insert(a);
        System.out.println("Usuario Cadastrado!");

    }

    @Test
    public void deveExcluirUmUsuarioCorretamente(){
        Connection conn = DB.getConnection();
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao(conn);
        usuarioDao.deleteById(2);
        System.out.println("Usuario Deletado Corretamente!");
    }
}
