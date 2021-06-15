package view;

import db.DB;
import model.dao.DaoFactory;
import model.dao.IngredienteDao;
import model.dao.SolicitacaoReceitaDao;
import model.entities.Ingrediente;
import model.entities.Receita;
import model.entities.Usuario;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TelaCadastroReceitas {

    private final Usuario usuarioLogado;
    private final CadastroIngredientes cadastroIngredientes;
    private final Scanner teclado;
    private final IngredienteDao ingredienteDao;
    private final SolicitacaoReceitaDao solicitacaoReceitaDao;

    public TelaCadastroReceitas(Usuario usuarioLogado) {
        this.teclado = new Scanner(System.in);
        this.usuarioLogado = usuarioLogado;
        this.cadastroIngredientes = new CadastroIngredientes();
        Connection conn = DB.getConnection();
        this.ingredienteDao = DaoFactory.createIngredienteDao(conn);
        this.solicitacaoReceitaDao = DaoFactory.createSolicitacaoReceitaDao(conn);

    }

    public void cadastroReceitaMenu() {
        boolean continuar = true;
        do {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("---CADASTRO DE RECEITAS---");
            System.out.println("Aqui você pode cadastrar uma receita,mas ela só irá aparecer para os usuários \n" +
                    "quando um Administrador Aceitar a Receita.");
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Quer Cadastrar um pedido de receita?\n" +
                    "S/N ");
            String resposta = teclado.next();
            switch (resposta) {
                case "S":
                case "s":
                    cadastroReceita();
                    break;
                case "n":
                case "N":
                    continuar = false;
                    break;
                default:
                    System.out.println("Digite S/N.");
            }
        } while (continuar);
    }


    public void cadastroReceita() {
        Receita pedidoReceita = new Receita();
        System.out.print("Digite Abaixo os dados da sua receita.\n" +
                "Nome da Receita : ");
        String nomeReceita = teclado.nextLine();
        System.out.print("Tempo de preparo (Especificar unidade de tempo) :");
        String tempoDePreparo = teclado.nextLine();
        System.out.print("Sequencia de Preparo : ");
        String sequenciaDePreparo = teclado.nextLine();
        LocalDate dataCadastro = LocalDate.now();
        pedidoReceita.setNome(nomeReceita);
        pedidoReceita.setData_Cadastro(dataCadastro);
        pedidoReceita.setTempoPreparo(tempoDePreparo);
        pedidoReceita.setSequenciaPreparo(sequenciaDePreparo);
        pedidoReceita.setAutor(usuarioLogado);
        List<Ingrediente> ingredientesEscolhidos = new ArrayList<>();

        List<Ingrediente> todosIngredientes = new ArrayList<>(ingredienteDao.findAll());
        System.out.println("Na sua receita vão quantos ingredientes?");
        int quantidadeIngredientes = teclado.nextInt();

        for (int i = 0; i < todosIngredientes.size(); i++) {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println(i + " - " + todosIngredientes.get(i));
            System.out.println("-----------------------------------------------------------------------");
        }
        System.out.println("A sua receita precisa de ingredientes que não foram listados acima?\n" +
                "(S/N)");
        String precisaIngredientes = teclado.next();
        boolean continuar = true;
        while (continuar)
            switch (precisaIngredientes) {
                case "s":
                case "S":
                    System.out.println("Quantos ingredientes faltaram?");
                    int quantidade = teclado.nextInt();
                    for (int i = 1; i <= quantidade; i++) {
                        cadastroIngredientes.cadastrarIngredientes();
                    }
                    todosIngredientes = ingredienteDao.findAll();
                    for (int i = 0; i < todosIngredientes.size(); i++) {
                        System.out.println("-----------------------------------------------------------------------");
                        System.out.println(i + " - " + todosIngredientes.get(i));
                        System.out.println("-----------------------------------------------------------------------");
                    }
                    continuar = false;
                    break;
                case "N":
                case "n":
                    System.out.println("Então somente siga as proximas instruções!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Digite algo válido.");
                    precisaIngredientes = teclado.next();
                    continuar = true;
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
        pedidoReceita.setIngredientes(ingredientesEscolhidos);
        solicitacaoReceitaDao.insert(pedidoReceita);

    }

}
