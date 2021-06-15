package view;

import db.DB;
import model.dao.DaoFactory;
import model.dao.ReceitaDao;
import model.dao.SolicitacaoReceitaDao;
import model.entities.Administrador;
import model.entities.Receita;
import model.entities.Usuario;
import util.Sleep;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class FuncoesAdm {
    private final Usuario admLogado;
    public FuncoesAdm(Usuario admLogado) {
    this.admLogado=admLogado;
    }

    public void analisarPedidosReceitas() throws InterruptedException {
        Scanner teclado = new Scanner(System.in);
        Connection conn = DB.getConnection();
        SolicitacaoReceitaDao solicitacaoReceitaDao = DaoFactory.createSolicitacaoReceitaDao(conn);
        ReceitaDao receitaDao = DaoFactory.createReceitaDao(conn);
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("--- PEDIDOS DE RECEITAS ---");
        System.out.println("Aqui você pode analisar os pedidos de receitas feitos pelos usuários.");
        boolean repetir = true;
        do {
            List<Receita> todasSolicitacoes = solicitacaoReceitaDao.findAll();
            Sleep.sleep();
            if (todasSolicitacoes.isEmpty()){
                System.out.println("Nenhuma solicitação por enquanto!\n" +
                        "Volte mais tarde!");
                Sleep.sleep();
                repetir=false;
            }else{
                int i = 0;
                System.out.println("-----------------------------------------------------------------------");
                for (Receita receita : todasSolicitacoes) {
                    System.out.println("-----------------------------------------------------------------------");
                    System.out.print(i + "- "+ receita.mostrarSolicitacao());
                    System.out.println("-----------------------------------------------------------------------");
                    i++;
                }
                System.out.println("-----------------------------------------------------------------------");
                System.out.println("Escolha a solicitação para julgar.");
                int escolha = teclado.nextInt();

                System.out.println(todasSolicitacoes.get(escolha).imprimir());
                Sleep.sleep();
                boolean continuar = true;
                while (continuar) {
                    System.out.println("Vai aceitar a solicitação?\n" +
                            "(S/N)");
                    Thread.sleep(900);
                    String julgamento = teclado.next();
                    switch (julgamento) {
                        case "s":
                        case "S":
                            todasSolicitacoes.get(escolha).setConcessorDePermissao((Administrador) admLogado);
                            Integer antigoId = todasSolicitacoes.get(escolha).getIdReceita();
                            receitaDao.insert(todasSolicitacoes.get(escolha));
                            solicitacaoReceitaDao.desativeSolicitacion(antigoId);
                            continuar = false;
                            break;
                        case "N":
                        case "n":
                            System.out.println("Receita Negada!");
                            continuar = false;
                            break;
                        default:
                            System.out.println("Digite algo válido.");
                            continuar = true;
                            break;
                    }
                }

                System.out.println("Quer julgar mais algum pedido?\n" +
                        "(S/N)");
                String decisao = teclado.next();
                continuar = true;
                while (continuar) {
                    switch (decisao) {
                        case "S":
                        case "s":
                            repetir = true;
                            continuar = false;
                            break;
                        case "n":
                        case "N":
                            repetir = false;
                            continuar = false;
                            break;
                        default:
                            continuar = true;
                    }
                }

            }


        } while (repetir);


    }
}
