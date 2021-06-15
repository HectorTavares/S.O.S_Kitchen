import org.junit.Test;
import util.FormatadorString;
import view.TelaLogin;

import java.util.Scanner;

public class test {
    @Test
    public void testa(){
        System.out.println("a");
        Scanner a = new Scanner(System.in);
        int b= a.nextInt();
    }
    @Test
    public void testaMetodo(){

    }
    @Test
    public void testeFormatacao(){
        String a = "colocaR Na Panela DE PRESSao.junto DO ALHO.ACUCAR";
        System.out.println(FormatadorString.formatarFrase(a));
    }
}
