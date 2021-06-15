package util;

public class FormatadorString {
    private FormatadorString() {
    }

    public static String formatarPalavra(String palavra) {
        return palavra.substring(0, 1).toUpperCase() + palavra.substring(1).toLowerCase();
    }

    public static String formatarFrase(String frase) {
        String[] vetorArray;
        vetorArray = frase.split("\\.");
        for (int i = 0; i < vetorArray.length; i++) {
            vetorArray[i] = FormatadorString.formatarPalavra(vetorArray[i]);
        }
        StringBuilder resultado = new StringBuilder();
        for (String s : vetorArray) {
            resultado.append(" \n").append(s).append(".");
        }
        return resultado.toString();
    }

    public static void removedorDeEspacosOr(StringBuilder palavra) {
        int inicio = palavra.lastIndexOf("o");
        int termino = palavra.lastIndexOf("=");
        palavra.replace(inicio, termino + 1, "");
    }

    public static void removedorDeEspacosAnd(StringBuilder palavra) {
        int a = palavra.lastIndexOf("a");
        palavra.deleteCharAt(a);
        int n = palavra.lastIndexOf("n");
        palavra.deleteCharAt(n);
        int d = palavra.lastIndexOf("d");
        palavra.deleteCharAt(d);
    }
}

