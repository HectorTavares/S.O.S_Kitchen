package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SolicitacaoReceitaDao;
import model.entities.Administrador;
import model.entities.Ingrediente;
import model.entities.Receita;
import model.entities.Usuario;
import util.ConversaoDeData;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolicitacaoReceitaDaoJDBC implements SolicitacaoReceitaDao {
    private final Connection conn;

    public SolicitacaoReceitaDaoJDBC(Connection conn) {
        this.conn = conn;

    }

    public Ingrediente instantiateIngrediente(ResultSet rs) throws SQLException {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setIdIngrediente(rs.getInt("id_ingrediente"));
        ingrediente.setNome(rs.getString("nome"));
        return ingrediente;
    }

    private Receita instantiateSolicitacaoReceita(ResultSet rs, Usuario usuario) throws SQLException {
        Receita obj = new Receita();
        obj.setIdReceita(rs.getInt("id_solicitacao_receita"));
        obj.setNome(rs.getString("nome"));
        obj.setTempoPreparo(rs.getString("tempo_preparo"));
        obj.setSequenciaPreparo(rs.getString("sequencia_preparo"));
        LocalDate cadastro = ConversaoDeData.dateToLocalDate(rs.getDate("data_cadastro"));
        obj.setData_Cadastro(cadastro);
        obj.setAutor(usuario);
        return obj;
    }

    private Usuario instantiateUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("userId"));
        usuario.setNome(rs.getString("nomeUsuario"));
        usuario.setLogin(rs.getString("loginUsuario"));
        usuario.setSenha(rs.getString("senhaUsuario"));
        return usuario;
    }

    public void desativeSolicitacion(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE solicitacao_receita set status_solicitacao = false WHERE " +
                    " id_solicitacao_receita =  ? ");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void insert(Receita obj) {
        PreparedStatement st = null;
        PreparedStatement st2;
        try {
            st = conn.prepareStatement("INSERT INTO solicitacao_receita" +
                            "(nome,tempo_preparo,sequencia_preparo,data_cadastro,id_usuario,status_solicitacao)" +
                            "VALUES" +
                            "(?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getNome());
            st.setString(2, obj.getTempoPreparo());
            st.setString(3, obj.getSequenciaPreparo());
            Date date = ConversaoDeData.localDateToDate(obj.getData_Cadastro());
            assert date != null;
            st.setDate(4, new java.sql.Date(date.getTime()));
            st.setInt(5, obj.getAutor().getId());
            st.setBoolean(6, false);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setIdReceita(id);
                    System.out.println("Pedido Cadastrado com Sucesso!");
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected");
            }
            for (Ingrediente ingrediente : obj.getIngredientes()) {
                st2 = conn.prepareStatement("INSERT INTO ingrediente_da_solicitacao_receita" +
                                "(id_ingrediente,id_solicitacao_receita)" +
                                "VALUES " +
                                "(?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                st2.setInt(1, ingrediente.getIdIngrediente());
                st2.setInt(2, obj.getIdReceita());
                int rowsAffected2 = st2.executeUpdate();
                if (rowsAffected2 > 0) {
                    ResultSet rs = st2.getGeneratedKeys();
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        ingrediente.setIdIngrediente(id);
                    }
                    DB.closeResultSet(rs);
                } else {
                    throw new DbException("Unexpected error! No rows affected");
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Receita> findAll() {
        PreparedStatement st = null;
        PreparedStatement st2;
        ResultSet rs;
        ResultSet rs2;
        List<Receita> listaReceitas = new ArrayList<>();
        try {
            st = conn.prepareStatement("SELECT s.* , u.id_usuario as userId, u.nome as nomeUsuario , " +
                    " u.login as loginUsuario , u.senha as senhaUsuario " +
                    " FROM solicitacao_receita s INNER JOIN usuario u ON " +
                    " s.id_usuario = u.id_usuario WHERE status_solicitacao = true ");
            Map<Integer, Usuario> map = new HashMap<>();
            rs = st.executeQuery();
            while (rs.next()) {
                Usuario user = map.get(rs.getInt("userId"));
                if (user == null) {
                    user = instantiateUsuario(rs);
                    map.put(rs.getInt("userId"), user);
                }
                Receita receita = instantiateSolicitacaoReceita(rs, user);
                listaReceitas.add(receita);
                st2 = conn.prepareStatement("SELECT * FROM receita_possui_ingrediente r INNER JOIN ingrediente i ON " +
                        "r.id_ingrediente = i.id_ingrediente WHERE r.id_receita = ?");
                st2.setInt(1, receita.getIdReceita());
                rs2 = st2.executeQuery();
                List<Ingrediente> lista = new ArrayList<>();
                while (rs2.next()) {
                    lista.add(instantiateIngrediente(rs2));
                }
                receita.setIngredientes(lista);
            }

            return listaReceitas;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }


}
