package view;

import db.DB;
import model.dao.*;
import model.entities.Ingrediente;
import model.entities.NivelAcesso;
import model.entities.Receita;
import model.entities.Usuario;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TelaConta {
    private final Usuario usuarioLogado;
    public TelaConta(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public void alterarDadosConta() throws InterruptedException {
        Scanner teclado = new Scanner(System.in);
        boolean repetir = true;
        do {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("---ALTERAR DADOS DA CONTA---");
            System.out.println("Aqui você poderá alterar os dados da sua conta");
            System.out.print("Quer alterar Qual dado da sua conta? \n" +
                    "0 - Voltar \n" +
                    "1 - Nome \n" +
                    "2 - Senha \n" +
                    "3 - Login\n");
            if (usuarioLogado.getNivelAcesso()==NivelAcesso.USUARIO){
                System.out.print("4- Desativar Conta \n");
            }
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
                case "4":
                    if (usuarioLogado.getNivelAcesso()==NivelAcesso.USUARIO){
                        desativarConta();
                    }else{
                        System.out.println("Digite algo válido. ");
                    }
                case "0":
                    repetir = false;
                    break;
                default:
                    System.out.println("Digite algo válido.");
            }
        } while (repetir);

    }

    private void desativarConta() throws InterruptedException {
        Scanner teclado = new Scanner(System.in);
        Connection conn = DB.getConnection();
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao(conn);
        System.out.println("--- DESATIVAÇÃO DE CONTA ---");
        System.out.println("Você está ciente que depois de desativar sua conta o aplicativo fechará e \n" +
                "você não irá mais conseguir utilizar a sua conta. \n" +
                "Gostaria mesmo de desativar a sua conta? \n" +
                "s/n");
        String resposta = teclado.next();
        switch (resposta) {
            case "s":
            case "S":
                Thread.sleep(1000);
                System.out.print("Digite sua senha corretamente : ");
                String senha = teclado.next();
                if (senha.equals(usuarioLogado.getSenha())){
                    usuarioDao.desativeById(usuarioLogado.getId());
                    System.out.println("Usuário Desativado com sucesso.");
                    Thread.sleep(1000);
                    System.exit(0);
                }else{
                    System.out.println("Senha incorreta.");
                }

                break;
            case "n":
            case "N":
                System.out.println("Boa escolha, continue utilizando nosso sistema!");
                break;
            default:
                System.out.println("Digite algo válido.");
        }
    }

    private void alterarLogin() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("---ALTERAR LOGIN---\n" +
                "Digite sua novo login : ");
        String novoLogin = teclado.nextLine();
        usuarioLogado.setLogin(novoLogin);
        Connection conn = DB.getConnection();
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao(conn);
        if (usuarioLogado.getNivelAcesso()== NivelAcesso.ADM){
            AdministradorDao administradorDao = DaoFactory.createAdministradorDao(conn);
            administradorDao.update(usuarioLogado);
        }else{
            usuarioDao.update(usuarioLogado);
        }

    }

    private void alterarSenha() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("---ALTERAR SENHA---\n" +
                "Digite sua nova senha : ");
        String novaSenha = teclado.nextLine();
        usuarioLogado.setSenha(novaSenha);
        Connection conn = DB.getConnection();
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao(conn);
        if (usuarioLogado.getNivelAcesso()== NivelAcesso.ADM){
            AdministradorDao administradorDao = DaoFactory.createAdministradorDao(conn);
            administradorDao.update(usuarioLogado);
        }else{
            usuarioDao.update(usuarioLogado);
        }
    }

    public void alterarNome() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("---ALTERAR NOME---\n" +
                "Digite seu novo nome: ");
        String novoNome = teclado.nextLine();
        usuarioLogado.setNome(novoNome);
        Connection conn = DB.getConnection();
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao(conn);
        if (usuarioLogado.getNivelAcesso()== NivelAcesso.ADM){
            AdministradorDao administradorDao = DaoFactory.createAdministradorDao(conn);
            administradorDao.update(usuarioLogado);
        }else{
            usuarioDao.update(usuarioLogado);
        }
    }

}
