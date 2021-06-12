package view;

import db.DB;
import model.dao.DaoFactory;
import model.dao.UsuarioDao;
import model.entities.Usuario;

import java.sql.Connection;
import java.util.Scanner;

public class TelaConta extends MenuInicial{
    public TelaConta(Usuario usuarioLogado) {
        super(usuarioLogado);
    }

    public void alterarDadosConta(){
        Scanner teclado = new Scanner(System.in);
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("---Alterar dados da conta---");
        System.out.println("Aqui você poderá alterar os dados da sua conta");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Tem certeza que quer alterar seus dados?\n" +
                "S/N");
        String resposta = teclado.nextLine();
        switch (resposta){
            case"S":
            case "s":
                System.out.println("Digite seu novo nome:");
                String novoNome = teclado.nextLine();
                usuarioLogado.setNome(novoNome);
                System.out.println("Digite seu novo login: ");
                String novoLogin = teclado.nextLine();
                usuarioLogado.setLogin(novoLogin);
                System.out.println("Digite sua nova Senha: ");
                String novaSenha = teclado.nextLine();
                usuarioLogado.setSenha(novaSenha);
                Connection conn = DB.getConnection();
                UsuarioDao usuarioDao = DaoFactory.createUsuarioDao(conn);
                usuarioDao.update(usuarioLogado);
                break;
            case "N":
            case "n":
                System.out.println("Nenhuma Informção foi alterada.");
                break;

        }
    }
}
