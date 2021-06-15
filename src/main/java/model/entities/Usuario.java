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
    protected NivelAcesso nivelAcesso;

    public Usuario(int id, String nome, String senha, String login) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.login = login;
        this.nivelAcesso = NivelAcesso.USUARIO;
    }
    public Usuario(String nome, String senha , String login){
        this.setNome(nome);
        this.setSenha(senha);
        this.setLogin(login);
        this.nivelAcesso = NivelAcesso.USUARIO;
    }
    public Usuario(){
        this.nivelAcesso = NivelAcesso.USUARIO;
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

    public NivelAcesso getNivelAcesso() {
        return nivelAcesso;
    }

    protected void setNivelAcesso(NivelAcesso nivelAcesso){
        this.nivelAcesso = nivelAcesso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        if (id != usuario.id) return false;
        if (nome != null ? !nome.equals(usuario.nome) : usuario.nome != null) return false;
        if (senha != null ? !senha.equals(usuario.senha) : usuario.senha != null) return false;
        if (login != null ? !login.equals(usuario.login) : usuario.login != null) return false;
        return nivelAcesso == usuario.nivelAcesso;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (senha != null ? senha.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (nivelAcesso != null ? nivelAcesso.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", login='" + login + '\'' +
                ", nivelAcesso=" + nivelAcesso +
                '}';
    }
}
