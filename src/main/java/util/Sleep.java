package util;

public class Sleep {
    public static void sleep() throws InterruptedException {
        System.out.print(".");Thread.sleep(1000);
        System.out.print(".");Thread.sleep(1000);
        System.out.println(".");Thread.sleep(500);
    }
}
