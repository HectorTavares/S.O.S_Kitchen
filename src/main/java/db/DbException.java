package db;

public class DbException extends RuntimeException{

    /**
     * Default serial id
     */
    private static final long serialVersionUID = 1L;

    public DbException(String msg) {
        super(msg);
    }


}