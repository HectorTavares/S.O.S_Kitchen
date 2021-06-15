package model.dao.impl;

import model.dao.ReceitaDao;
import db.DB;
import db.DbException;
import model.entities.Administrador;
import model.entities.Ingrediente;
import model.entities.Receita;
import model.entities.Usuario;
import util.ConversaoDeData;
import util.FormatadorString;
import util.VerificadorDeRepeticao;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class ReceitaDaoJDBC implements ReceitaDao {
    private final Connection conn;

    public ReceitaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    private Usuario instantiateUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id_usuario"));
        usuario.setNome(rs.getString("nomeUsuario"));
        usuario.setLogin(rs.getString("loginUsuario"));
        usuario.setSenha(rs.getString("senhaUsuario"));
        return usuario;
    }

    private Receita instantiateReceita(ResultSet rs, Administrador adm, Usuario usuario) throws SQLException {
        Receita obj = new Receita();
        obj.setIdReceita(rs.getInt("id_receita"));
        obj.setNome(rs.getString("nome"));
        obj.setTempoPreparo(rs.getString("tempo_preparo"));
        obj.setSequenciaPreparo(rs.getString("sequencia_preparo"));
        LocalDate cadastro = ConversaoDeData.dateToLocalDate(rs.getDate("data_cadastro"));
        LocalDate edicao = ConversaoDeData.dateToLocalDate(rs.getDate("data_edicao"));
        obj.setData_Edicao(edicao);
        obj.setData_Cadastro(cadastro);
        obj.setAutor(usuario);
        obj.setConcessorDePermissao(adm);
        return obj;
    }

    private Administrador instantiateAdministrador(ResultSet rs) throws SQLException {
        Administrador adm = new Administrador();
        adm.setId(rs.getInt("id_adm"));
        adm.setNome(rs.getString("nomeAdm"));
        adm.setLogin(rs.getString("loginAdm"));
        adm.setSenha(rs.getString("senhaAdm"));
        return adm;
    }

    public Ingrediente instantiateIngrediente(ResultSet rs) throws SQLException {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setIdIngrediente(rs.getInt("id_ingrediente"));
        ingrediente.setNome(rs.getString("nome"));
        return ingrediente;
    }

    @Override
    public void insert(Receita obj) {
        PreparedStatement st = null;
        PreparedStatement st2;
        try {
            st = conn.prepareStatement("INSERT INTO receita" +
                            "(nome,tempo_preparo,sequencia_preparo,data_cadastro,id_usuario,id_adm)" +
                            "VALUES " +
                            "(?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getNome());
            st.setString(2, obj.getTempoPreparo());
            st.setString(3, obj.getSequenciaPreparo());
            Date date = Date.valueOf(obj.getData_Cadastro());
            st.setDate(4, new java.sql.Date(date.getTime()));
            st.setInt(5, obj.getAutor().getId());
            st.setInt(6, obj.getConcessorDePermissao().getId());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setIdReceita(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected");
            }
            for (Ingrediente ingrediente : obj.getIngredientes()) {
                st2 = conn.prepareStatement("INSERT INTO receita_possui_ingrediente" +
                                "(id_ingrediente,id_receita)" +
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
    } //create


    @Override
    public Receita findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs;
        ResultSet rs2;
        PreparedStatement st2;
        try {
            st = conn.prepareStatement("SELECT r.* , u.nome as nomeUsuario , u.senha as senhaUsuario , " +
                    "u.login as loginUsuario , a.nome as nomeAdm , a.senha as senhaAdm , a.login as loginAdm " +
                    "FROM receita r INNER JOIN usuario u ON  r.id_usuario = u.id_usuario INNER JOIN administrador " +
                    "a ON r.id_adm = a.id_adm " +
                    "WHERE r.id_receita = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            Receita receita = new Receita();
            if (rs.next()) {
                Usuario usuario = instantiateUsuario(rs);
                Administrador adm = instantiateAdministrador(rs);
                receita = instantiateReceita(rs, adm, usuario);
            }
            st2 = conn.prepareStatement("SELECT * FROM receita_possui_ingrediente r INNER JOIN ingrediente i ON " +
                    "r.id_ingrediente = i.id_ingrediente WHERE r.id_receita = ?");
            st2.setInt(1, id);
            rs2 = st2.executeQuery();
            List<Ingrediente> lista = new ArrayList<>();
            while (rs2.next()) {
                lista.add(instantiateIngrediente(rs2));
            }
            receita.setIngredientes(lista);
            return receita;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    } //read

    @Override
    public void update(Receita obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE receita " +
                    "SET nome = ?, tempo_preparo = ?, sequencia_preparo =?, data_edicao=? " +
                    "WHERE id_usuario = ? AND id_receita = ? ");
            st.setString(1, obj.getNome());
            st.setString(2, obj.getTempoPreparo());
            st.setString(3, obj.getSequenciaPreparo());
            Date date = Date.valueOf(LocalDate.now());
            st.setDate(4, new Date(date.getTime()));
            st.setInt(5, obj.getAutor().getId());
            st.setInt(6,obj.getIdReceita());
            st.executeUpdate();
            System.out.println("Mudança concluída com sucesso!");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    } //update

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        PreparedStatement st2;
        PreparedStatement st3;
        try {
            st3 = conn.prepareStatement("DELETE FROM avaliacao WHERE id_receita = ?");
            st2 = conn.prepareStatement("DELETE FROM receita_possui_ingrediente WHERE id_receita = ?");
            st = conn.prepareStatement("DELETE FROM receita WHERE id_receita = ?");
            st.setInt(1, id);
            st2.setInt(1, id);
            st3.setInt(1, id);
            st3.executeUpdate();
            st2.executeUpdate();
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    } //delete


    @Override
    public List<Receita> findAll() {
        PreparedStatement st = null;
        ResultSet rs;
        List<Receita> listaReceitas = new ArrayList<>();
        try {
            st = conn.prepareStatement("SELECT id_receita FROM receita");
            rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_receita");
                listaReceitas.add(findById(id));
            }
            return listaReceitas;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    } //read


    @Override
    public List<Receita> findByAllIngredientes(List<Ingrediente> listaIngredientes) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Receita>listaReceitas = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM receita_possui_ingrediente p " +
                    "INNER JOIN receita r ON r.id_receita = p.id_receita" +
                    " INNER JOIN usuario u" +
                    " ON r.id_usuario = u.id_usuario WHERE p.id_ingrediente = ");

            for (Ingrediente ignored :listaIngredientes) {
                sql.append(" ? and ");
            }

            FormatadorString.removedorDeEspacosAnd(sql);

            st = conn.prepareStatement(sql.toString());
            int i = 1;

            for (Ingrediente ingrediente:listaIngredientes) {
               st.setInt(i,ingrediente.getIdIngrediente());
               i++;
            }

            rs = st.executeQuery();
            while (rs.next()){
                Receita receita = findById(rs.getInt("id_receita"));
                listaReceitas.add(receita);
            }

            return VerificadorDeRepeticao.verificarRepeticao(listaReceitas);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    } //read

    @Override
    public List<Receita> findByIngredientes(List<Ingrediente> listaIngredientes) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Receita>listaReceitas = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM receita_possui_ingrediente p " +
                    "INNER JOIN receita r ON r.id_receita = p.id_receita" +
                    " INNER JOIN usuario u" +
                    " ON r.id_usuario = u.id_usuario WHERE p.id_ingrediente = ");

            for (Ingrediente ignored :listaIngredientes) {
                sql.append(" ? or p.id_ingrediente =  ");
            }

            FormatadorString.removedorDeEspacosOr(sql);

            st = conn.prepareStatement(sql.toString());
            int i = 1;
            for (Ingrediente ingrediente:listaIngredientes) {
                st.setInt(i,ingrediente.getIdIngrediente());
                i++;
            }

            rs = st.executeQuery();
            while (rs.next()){
                Receita receita = findById(rs.getInt("id_receita"));
                listaReceitas.add(receita);
            }
            return VerificadorDeRepeticao.verificarRepeticao(listaReceitas);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Receita> findByName(String nomePesquisado) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Receita> listaReceitas = new ArrayList<>();
        PreparedStatement st2;
        ResultSet rs2;
        try {
            String pesquisa = '%'+nomePesquisado+'%';
            st = conn.prepareStatement("SELECT r.* , u.nome as nomeUsuario , u.senha as senhaUsuario ," +
                    " u.login as loginUsuario ,  a.nome as nomeAdm , a.senha as senhaAdm , a.login as loginAdm  " +
                    "FROM receita r INNER JOIN usuario u ON  r.id_usuario = u.id_usuario INNER JOIN administrador " +
                    "a ON r.id_adm = a.id_adm " +
                    "WHERE r.nome LIKE ? ");
            st.setString(1, pesquisa);
            rs = st.executeQuery();
            Receita receita;
            while (rs.next()) {
                int id_receita = rs.getInt("id_receita");
                Usuario usuario = instantiateUsuario(rs);
                Administrador adm = instantiateAdministrador(rs);
                receita = instantiateReceita(rs, adm, usuario);

                st2 = conn.prepareStatement("SELECT * FROM receita_possui_ingrediente r INNER JOIN ingrediente i ON " +
                        "r.id_ingrediente = i.id_ingrediente WHERE r.id_receita = ?");
                st2.setInt(1, id_receita);
                rs2 = st2.executeQuery();
                List<Ingrediente> listaIngrediente = new ArrayList<>();
                while (rs2.next()) {
                    listaIngrediente.add(instantiateIngrediente(rs2));
                }
                receita.setIngredientes(listaIngrediente);
                listaReceitas.add(receita);
            }
            return VerificadorDeRepeticao.verificarRepeticao(listaReceitas);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    } //read

    @Override
    public List<Receita> findAllByAutor(Usuario autor){
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Receita>listaReceitas = new ArrayList<>();
        try{
            st = conn.prepareStatement("SELECT id_receita FROM receita where id_usuario = ?");
            st.setInt(1,autor.getId());
            rs = st.executeQuery();
            rs = st.executeQuery();
            while (rs.next()){
                Receita receita = findById(rs.getInt("id_receita"));
                listaReceitas.add(receita);
            }
            return VerificadorDeRepeticao.verificarRepeticao(listaReceitas);
        }catch (SQLException e ){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
