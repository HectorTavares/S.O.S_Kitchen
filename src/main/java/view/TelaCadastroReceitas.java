package view;

import db.DB;
import model.dao.DaoFactory;
import model.dao.IngredienteDao;
import model.dao.ReceitaDao;
import model.entities.Ingrediente;
import model.entities.Receita;
import model.entities.Usuario;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TelaCadastroReceitas extends MenuInicial{

    private static Usuario usuarioLogado;

    public TelaCadastroReceitas(Usuario usuarioLogado) {
        super(usuarioLogado);
        setUsuarioLogado(usuarioLogado);
    }

    public static void cadastroReceitaMenu() {
        Scanner teclado = new Scanner(System.in);
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
            }


        } while (continuar);


    }


    public static void cadastroReceita(){
        Scanner teclado = new Scanner(System.in);
        Connection conn =  DB.getConnection();
        Receita pedidoReceita = new Receita();
        System.out.print("Digite Abaixo os dados da sua receita.\n" +
                "Nome da Receita : ");
        String nomeReceita = teclado.nextLine();
        System.out.println("Tempo de preparo (Especificar unidade de tempo) :");
        String tempoDePreparo = teclado.nextLine();
        System.out.println("Sequencia de Preparo : ");
        String sequenciaDePreparo = teclado.nextLine();
        LocalDate dataCadastro = LocalDate.now();
        pedidoReceita.setNome(nomeReceita);
        pedidoReceita.setData_Cadastro(dataCadastro);
        pedidoReceita.setTempoPreparo(tempoDePreparo);
        pedidoReceita.setSequenciaPreparo(sequenciaDePreparo);
        pedidoReceita.setAutor(usuarioLogado);
        List<Ingrediente> ingredientesEscolhidos=new ArrayList<>();
        IngredienteDao ingredienteDao = DaoFactory.createIngredienteDao(conn);

        List<Ingrediente> todosIngredientes = new ArrayList<>(ingredienteDao.findAll());
        System.out.println("Na sua receita vão quantos ingredientes?");
        int quantidadeIngredientes = teclado.nextInt();

        for (int i = 0; i < todosIngredientes.size(); i++) {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println(i + " - " + todosIngredientes.get(i));
            System.out.println("-----------------------------------------------------------------------");
        }

        for (int i=1;i<=quantidadeIngredientes;i++){
            System.out.println("Escolha "+(quantidadeIngredientes-i+1)+" ingrediente(s) ");
            int ingredienteEscolhido = teclado.nextInt();
            ingredientesEscolhidos.add(todosIngredientes.get(ingredienteEscolhido));
        }
        pedidoReceita.setIngredientes(ingredientesEscolhidos);

        ReceitaDao receitaDao = DaoFactory.createReceitaDao(conn);
        receitaDao.insertToAvaliation(pedidoReceita);

    }

    public static void setUsuarioLogado(Usuario usuarioLogado) {
        TelaCadastroReceitas.usuarioLogado = usuarioLogado;
    }
}
