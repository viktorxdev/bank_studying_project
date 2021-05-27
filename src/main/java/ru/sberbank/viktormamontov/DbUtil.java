package ru.sberbank.viktormamontov;


public class DbUtil {

    public static final String JDBC_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:~/bank_db";
    public static final String USER = "user";
    public static final String PASS = "user";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
