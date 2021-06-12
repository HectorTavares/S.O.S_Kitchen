package exception;

public class NotaAvaliacao extends RuntimeException{
    public NotaAvaliacao(){
        super("Sua Nota Foi Maior do que 10 ou menor que 1, Coloque Outra nota! ");
    }
}
