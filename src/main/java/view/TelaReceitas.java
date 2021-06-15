package view;

import db.DB;
import model.dao.DaoFactory;
import model.dao.IngredienteDao;
import model.dao.ReceitaDao;
import model.entities.Ingrediente;
import model.entities.Receita;
import model.entities.Usuario;
import util.FormatadorString;
import util.Sleep;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TelaReceitas {

    private final Usuario usuarioLogado;
    private final AvaliacaoReceita telaAvaliacaoReceita;
    private final ReceitaDao receitaDao;
    private final IngredienteDao ingredienteDao;
    private final Scanner teclado;

    public TelaReceitas(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        this.telaAvaliacaoReceita = new AvaliacaoReceita(usuarioLogado);
        Connection conn = DB.getConnection();
        this.receitaDao = DaoFactory.createReceitaDao(conn);
        this.teclado = new Scanner(System.in);
        this.ingredienteDao = DaoFactory.createIngredienteDao(conn);
    }

    public void menuPesquisa() throws InterruptedException {
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

    private void mostrarTodasReceitas() throws InterruptedException {
        List<Receita> resultadosDaPesquisa;
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
            Thread.sleep(1000);
            System.out.println("-----------------------------------------------------------------------");
            for (int i = 0; i < 1; i++) {
                try {
                    System.out.println("Escolha a Receita de sua preferência: ");
                    int escolhido = teclado.nextInt();
                    System.out.println("-----------------------------------------------------------------------");
                    System.out.println(resultadosDaPesquisa.get(escolhido).imprimir());
                    System.out.println("-----------------------------------------------------------------------");
                    Sleep.sleep();
                    telaAvaliacaoReceita.setReceitaAvaliada(resultadosDaPesquisa.get(escolhido));
                    telaAvaliacaoReceita.avaliarReceita();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Digitar Numero de Receita existente!");
                    i--;
                }
            }

            Thread.sleep(1000);
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

    public void alterarReceita(Receita receita) throws InterruptedException {
        boolean repetir = true;
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("---ALTERAR RECEITA---");
        do{

        System.out.println("Selecione o que deseja fazer:");
        System.out.println("0 -  Voltar\n" +
                "1 - Alterar Nome da Receita\n" +
                "2 - Alterar Tempo de preparo\n" +
                "3 - Alterar sequencia de preparo");
        String escolha = teclado.next();
        teclado.nextLine();
        Thread.sleep(1000);
            switch (escolha){
                case "1":
                    alterarNome(receita);
                    break;
                case "2":
                   alterarTempoDePreparo(receita);
                    break;
                case "3":
                   alterarSequenciaDePreparo(receita);
                    break;
                case "0":
                    repetir = false;
                    break;
                default:
                    System.out.println("Digite algo válido.");
            }
        }while(repetir);
    }

    public void alterarSequenciaDePreparo(Receita receita){
        System.out.print("Digite a Nova Sequencia de preparo da sua Receita: ");
        String novaSequenciaDePreparo = teclado.nextLine();
        receita.setSequenciaPreparo(novaSequenciaDePreparo);
        receitaDao.update(receita);
    }

    public void alterarTempoDePreparo(Receita receita){
        System.out.print("Digite o Novo Tempo de preparo da sua Receita: ");
        String novoTempoDePreparo = teclado.nextLine();
        receita.setTempoPreparo(novoTempoDePreparo);
        receitaDao.update(receita);
    }

    public void alterarNome(Receita receita){
        System.out.print("Digite o Novo Nome da sua Receita: ");
        String novoNome = teclado.nextLine();
        receita.setNome(novoNome);
        receitaDao.update(receita);
    }



    public void minhasReceitas() throws InterruptedException {
        boolean continuar = true;
        List<Receita> minhasReceitas = new ArrayList<>(receitaDao.findAllByAutor(usuarioLogado));
        do {
            if (!minhasReceitas.isEmpty()) {
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
                telaAvaliacaoReceita.setReceitaAvaliada(minhasReceitas.get(escolhido));
                telaAvaliacaoReceita.avaliarReceita();

                System.out.println("Gostaria de mudar algo da sua receita?\n" +
                        "(S/N) ");
                String mudanca = teclado.next();

                switch (mudanca){
                    case "S":
                    case "s":
                        alterarReceita(minhasReceitas.get(escolhido));
                        break;
                    default:
                        break;
                }

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
            } else {
                Sleep.sleep();
                System.out.println("Você não tem nenhuma receita cadastrada ainda, tente cadastrar alguma.");
                continuar = false;
                Thread.sleep(1000);
            }
        } while (continuar);
    }

    public void pesquisaPorNome() throws InterruptedException {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("---PESQUISA POR NOME--- \n" +
                "Digite o Nome da Receita que esta procurando : \n" +
                "Obs: tu pode digitar palavras chave referentes ao nome da receita.");
        System.out.println("-----------------------------------------------------------------------");
        String palavraPesquisada = teclado.nextLine();
        List<Receita> resultadosDaPesquisa;
        resultadosDaPesquisa = receitaDao.findByName(palavraPesquisada);
        if (resultadosDaPesquisa.isEmpty()) {
            System.out.println("Nenhuma receita encontrada");
            Thread.sleep(1500);
        } else {
            boolean continuar = true;
            do {
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
                Sleep.sleep();
                telaAvaliacaoReceita.setReceitaAvaliada(resultadosDaPesquisa.get(escolhido));
                telaAvaliacaoReceita.avaliarReceita();

                System.out.println("-----------------------------------------------------------------------");
                Sleep.sleep();
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

    }

    public void pesquisaPorIngredienteTipo1() throws InterruptedException {
        List<Receita> resultadoDaPesquisa;
        List<Ingrediente> ingredientesEscolhidos = new ArrayList<>();
        boolean continuar = true;
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("--- PESQUISA POR INGREDIENTE TIPO 1 ---");
        List<Ingrediente> todosIngredientes = new ArrayList<>(ingredienteDao.findAll());
        System.out.println("Voce quer realizar a pesquisa com quantos ingredientes?");
        int quantidadeIngredientes = teclado.nextInt();

        for (int i = 0; i < todosIngredientes.size(); i++) {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println(i + " - " + todosIngredientes.get(i));
            System.out.println("-----------------------------------------------------------------------");
        }
        System.out.println("Escolha ingredientes diferentes.");
        for (int i = 1; i <= quantidadeIngredientes; i++) {
            try {
                System.out.println("Escolha " + (quantidadeIngredientes - i + 1) + " ingrediente(s) ");
                int ingredienteEscolhido = teclado.nextInt();
                ingredientesEscolhidos.add(todosIngredientes.get(ingredienteEscolhido));
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Erro, digite um número válido");
                i--;
            }
        }

        resultadoDaPesquisa = receitaDao.findByAllIngredientes(ingredientesEscolhidos);
        if (resultadoDaPesquisa.isEmpty()) {
            System.out.println("Nenhuma receita encontrada com os requisitos da pesquisa!");
            Sleep.sleep();
        } else {
            do {
                System.out.println("Resultados da Pesquisa : ");
                for (int i = 0; i < resultadoDaPesquisa.size(); i++) {
                    System.out.println("-----------------------------------------------------------------------");
                    System.out.println(i + " - " + resultadoDaPesquisa.get(i));
                    System.out.println("-----------------------------------------------------------------------");
                }
                for (int i = 0; i < 1; i++) {
                    try {
                        System.out.println("Escolha a Receita de sua preferência: ");
                        int escolhido = teclado.nextInt();
                        System.out.println("-----------------------------------------------------------------------");
                        System.out.println(resultadoDaPesquisa.get(escolhido).imprimir());
                        System.out.println("-----------------------------------------------------------------------");
                        Sleep.sleep();
                        telaAvaliacaoReceita.setReceitaAvaliada(resultadoDaPesquisa.get(escolhido));
                        telaAvaliacaoReceita.avaliarReceita();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Digitar Numero de Receita existente!");
                        i--;
                    }
                }
                Sleep.sleep();
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


    }

    public void pesquisaPorIngredienteTipo2() throws InterruptedException {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("--- PESQUISA POR INGREDIENTE TIPO 2 ---");
        List<Receita> resultadoDaPesquisa;
        List<Ingrediente> ingredientesEscolhidos = new ArrayList<>();
        boolean continuar = true;
        List<Ingrediente> todosIngredientes = new ArrayList<>(ingredienteDao.findAll());
        System.out.println("Voce quer realizar a pesquisa com quantos ingredientes?");
        int quantidadeIngredientes = teclado.nextInt();

        for (int i = 0; i < todosIngredientes.size(); i++) {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println(i + " - " + todosIngredientes.get(i));
            System.out.println("-----------------------------------------------------------------------");
        }
        System.out.println("Escolha ingredientes diferentes.");
        for (int i = 1; i <= quantidadeIngredientes; i++) {
            try {
                System.out.println("Escolha " + (quantidadeIngredientes - i + 1) + " ingrediente(s) ");
                int ingredienteEscolhido = teclado.nextInt();
                ingredientesEscolhidos.add(todosIngredientes.get(ingredienteEscolhido));
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Erro, digite um número válido");
                i--;
            }
        }

        resultadoDaPesquisa = receitaDao.findByIngredientes(ingredientesEscolhidos);
        if (resultadoDaPesquisa.isEmpty()) {
            System.out.println("Nenhuma receita encontrada com os requisitos da pesquisa!");
            Sleep.sleep();
        } else {
            do {
                System.out.println("Resultados da Pesquisa : ");
                for (int i = 0; i < resultadoDaPesquisa.size(); i++) {
                    System.out.println("-----------------------------------------------------------------------");
                    System.out.println(i + " - " + resultadoDaPesquisa.get(i));
                    System.out.println("-----------------------------------------------------------------------");
                }

                for (int i = 0; i < 1; i++) {
                    try {
                        System.out.println("Escolha a Receita de sua preferência: ");
                        int escolhido = teclado.nextInt();
                        System.out.println("-----------------------------------------------------------------------");
                        System.out.println(resultadoDaPesquisa.get(escolhido).imprimir());

                        System.out.println("-----------------------------------------------------------------------");
                        Sleep.sleep();
                        telaAvaliacaoReceita.setReceitaAvaliada(resultadoDaPesquisa.get(escolhido));
                        telaAvaliacaoReceita.avaliarReceita();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Digitar Numero de Receita existente!");
                        i--;
                    }
                }
                Sleep.sleep();
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
    }

    public void pesquisarPorIngredientesMenu() throws InterruptedException {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("--- PESQUISA POR INGREDIENTES ---");
        System.out.println("No S.O.S Kitchen nós temos 2 tipos de pesquisa por ingredientes.");
        System.out.println("No tipo 1, você seleciona ingredientes, e os resultados da pesquisa serão receitas \n" +
                "que contenham somente todos os ingredientes selecionados.");
        System.out.println("No tipo 2, você seleciona ingredientes, e os resultados da pesquisa serão receitas que  \n" +
                "contenham algum dos ingredientes, mas que também possam conter outros não selecionados.");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Quer fazer a pesquisa em qual dos tipos? \n" +
                "1/2");
        String escolha = teclado.nextLine();
        FormatadorString.formatarPalavra(escolha);

        switch (escolha) {
            case "Type 1":
            case "Tipo 1":
            case "1":
                pesquisaPorIngredienteTipo1();
                break;
            case "Type 2":
            case "Tipo 2":
            case "2":
                pesquisaPorIngredienteTipo2();
                break;
            default:
                System.out.println("Digitar um numero válido.");
        }
    }
}
