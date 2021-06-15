package view;

import db.DB;
import exception.NotaAvaliacao;
import model.dao.AvaliacaoDao;
import model.dao.DaoFactory;
import model.entities.Avaliacao;
import model.entities.Receita;
import model.entities.Usuario;
import util.Sleep;

import java.sql.Connection;
import java.util.Scanner;

public class AvaliacaoReceita {
    private final Usuario usuarioLogado;
    private Receita receitaAvaliada;
    private final AvaliacaoDao avaliacaoDao;
    private  Scanner teclado;

    public AvaliacaoReceita(Usuario usuarioLogado) {
        Connection conn = DB.getConnection();
        this.usuarioLogado = usuarioLogado;
        this.avaliacaoDao = DaoFactory.createAvaliacaoDao(conn);

    }

    public void avaliarReceita() throws InterruptedException {
        teclado = new Scanner(System.in);
        boolean repetir = true;
        System.out.println("Você Gostaria de avaliar esta Receita?\n" +
                "Avaliar as receitas faz com que a comunidade se ajude!\n" +
                "(S/N)");
        do {
            String resposta = teclado.next();
            switch (resposta) {
                case "s":
                case "S":
                    Avaliacao avaliacao = new Avaliacao();
                    for (int i = 0; i < 1; i++) {
                        try {
                            if (!avaliacaoDao.jaAvaliou(receitaAvaliada, usuarioLogado)) {
                                System.out.println("Qual a nota você Gostaria de dar a esta receita? (1 a 10)");
                                int nota = teclado.nextInt();
                                avaliacao.setNota(nota);
                                avaliacao.setAvaliador(usuarioLogado);
                                avaliacao.setReceitaAvaliada(receitaAvaliada);
                                avaliacaoDao.usuarioAvalia(avaliacao);
                            } else {
                                double nota = avaliacaoDao.findNotaByReceitaAndAvaliador(receitaAvaliada, usuarioLogado);
                                System.out.println("Voce já avaliou esta receita com a nota " + nota + " Gostaria de mudar esta nota?\n" +
                                        "(S/N)");
                                String mudarNota = teclado.next();
                                switch (mudarNota) {
                                    case "S":
                                    case "s":
                                        System.out.println("Qual a nota você Gostaria de dar a esta receita? (1 a 10)");
                                        int novaNota = teclado.nextInt();
                                        avaliacao.setNota(novaNota);
                                        avaliacao.setReceitaAvaliada(receitaAvaliada);
                                        avaliacao.setAvaliador(usuarioLogado);
                                        avaliacaoDao.updateUsuario(avaliacao);
                                        break;
                                    case "N":
                                    case "n":
                                        System.out.println("A nota foi mantida!");
                                        break;
                                }
                            }
                        } catch (NotaAvaliacao e) {
                            System.out.println(e.getMessage());
                            i--;
                        }
                    }
                    repetir = false;
                    break;
                case "n":
                case "N":
                    Sleep.sleep();
                    repetir = false;
                    break;
                default:
                    System.out.println("Digite algo válido.");
            }
        } while (repetir);
    }

    public void setReceitaAvaliada(Receita receitaAvaliada) {
        this.receitaAvaliada = receitaAvaliada;
    }
}
