package model.dao.impl;

import model.dao.AdministradorDao;
import db.DB;
import db.DbException;
import model.entities.Administrador;
import model.entities.Usuario;

import java.sql.*;

public class AdministadorDaoJDBC implements AdministradorDao {
    private final Connection conn;

    public AdministadorDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    private Administrador instantiateAdministrador(ResultSet rs) throws SQLException {
        Administrador adm = new Administrador();
        adm.setId(rs.getInt("id_adm"));
        adm.setNome(rs.getString("nome"));
        adm.setSenha(rs.getString("senha"));
        adm.setLogin(rs.getString("login"));
        return adm;
    }

    @Override
    public void insert(Administrador obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO administrador "
                            + "(nome, senha, login) "
                            + "VALUES "
                            + "(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getNome());
            st.setString(2, obj.getSenha());
            st.setString(3, obj.getLogin());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Administrador findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM administrador WHERE id_adm = ? ");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                return instantiateAdministrador(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void update(Usuario obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE administrador "
                            + "SET nome = ?, senha = ?, login = ?"
                            + "WHERE id_adm = ?");
            st.setString(1, obj.getNome());
            st.setString(2, obj.getSenha());
            st.setString(3, obj.getLogin());
            st.setInt(4, obj.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM administrador WHERE id_adm = ?");

            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }


    @Override
    public void analyzeRecipe() {


    }

    @Override
    public Administrador findByLoginAndSenha(String login, String senha) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM administrador WHERE login = ? AND senha  = ?");
            st.setString(1, login);
            st.setString(2, senha);
            rs = st.executeQuery();
            if (rs.next()) {
                return instantiateAdministrador(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
