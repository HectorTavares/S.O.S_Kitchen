package view;

import model.entities.Usuario;

import java.util.Scanner;

public class MenuInicial {
    protected final Usuario usuarioLogado;
    protected final TelaConta telaConta ;
    protected final TelaReceitas telaReceitas;
    protected final TelaCadastroReceitas telaCadastroReceitas;

    public MenuInicial(Usuario usuarioLogado){
        this.usuarioLogado=usuarioLogado;
        this.telaConta = new TelaConta(usuarioLogado);
        this.telaReceitas = new TelaReceitas(usuarioLogado);
        this.telaCadastroReceitas = new TelaCadastroReceitas(usuarioLogado);
        //iniciar todas as telas e arrumar metodos staticos
    }

    public void menuInicial() {

        Scanner teclado = new Scanner(System.in);
        boolean escolhido = false;
        do {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Bem-Vindo ao S.O.S Kitchen, O aplicativo que lhe socorre na cozinha! \n" +
                    "Selecione o que você deseja fazer.");

            System.out.println("0 - Deslogar \n" +
                    "1 - Pesquisar Receitas \n" +
                    "2 - Registar Receitas \n" +
                    "3 - Alterar Dados da conta \n" +
                    "4 - Minhas Receitas ");
            System.out.println("-----------------------------------------------------------------------");
            String escolha = teclado.nextLine();
            switch (escolha){
                case "1":
                   telaReceitas.menuPesquisa();
                    break;
                case "2":

                    telaCadastroReceitas.cadastroReceitaMenu();
                    break;
                case "3":
                    telaConta.alterarDadosConta();
                    break;
                case "4":
                    telaReceitas.minhasReceitas();
                    break;
                case "0":
                    escolhido = true;
                    break;
                default:
                    System.out.println("Digite um número válido.");

            }
        }while(!escolhido);

    }

}
