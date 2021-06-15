package view;

import db.DB;
import jdk.nashorn.internal.runtime.Undefined;
import model.dao.AdministradorDao;
import model.dao.DaoFactory;
import model.dao.UsuarioDao;
import model.entities.Administrador;
import model.entities.Usuario;

import java.sql.Connection;
import java.util.Scanner;

public class TelaLogin {

    public static void telaLoginUsuario() throws InterruptedException {
        Scanner teclado = new Scanner(System.in);
        Usuario usuarioLogado;
        boolean logado;
        String login;
        String senha;
        do {
            System.out.println("--- LOGIN ---");
            System.out.println("Preencha corretamente os campos para efetuar o login :");
            System.out.println("Login : ");
            login = teclado.nextLine();
            System.out.println("Senha : ");
            senha = teclado.nextLine();
            logado = Usuario.consegueLogar(login, senha);
            if (logado) {
                break;
            } else {
                System.out.println("Login ou Senha incorretos, verifique os campos.");
            }
        } while (!logado);
        Connection conn = DB.getConnection();
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao(conn);
        usuarioLogado = usuarioDao.findByLoginAndSenha(login, senha);
        MenuInicial menu = new MenuInicial(usuarioLogado);
        menu.menuInicial();
        //
    }

    public static void telaEscolhaLogin() throws InterruptedException {
        Scanner teclado = new Scanner(System.in);
        boolean escolhido = false;
        do {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Escolha o tipo de sua conta para logar corretamente. \n" +
                    "0 - Fechar Programa \n" +
                    "1 - Usuario \n" +
                    "2 - Administrador \n" +
                    "3 - Criar Nova Conta ");
            System.out.println("-----------------------------------------------------------------------");

            int escolha = teclado.nextInt();

            switch (escolha) {
                case 1:
                    escolhido = true;
                    telaLoginUsuario();
                    break;
                case 2:
                    escolhido = true;
                    telaLoginAdministrador();
                    break;
                case 3:
                    criarConta();
                    break;
                case 0:
                    escolhido = false;
                    break;
                default:
                    System.out.println("Digite um numero válido.");

            }
        } while (escolhido);
    }

    private static void telaLoginAdministrador() throws InterruptedException {
        Scanner teclado = new Scanner(System.in);
        Administrador adm = new Administrador();
        String login;
        String senha;
        boolean logado;
        do {
            System.out.println("--- LOGIN ---");
            System.out.println("Preencha corretamente os campos para efetuar o login :");
            System.out.println("Login : ");
            login = teclado.nextLine();
            adm.setLogin(login);
            System.out.println("Senha : ");
            senha = teclado.nextLine();
            adm.setSenha(senha);
            logado = Administrador.consegueLogar(login, senha);
            if (logado) {
                break;
            } else {
                System.out.println("Login ou Senha incorretos, verifique os campos.");
            }
        } while (!logado);
        Connection conn = DB.getConnection();
        AdministradorDao administradorDao = DaoFactory.createAdministradorDao(conn);
        adm = administradorDao.findByLoginAndSenha(login, senha);
        MenuInicial menu = new MenuInicial(adm);
        menu.menuInicial();
        //
    }

    public static void criarConta() throws InterruptedException {
        Scanner teclado = new Scanner(System.in);

        boolean cadastrado = false;
        do {
            System.out.println("---CADASTRO--- \n" +
                    "Digite seu nome : ");
            String nome = teclado.nextLine();
            System.out.println("Digite o Login Desejado :");
            String login = teclado.nextLine();
            System.out.println("Digite sua senha : ");
            String senha = teclado.nextLine();

            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(nome);
            novoUsuario.setLogin(login);
            novoUsuario.setSenha(senha);
            Connection conn = DB.getConnection();
            UsuarioDao usuarioDao = DaoFactory.createUsuarioDao(conn);
            usuarioDao.insert(novoUsuario);
            try {
                int nullpointer = novoUsuario.getId();
                cadastrado = true;
            } catch (NullPointerException ignored) {
                System.out.println("Login existente, tente outro.");
            }

        } while (!cadastrado);
        telaLoginUsuario();

    }

}
