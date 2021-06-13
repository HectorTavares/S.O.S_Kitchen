package util;

public class FormatadorString {
    private FormatadorString() {
    }

    public static String formatarPalavra(String palavra) {
        return palavra.substring(0, 1).toUpperCase() + palavra.substring(1).toLowerCase();
    }
    public static String formatarFrase(String frase){
        String[]vetorArray;
            vetorArray=frase.split("\\.");
            for (int i = 0;i<vetorArray.length;i++){
                vetorArray[i]=FormatadorString.formatarPalavra(vetorArray[i]);
            }
            String resultado ="";
            for (int i = 0;i<vetorArray.length;i++){
                resultado =resultado+" \n"+vetorArray[i]+".";
            }
        return resultado;
    }

    public static StringBuilder removedorDeEspacosOr(StringBuilder palavra){
        int inicio = palavra.lastIndexOf("o");
        int termino = palavra.lastIndexOf("=");
        palavra.replace(inicio,termino+1,"");
        return palavra;
    }

    public static StringBuilder removedorDeEspacosAnd(StringBuilder palavra){
        int a = palavra.lastIndexOf("a");
        palavra.deleteCharAt(a);
        int n = palavra.lastIndexOf("n");
        palavra.deleteCharAt(n);
        int d = palavra.lastIndexOf("d");
        palavra.deleteCharAt(d);
        return palavra;
    }
}

