package view;

import db.DB;
import model.dao.DaoFactory;
import model.dao.IngredienteDao;
import model.dao.ReceitaDao;
import model.dao.UsuarioDao;
import model.entities.Ingrediente;
import model.entities.Receita;
import model.entities.Usuario;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TelaConta extends MenuInicial {
    public TelaConta(Usuario usuarioLogado) {
        super(usuarioLogado);
    }

    public void alterarDadosConta() {
        Scanner teclado = new Scanner(System.in);
        boolean repetir = true;
        do {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("---ALTERAR DADOS DA CONTA---");
            System.out.println("Aqui você poderá alterar os dados da sua conta");
            System.out.println("Quer alterar Qual dado da sua conta? \n" +
                    "0 - Voltar \n" +
                    "1 - Nome \n" +
                    "2 - Senha \n" +
                    "3 - Login");
            System.out.println("-----------------------------------------------------------------------");
            String resposta = teclado.next();
            switch (resposta) {
                case "1":
                    alterarNome();
                    break;
                case "2":
                    alterarSenha();
                    break;
                case "3":
                    alterarLogin();
                    break;
                case "0":
                    repetir = false;
                    break;
                default:
                    System.out.println("Digite algo válido.");
            }
        } while (repetir);
    }

    private void alterarLogin() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("---ALTERAR LOGIN---\n" +
                "Digite sua novo login : ");
        String novoLogin = teclado.nextLine();
        usuarioLogado.setLogin(novoLogin);
        Connection conn = DB.getConnection();
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao(conn);
        usuarioDao.update(usuarioLogado);
    }

    private void alterarSenha() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("---ALTERAR SENHA---\n" +
                "Digite sua nova senha : ");
        String novaSenha = teclado.nextLine();
        usuarioLogado.setSenha(novaSenha);
        Connection conn = DB.getConnection();
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao(conn);
        usuarioDao.update(usuarioLogado);
    }

    public void alterarNome() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("---ALTERAR NOME---\n" +
                "Digite seu novo nome: ");
        String novoNome = teclado.nextLine();
        usuarioLogado.setNome(novoNome);
        Connection conn = DB.getConnection();
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao(conn);
        usuarioDao.update(usuarioLogado);
    }

}
