package model.dao;

import model.entities.Avaliacao;
import model.entities.Receita;
import model.entities.Usuario;

public interface AvaliacaoDao {

    void usuarioAvalia(Avaliacao obj);
    void administradorAvalia(Avaliacao obj);
    void updateUsuario(Avaliacao obj);
    void updateAdministrador(Avaliacao obj);
    void deleteById(Integer id);
    Avaliacao findById(Integer id);
    double findMedia(Receita obj);
    boolean jaAvaliou(Receita receitaAvaliada, Usuario Avaliador);
    double findNotaByReceitaAndAvaliador(Receita receitaAvaliada,Usuario avaliador);
}

