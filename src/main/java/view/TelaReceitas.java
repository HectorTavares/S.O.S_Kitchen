package view;

import db.DB;
import model.dao.DaoFactory;
import model.dao.IngredienteDao;
import model.dao.ReceitaDao;
import model.entities.Ingrediente;
import model.entities.Receita;
import model.entities.Usuario;
import util.FormatadorString;

import java.sql.Connection;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TelaReceitas extends MenuInicial {

    private static Usuario usuarioLogado;

    public TelaReceitas(Usuario usuarioLogado) {
        super(usuarioLogado);
        setUsuarioLogado(usuarioLogado);
    }

    public static void menuPesquisa() {
        Scanner teclado = new Scanner(System.in);
        boolean escolhido = false;
        do {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("---MENU PESQUISA--- \n" +
                    "Selecione o que deseja. \n" +
                    "0 - Voltar \n" +
                    "1 - Pesquisar Receitas pelo nome \n" +
                    "2 - Pesquisar Receitas por Ingrediente \n" +
                    "3 - Mostrar todas as Receitas ");
            System.out.println("-----------------------------------------------------------------------");
            String escolha = teclado.nextLine();
            switch (escolha) {
                case "1":
                    pesquisaPorNome();
                    break;
                case "2":
                    pesquisarPorIngredientesMenu();
                    break;
                case "3":
                    mostrarTodasReceitas();
                case "0":
                    escolhido = true;
                    break;
                default:
                    System.out.println("Digite um número válido.");
            }
        } while (!escolhido);


    }

    private static void mostrarTodasReceitas() {
        Scanner teclado = new Scanner(System.in);
        Connection conn = DB.getConnection();
        ReceitaDao receitaDao = DaoFactory.createReceitaDao(conn);
        List<Receita> resultadosDaPesquisa = new ArrayList<>();
        resultadosDaPesquisa = receitaDao.findAll();
        boolean continuar = true;
        do {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("--- TODAS RECEITAS ---");

            for (int i = 0; i < resultadosDaPesquisa.size(); i++) {
                System.out.println("-----------------------------------------------------------------------");
                System.out.println(i + " - " + resultadosDaPesquisa.get(i));
                System.out.println("-----------------------------------------------------------------------");
            }
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Escolha a Receita de sua preferência: ");
            int escolhido = teclado.nextInt();

            System.out.println("-----------------------------------------------------------------------");
            System.out.println(resultadosDaPesquisa.get(escolhido).imprimir());
            System.out.println("-----------------------------------------------------------------------");


            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Quer ver outra Receita da lista?\n" +
                    "S/N");
            System.out.println("-----------------------------------------------------------------------");

            String resposta = teclado.next();
            switch (resposta) {
                case "s":
                case "S":
                    continuar = true;
                    break;
                case "n":
                case "N":
                    continuar = false;
                    break;
                default:
                    System.out.println("Digite algo válido.");
            }

        } while (continuar);


    }



    public static void minhasReceitas() {
        Connection conn = DB.getConnection();
        Scanner teclado = new Scanner(System.in);
        boolean continuar = true;
        ReceitaDao receitaDao = DaoFactory.createReceitaDao(conn);
        List<Receita>minhasReceitas = new ArrayList<>(receitaDao.findAllByAutor(usuarioLogado));
        do{
            for (int i = 0; i < minhasReceitas.size(); i++) {
                System.out.println("-----------------------------------------------------------------------");
                System.out.println(i + " - " + minhasReceitas.get(i));
                System.out.println("-----------------------------------------------------------------------");
            }
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Escolha a Receita de sua preferência: ");
            int escolhido = teclado.nextInt();

            System.out.println("-----------------------------------------------------------------------");
            System.out.println(minhasReceitas.get(escolhido).imprimir());
            System.out.println("-----------------------------------------------------------------------");


            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Quer ver outra Receita da lista?\n" +
                    "S/N");
            System.out.println("-----------------------------------------------------------------------");

            String resposta = teclado.next();
            switch (resposta) {
                case "s":
                case "S":
                    continuar = true;
                    break;
                case "n":
                case "N":
                    continuar = false;
                    break;
                default:
                    System.out.println("Digite algo válido.");
            }

        }while(continuar);


        System.out.println("em desenvolvimento");
    }

    public static void pesquisaPorNome() {
        Connection conn = DB.getConnection();
        Scanner teclado = new Scanner(System.in);
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("---PESQUISA POR NOME--- \n" +
                "Digite o Nome da Receita que esta procurando : \n" +
                "Obs: tu pode digitar palavras chave referentes ao nome da receita.");
        System.out.println("-----------------------------------------------------------------------");
        String palavraPesquisada = teclado.nextLine();
        ReceitaDao receitaDao = DaoFactory.createReceitaDao(conn);
        List<Receita> resultadosDaPesquisa = new ArrayList<>();
        resultadosDaPesquisa = receitaDao.findByName(palavraPesquisada);

        System.out.println("Resultados da Pesquisa : ");
        for (int i = 0; i < resultadosDaPesquisa.size(); i++) {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println(i + " - " + resultadosDaPesquisa.get(i));
            System.out.println("-----------------------------------------------------------------------");
        }


        System.out.println("Escolha a Receita de sua preferência: ");
        int escolhido = teclado.nextInt();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(resultadosDaPesquisa.get(escolhido).imprimir());
        System.out.println("-----------------------------------------------------------------------");

    }

    public static void pesquisaPorIngredienteTipo1() {
        System.out.println("em desenvolvimento");
    }

    public static void pesquisaPorIngredienteTipo2() {
        System.out.println("em desenvolvimento");
    }

    public static void pesquisarPorIngredientesMenu() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("--- PESQUISA POR INGREDIENTES ---");
        System.out.println("No S.O.S Kitchen nós temos 2 tipos de pesquisa por ingredientes.");
        System.out.println("No tipo 1, você seleciona ingredientes, e os resultados da pesquisa serão receitas \n" +
                "que contenham somente os ingredientes selecionados.");
        System.out.println("No tipo 2, você seleciona ingredientes, e os resultados da pesquisa serão receitas que  \n" +
                "contenham os ingredientes, mas que também possam conter outros.");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Quer fazer a pesquisa em qual dos tipos? ");
        String escolha = teclado.nextLine();
        FormatadorString.formatarPalavra(escolha);

        switch (escolha) {
            case "1":
                pesquisaPorIngredienteTipo1();
                break;
            case "2":
                pesquisaPorIngredienteTipo2();
                break;
            default:
                System.out.println("Digitar um numero válido.");
        }


    }

    public static void setUsuarioLogado(Usuario usuarioLogado) {
        TelaReceitas.usuarioLogado = usuarioLogado;
    }
}
