package model.dao.impl;

import model.dao.IngredienteDao;
import db.DB;
import db.DbException;
import model.entities.Ingrediente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredienteDaoJDBC implements IngredienteDao {
    private final Connection conn;

    public IngredienteDaoJDBC(Connection conn) {
        this.conn=conn;
    }


    public Ingrediente instantiateIngrediente(ResultSet rs) throws SQLException {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setIdIngrediente(rs.getInt("id_ingrediente"));
        ingrediente.setNome(rs.getString("nome"));
        return ingrediente;
    }

    @Override
    public void insert(Ingrediente obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO ingrediente "
                            + "(nome) "
                            + "VALUES "
                            + "(?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getNome());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    System.out.println("Ingrediente Cadastrado com sucesso!");
                    int id = rs.getInt(1);
                    obj.setIdIngrediente(id);
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
    public void update(Ingrediente obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE ingrediente "
                            + "SET nome = ?"
                            + "WHERE id_ingrediente = ?");
            st.setString(1, obj.getNome());
            st.setInt(2,obj.getIdIngrediente());
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
            st = conn.prepareStatement("DELETE FROM ingrediente WHERE id_ingrediente = ?");
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
    public List<Ingrediente> findAll() {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("SELECT * FROM ingrediente ORDER BY nome");
            ResultSet rs = st.executeQuery();
            List<Ingrediente> lista = new ArrayList<>();
            while (rs.next()){
                Ingrediente ingrediente = instantiateIngrediente(rs);
                lista.add(ingrediente);
            }
            return lista;
        } catch (SQLException e) {
            throw  new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Ingrediente findById(Integer id) {
        PreparedStatement st;
        ResultSet rs;
        try{
            st= conn.prepareStatement("SELECT*FROM ingrediente WHERE id_ingrediente = ?");
            st.setInt(1,id);
            rs = st.executeQuery();
            if (rs.next()) {
                return instantiateIngrediente(rs);
            }
            return null;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
    }
}
