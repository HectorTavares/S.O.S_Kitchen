package model.dao;

import model.entities.Receita;

import java.util.List;

public interface SolicitacaoReceitaDao {
    void insert(Receita obj);
    List<Receita>findAll();
    void desativeSolicitacion(Integer id);
}
