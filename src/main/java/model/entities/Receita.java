package model.entities;

import db.DB;
import model.dao.AvaliacaoDao;
import model.dao.DaoFactory;
import util.FormatadorString;
import view.MenuInicial;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class Receita {
    private int idReceita;
    private String nome;
    private String tempoPreparo;
    private String sequenciaPreparo;
    private LocalDate data_Cadastro;
    private LocalDate data_Edicao;
    private Usuario autor;
    private Administrador concessorDePermissao;
    private List<Ingrediente> ingredientes;
    private double avalicaoMedia;

    //construtor sem o id
    public Receita(String nome, String tempoPreparo, String sequenciaPreparo, LocalDate data_Cadastro, Usuario autor, Administrador concessorDePermissao, List<Ingrediente> ingredientes, double avalicaoMedia) {
        this.nome = FormatadorString.formatarPalavra(nome);
        this.tempoPreparo = tempoPreparo;
        this.sequenciaPreparo =FormatadorString.formatarPalavra(sequenciaPreparo);
        this.data_Cadastro = data_Cadastro;
        this.autor = autor;
        this.concessorDePermissao = concessorDePermissao;
        this.ingredientes = ingredientes;
        this.avalicaoMedia = avalicaoMedia;
    }

    //construtor com o id
    public Receita(int idReceita, String nome, String tempoPreparo, String sequenciaPreparo, LocalDate data_Cadastro, Usuario autor, Administrador concessorDePermissao, List<Ingrediente> ingredientes, double avalicaoMedia) {
        this.idReceita = idReceita;
        this.nome = FormatadorString.formatarPalavra(nome);
        this.tempoPreparo = tempoPreparo;
        this.sequenciaPreparo = FormatadorString.formatarFrase(sequenciaPreparo);
        this.data_Cadastro = data_Cadastro;
        this.autor = autor;
        this.concessorDePermissao = concessorDePermissao;
        this.ingredientes = ingredientes;
        this.avalicaoMedia = avalicaoMedia;
    }

    //usada para cadastrar receitas na tabela de solicitacao de receitas
    public Receita(int idReceita, String nome, String tempoPreparo, String sequenciaPreparo, LocalDate data_Cadastro, Usuario autor, List<Ingrediente> ingredientes) {
        this.idReceita = idReceita;
        this.nome = FormatadorString.formatarPalavra(nome);
        this.tempoPreparo = tempoPreparo;
        this.sequenciaPreparo = FormatadorString.formatarFrase(sequenciaPreparo);
        this.data_Cadastro = data_Cadastro;
        this.autor = autor;
        this.ingredientes = ingredientes;
    }

    public Receita(int idReceita, String nome, String tempoPreparo, String sequenciaPreparo, LocalDate data_Cadastro, Usuario autor, Administrador concessorDePermissao, List<Ingrediente> ingredientes) {
        this.idReceita = idReceita;
        this.nome = FormatadorString.formatarPalavra(nome);
        this.tempoPreparo =tempoPreparo;
        this.sequenciaPreparo = FormatadorString.formatarFrase(sequenciaPreparo);
        this.data_Cadastro = data_Cadastro;
        this.autor = autor;
        this.concessorDePermissao = concessorDePermissao;
        this.ingredientes = ingredientes;
    }

    public Receita(int idReceita) {
        this.idReceita = idReceita;
    }

    public Receita() {
        super();
    }


    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Administrador getConcessorDePermissao() {
        return concessorDePermissao;
    }

    public void setConcessorDePermissao(Administrador concessorDePermissao) {
        this.concessorDePermissao = concessorDePermissao;
    }

    public LocalDate getData_Cadastro() {
        return data_Cadastro;
    }

    public void setData_Cadastro(LocalDate data_Cadastro) {
        this.data_Cadastro = data_Cadastro;
    }

    public LocalDate getData_Edicao() {
        return data_Edicao;
    }

    public void setData_Edicao(LocalDate data_Edicao) {
        this.data_Edicao = data_Edicao;
    }

    public int getIdReceita() {
        return idReceita;
    }

    public void setIdReceita(int idReceita) {
        this.idReceita = idReceita;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = FormatadorString.formatarPalavra(nome);
    }

    public String getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(String tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public String getSequenciaPreparo() {
        return sequenciaPreparo;
    }

    public void setSequenciaPreparo(String sequenciaPreparo) {
        this.sequenciaPreparo = FormatadorString.formatarFrase(sequenciaPreparo);
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public double getAvalicaoMedia() {
        return avalicaoMedia;
    }



    public void setAvalicaoMedia(){
        Connection conn = DB.getConnection();
        AvaliacaoDao avaliacaoDao = DaoFactory.createAvaliacaoDao(conn);
        Receita receita = new Receita();
        receita.setIdReceita(idReceita);
        this.avalicaoMedia = avaliacaoDao.findMedia(receita);
    }

    public LocalDate ultimaModificacao(){
        if (data_Edicao!=null){
            return data_Edicao;
        }
        return data_Cadastro;
    }

    public String imprimir(){
        return "Nome da Receita : "+ nome +"\n"+
                "Ultima modificação :"+ ultimaModificacao()+"\n"+
                "Sequencia de preparo : "+ sequenciaPreparo +"\n"+
                "Tempo de preparo : "+tempoPreparo+"\n"+
                "Ingredientes : "+ ingredientes +"\n"+
                "Avaliação média : "+avalicaoMedia +"\n"+
                "Autor : " + autor.nome;
    }

    @Override
    public String toString() {
        setAvalicaoMedia();
        return nome + " \n" +
                "Autor : "+ autor.nome+ "\n"+
                "Avaliação Média : "+ avalicaoMedia +" \n";
    }
}
