package model.dao.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;
import model.dao.AvaliacaoDao;
import model.entities.Avaliacao;
import model.entities.Receita;


public class AvaliacaoDaoJDBC implements AvaliacaoDao {
    private Connection conn;

    public AvaliacaoDaoJDBC(Connection conn) {
        this.conn = conn;

    }

    @Override
    public void usuarioAvalia(Avaliacao obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO avaliacao " + "(nota, id_usuario, id_receita) " + "VALUES " + "(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, obj.getNota());
            st.setInt(2, obj.getAvaliador().getId());
            st.setInt(3,obj.getReceitaAvaliada().getIdReceita());
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
    public void administradorAvalia(Avaliacao obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO avaliacao " + "(nota, id_adm,id_receita) " + "VALUES " + "(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, obj.getNota());
            st.setInt(2, obj.getAvaliador().getId());
            st.setInt(3,obj.getReceitaAvaliada().getIdReceita());
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
    public void updateUsuario(Avaliacao obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE avaliacao SET Nota = ? WHERE id_usuario = ? and id_receita  = ?");
            st.setInt(1, obj.getNota());
            st.setInt(2, obj.getAvaliador().getId());
            st.setInt(3, obj.getReceitaAvaliada().getIdReceita());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void updateAdministrador(Avaliacao obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE avaliacao" + "SET Nota = ?" + "WHERE id_adm = ? and id_receita = ?");
            st.setInt(1, obj.getNota());
            st.setInt(2, obj.getAvaliador().getId());
            st.setInt(3, obj.getReceitaAvaliada().getIdReceita());
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
            st = conn.prepareStatement("DELETE FROM avaliacao WHERE id_avaliacao = ?");

            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Avaliacao findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM avaliacao WHERE id_avaliacao = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                return instantiateAvaliacao(rs);
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
    public double findMedia(Receita obj) {
        PreparedStatement st = null;
        ResultSet rs = null;
        double media = 0;
        try {
            st = conn.prepareStatement("SELECT AVG(nota) FROM avaliacao where id_receita = ? ");
            st.setInt(1, obj.getIdReceita());
            rs = st.executeQuery();
            if (rs.next()) {
                media = rs.getDouble(1);
                BigDecimal a = BigDecimal.valueOf(media).setScale(1, RoundingMode.HALF_EVEN);
                media = a.doubleValue();
            }
            return media;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    private Avaliacao instantiateAvaliacao(ResultSet rs) throws SQLException {
        Avaliacao obj = new Avaliacao();
        obj.setId(rs.getInt("id_usuario"));
        obj.setNota(rs.getInt("nota"));
        return obj;
    }

}
