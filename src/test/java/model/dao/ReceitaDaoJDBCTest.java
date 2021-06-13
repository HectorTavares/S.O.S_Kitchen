package model.dao;

import db.DB;
import model.entities.Administrador;
import model.entities.Ingrediente;
import model.entities.Receita;
import model.entities.Usuario;
import org.junit.Test;
import util.FormatadorString;
import util.VerificadorDeRepeticao;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReceitaDaoJDBCTest {
    @Test
    public void deveRegistarReceitaCorretamenteNoBanco() {
        Connection conn = DB.getConnection();
        Receita a = new Receita();
        ReceitaDao receitaDao = DaoFactory.createReceitaDao(conn);
        a.setNome("doce ocular");
        a.setTempoPreparo("230");
        a.setSequenciaPreparo("bota na lata3.");
        a.setData_Cadastro(LocalDate.now());
        a.setAutor(new Usuario(1, "remor", "cabecudos", "remor"));
        List<Ingrediente> ingredientes = new ArrayList<>();
        a.setConcessorDePermissao(new Administrador(1, "hector", "hector", "hector"));

        ingredientes.add(new Ingrediente(9, "crack"));
        ingredientes.add(new Ingrediente(8, "corneas"));
        ingredientes.add(new Ingrediente(4,"leite condensado"));


        a.setIngredientes(ingredientes);
        receitaDao.insert(a);
        System.out.println("Receita Cadastrada, Confira no banco");
        System.out.println(receitaDao.findById(a.getIdReceita()));

    }

    @Test
    public void deveRegistarUmPedidoDeReceitaNoBanco(){
        Connection conn = DB.getConnection();
        Receita a = new Receita();
        ReceitaDao receitaDao = DaoFactory.createReceitaDao(conn);
        a.setTempoPreparo("20000");

        a.setSequenciaPreparo("Cortar frango em posições quadriculadas, colcoar na panela em alta temperatura ...");
        a.setData_Cadastro(LocalDate.now());
        a.setNome("strogonoff do testas");
        a.setAutor(new Usuario(1, "remor", "cabecudos", "remor"));
        List<Ingrediente> ingredientes = new ArrayList<>();
        a.setConcessorDePermissao(new Administrador(1, "hector", "hector", "hector"));

        ingredientes.add(new Ingrediente(6, "Acucar"));
        ingredientes.add(new Ingrediente(3, "alho"));
        ingredientes.add(new Ingrediente(5,"leite"));
        ingredientes.add(new Ingrediente(8,"OVo"));

        a.setIngredientes(ingredientes);
        receitaDao.insertToAvaliation(a);

    }

    @Test
    public void deveExcluirReceitaDeIdInformado() {
        Connection conn = DB.getConnection();
        ReceitaDao receitaDao = DaoFactory.createReceitaDao(conn);
        receitaDao.deleteById(12);
    }

    @Test
    public void deveConsultarTodasAsReceitasDoBanco(){
        Connection conn = DB.getConnection();

        ReceitaDao receitaDao = DaoFactory.createReceitaDao(conn);
        List<Receita>todasReceitas =receitaDao.findAll();
        System.out.println(todasReceitas);
    }

    @Test
    public void deveInserirRegistroNaTabelaDeAvaliacaoDeReceita(){
        Connection conn = DB.getConnection();
        Receita a = new Receita();
        ReceitaDao receitaDao = DaoFactory.createReceitaDao(conn);
        a.setNome("Frango Gostosinho");
        a.setTempoPreparo("20 pras 2");
        a.setSequenciaPreparo("Cortar frango");
        a.setData_Cadastro(LocalDate.now());
        a.setAutor(new Usuario(1, "remor", "cabecudos", "remor"));
        List<Ingrediente> ingredientes = new ArrayList<>();
        a.setConcessorDePermissao(new Administrador(1, "hector", "hector", "hector"));
        ingredientes.add(new Ingrediente(6, "acucar"));
        ingredientes.add(new Ingrediente(3, "Alho"));
        ingredientes.add(new Ingrediente(1, "Banana"));
        a.setIngredientes(ingredientes);
        receitaDao.insertToAvaliation(a);
        System.out.println("Receita Cadastrada, Confira no banco");
    }

    @Test
    public void deveCOnsultarUmaReceitaAPartirDoId(){
        Connection conn = DB.getConnection();
        ReceitaDao receitaDao = DaoFactory.createReceitaDao(conn);
        System.out.println(receitaDao.findById(1));
    }

    @Test
    public void testeDoFindByName(){
        Connection conn = DB.getConnection();
        ReceitaDao receitaDao = DaoFactory.createReceitaDao(conn);
        System.out.println(receitaDao.findByName("batata"));
    }

    @Test
    public void testeDoFindByAllIngrediente(){
        Connection conn = DB.getConnection();
        ReceitaDao receitaDao = DaoFactory.createReceitaDao(conn);
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(new Ingrediente(1, "ovo"));
        System.out.println(receitaDao.findByAllIngredientes(ingredientes));
    }

    @Test
    public void testeDoFindByIngrediente(){
        Connection conn = DB.getConnection();
        ReceitaDao receitaDao = DaoFactory.createReceitaDao(conn);
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(new Ingrediente(9, "crack"));
        ingredientes.add(new Ingrediente(8,"corneas"));
        ingredientes.add(new Ingrediente(1,"ovo"));
        ingredientes.add(new Ingrediente(2,"leite"));
        ingredientes.add(new Ingrediente(3,"acucar"));
        ingredientes.add(new Ingrediente(4,"leite condensado"));
        ingredientes.add(new Ingrediente(5,"batata"));
        ingredientes.add(new Ingrediente(6,"gema"));
        ingredientes.add(new Ingrediente(7,"pilula"));


        List<Receita> a = receitaDao.findByAllIngredientes(ingredientes);
        System.out.println(a);
        System.out.println("--------------------------------------------");
        a = VerificadorDeRepeticao.verificarRepeticao(a);
        System.out.println(a);
    }

    @Test
    public void testeFormatacao(){
        String a = "colocaR Na Panela DE PRESSao.junto DO BB";
        System.out.println(FormatadorString.formatarFrase(a));
    }

}
