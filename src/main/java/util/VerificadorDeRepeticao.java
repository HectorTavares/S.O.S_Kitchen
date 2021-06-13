package util;

import model.entities.Receita;

import java.util.*;
import java.util.stream.Collectors;

public class VerificadorDeRepeticao {
    public static List<Receita> verificarRepeticao(List<Receita>lista) {
        return lista.stream().distinct().collect(Collectors.toList());
    }
}
