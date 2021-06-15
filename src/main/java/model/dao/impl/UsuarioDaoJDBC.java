package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.dao.UsuarioDao;
import db.DB;
import db.DbException;
import model.entities.Usuario;

public class UsuarioDaoJDBC implements UsuarioDao{
    private Connection conn;

    public UsuarioDaoJDBC (Connection conn) {
        this.conn=conn;
    }


    @Override
    public void insert(Usuario obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO Usuario "
                            + "(nome, senha, login) "
                            + "VALUES "
                            + "(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getNome());
            st.setString(2, obj.getSenha());
            st.setString(3, obj.getLogin());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Usuario Cadastrado com sucesso!");
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }


    }

    @Override
    public void update(Usuario obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE usuario "
                            + "SET nome = ?, senha = ?, login = ?"
                            + "WHERE id_usuario = ?");
            st.setString(1, obj.getNome());
            st.setString(2, obj.getSenha());
            st.setString(3, obj.getLogin());
            st.setInt(4,obj.getId());
            st.executeUpdate();
            System.out.println("Dados atualizados com sucesso!");
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }


    }

    @Override
    public void desativeById(Integer id){
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE usuario SET ativo = false WHERE id_usuario = ?");

            st.setInt(1, id);

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM usuario WHERE id_usuario = ?");

            st.setInt(1, id);

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Usuario findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM usuario WHERE usuario.id_usuario = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {

                return instantiateUsuario(rs);
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Usuario instantiateUsuario(ResultSet rs) throws SQLException {
        Usuario obj = new Usuario();
        obj.setId(rs.getInt("id_usuario"));
        obj.setNome(rs.getString("nome"));
        obj.setLogin(rs.getString("login"));
        obj.setSenha(rs.getString("senha"));
        return obj;
    }

    public Usuario findByLoginAndSenha(String login, String senha){
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM usuario WHERE login = ? AND senha  = ? AND ativo = true;");
            st.setString(1, login);
            st.setString(2,senha);
            rs = st.executeQuery();
            if (rs.next()) {
                return instantiateUsuario(rs);
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
