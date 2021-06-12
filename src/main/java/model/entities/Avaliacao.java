package model.entities;

import exception.NotaAvaliacao;

public class Avaliacao {
    private int nota;
    private int id;
    private Usuario avaliador;
    private Receita receitaAvaliada;

    public Avaliacao () {

    }
    public Avaliacao(int nota, Usuario avaliador, Receita receitaAvaliada) {
        checkNota(nota);
        this.nota = nota;
        this.avaliador=avaliador;
        this.receitaAvaliada=receitaAvaliada;
    }

    public void checkNota(int nota){
        if(nota>10 || nota<1){
            throw new NotaAvaliacao();
        }
    }

    public Usuario getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(Usuario avaliador) {
        this.avaliador = avaliador;
    }

    public Receita getReceitaAvaliada() {
        return receitaAvaliada;
    }

    public void setReceitaAvaliada(Receita receitaAvaliada) {
        this.receitaAvaliada = receitaAvaliada;
    }

    public int getNota() {
        return this.nota;
    }

    public void setNota(int nota) {
        checkNota(nota);
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id_usuario) {
        this.id = id_usuario;
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "nota=" + nota +
                ", id=" + id +
                ", avaliador=" + avaliador +
                ", receitaAvaliada=" + receitaAvaliada +
                '}';
    }
}