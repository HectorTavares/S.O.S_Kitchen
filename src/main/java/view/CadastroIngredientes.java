package view;

import db.DB;
import model.dao.DaoFactory;
import model.dao.IngredienteDao;
import model.entities.Ingrediente;

import java.sql.Connection;
import java.util.Scanner;

public class CadastroIngredientes {
    private final IngredienteDao ingredienteDao;

    public CadastroIngredientes() {
        Connection conn = DB.getConnection();
        this.ingredienteDao = DaoFactory.createIngredienteDao(conn);
    }

    public void cadastrarIngredientes() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Digite o Nome do Ingrediente do qual deseja cadastrar: ");
        String novoIngrediente = teclado.nextLine();
        Ingrediente ingrediente = new Ingrediente(novoIngrediente);
        ingredienteDao.insert(ingrediente);
    }
}
