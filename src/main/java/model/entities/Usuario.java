package model.entities;

import db.DB;
import model.dao.DaoFactory;
import model.dao.UsuarioDao;

import java.sql.Connection;

public class Usuario {
    protected int id;
    protected String nome;
    protected String senha;
    protected String login;

    public Usuario(int id, String nome, String senha, String login) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.login = login;
    }
    public Usuario(String nome, String senha , String login){
        this.setNome(nome);
        this.setSenha(senha);
        this.setLogin(login);
    }
    public Usuario(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id_usuario) {
        this.id = id_usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public static boolean consegueLogar(String login, String senha){
        Connection conn = DB.getConnection();
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao(conn);
        Usuario usuario = usuarioDao.findByLoginAndSenha(login, senha);

        return usuario != null;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", login='" + login + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        if (id != usuario.id) return false;
        if (!nome.equals(usuario.nome)) return false;
        if (!senha.equals(usuario.senha)) return false;
        return login.equals(usuario.login);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nome.hashCode();
        result = 31 * result + senha.hashCode();
        result = 31 * result + login.hashCode();
        return result;
    }

}
