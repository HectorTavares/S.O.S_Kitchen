package model.dao.impl;

import db.DB;
import model.dao.AvaliacaoDao;
import model.dao.DaoFactory;
import model.entities.*;

import junit.framework.TestCase;
import org.junit.Assert;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDaoJDBCTest extends TestCase {

    public void testUsuarioAvalia() {
        Connection conn = DB.getConnection();
        AvaliacaoDao avaliacaoDao = DaoFactory.createAvaliacaoDao(conn);
        Avaliacao avaliacao = new Avaliacao();
        Usuario b = new Usuario(1,"remor","cabecudos","remor");
        avaliacao.setAvaliador(b);
        Receita receita = new Receita(1);
        avaliacao.setReceitaAvaliada(receita);
        avaliacao.setNota(2);
        avaliacaoDao.usuarioAvalia(avaliacao);
        System.out.println("reza pra ta certo");
    }

    public void testAdministradorAvalia() {
        Connection conn = DB.getConnection();
        AvaliacaoDao avaliacaoDao = DaoFactory.createAvaliacaoDao(conn);
        Avaliacao avaliacao = new Avaliacao();
        Usuario b = new Usuario(1,"remor","cabecudos","remor");
        LocalDate a = LocalDate.of(2021,3,3);
        Administrador adm = new Administrador(1,"hector","hector","hector");
        List<Ingrediente> lista = new ArrayList<>();
        lista.add(new Ingrediente(1,"banana"));
        Receita receita = new Receita(1,"strogo","2000","joga na panela pai",a,b,adm,lista);
        avaliacao.setReceitaAvaliada(receita);
        avaliacao.setAvaliador(adm);
        avaliacao.setNota(10);
        avaliacaoDao.administradorAvalia(avaliacao);
        System.out.println("reza pra ta certo");
    }

    public void testUpdateUsuario() {
        Connection conn = DB.getConnection();
        AvaliacaoDao avaliacaoDao = DaoFactory.createAvaliacaoDao(conn);
        Avaliacao avaliacao = new Avaliacao();
        Usuario b = new Usuario(1,"remor","cabecudos","remor");
        Receita receita = new Receita(1);
        avaliacao.setAvaliador(b);
        avaliacao.setReceitaAvaliada(receita);
        avaliacao.setNota(7);
        avaliacaoDao.updateUsuario(avaliacao);
        System.out.println("reza pra ta certo");
    }

    public void testUpdateAdministrador() {
        Connection conn = DB.getConnection();
        AvaliacaoDao avaliacaoDao = DaoFactory.createAvaliacaoDao(conn);
        Avaliacao avaliacao = new Avaliacao();
        Usuario b = new Usuario(1,"remor","cabecudos","remor");
        Receita receita = new Receita(1);
        avaliacao.setAvaliador(b);
        avaliacao.setReceitaAvaliada(receita);
        avaliacao.setNota(3);
        avaliacaoDao.updateAdministrador(avaliacao);
        System.out.println("reza pra ta certo");
    }

    public void testDeleteById() {
        Connection conn = DB.getConnection();
        AvaliacaoDao avaliacaoDao = DaoFactory.createAvaliacaoDao(conn);
        avaliacaoDao.deleteById(1);
    }

    public void testFindById() {
        Connection conn  = DB.getConnection();
        AvaliacaoDao avaliacaoDao = DaoFactory.createAvaliacaoDao(conn);
        System.out.println(avaliacaoDao.findById(1));
    }

    public void testFindMedia() {
        Connection conn = DB.getConnection();
        AvaliacaoDao avaliacaoDao = DaoFactory.createAvaliacaoDao(conn);
        Receita rceita = new Receita(1);
        System.out.println(avaliacaoDao.findMedia(rceita));
    }

    public void testeJaAvaliou(){
        Connection conn = DB.getConnection();
        AvaliacaoDao avaliacaoDao = DaoFactory.createAvaliacaoDao(conn);
        Receita receita = new Receita(6);
        Usuario a =new Usuario(5,"s","s","s");
        Assert.assertTrue(avaliacaoDao.jaAvaliou(receita,a));

    }
}