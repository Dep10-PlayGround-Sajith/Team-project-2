package lk.ijse.dep10.app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
<<<<<<< HEAD
    public static DBConnection instance;
    private final Connection connection;

    private DBConnection(){
        try {
            connection= DriverManager.getConnection("jdbc:mysql://dep10.lk:3306/dep10_git", "root", "mysql");
=======
    private static DBConnection dbConnection;
    private final Connection connection;

    private DBConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://dep10.lk/dep10_git", "root", "mysql");
>>>>>>> refs/remotes/origin/main

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
<<<<<<< HEAD

    }

    public static DBConnection getInstance(){
        return (instance==null) ? instance=new DBConnection():instance;
    }

    public Connection getConnection(){
        return connection;
    }
}


=======
    }

    public static DBConnection getInstance() {
        return (dbConnection == null) ? dbConnection = new DBConnection() : dbConnection;

    }

    public Connection getConnection() {
        return connection;
    }
}
>>>>>>> refs/remotes/origin/main
