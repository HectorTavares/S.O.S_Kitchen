package model.entities;

import util.FormatadorString;

public class Ingrediente {
    private int idIngrediente;
    private String nome;

    public Ingrediente(int idIngrediente, String nome) {
        this.idIngrediente = idIngrediente;
        this.nome = FormatadorString.formatarPalavra(nome);
    }
    public Ingrediente(){

    }

    public Ingrediente(String nome) {
        this.nome=nome;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }


    public void setNome(String nome) {
        this.nome = FormatadorString.formatarPalavra(nome);
    }



    public String getNome() {
        return nome;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingrediente that = (Ingrediente) o;

        if (idIngrediente != that.idIngrediente) return false;
        return nome.equals(that.nome);
    }

    @Override
    public int hashCode() {
        int result = idIngrediente;
        result = 31 * result + nome.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return nome;
    }

}


