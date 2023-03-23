package lk.ijse.dep10.app.db;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection dbconnection;
    private final Connection connection;

    private DBConnection(){
        Properties configurations =new Properties();
        File confiFile = new File("application.properties");
        try {
            FileReader fr = new FileReader(confiFile);
            configurations.load(fr);
            fr.close();

            String host = configurations.getProperty("mysql.host", "localhost");
            String port = configurations.getProperty("mysql.port", "3306");
            String database = configurations.getProperty("mysql.database", "dep10_git");
            String username = configurations.getProperty("mysql.username", "root");
            String password = configurations.getProperty("mysql.password", "mysql");

            String queryString="createDatabaseIfNotExist=true&allowMultiQueries=true";
            String url=String.format("jdbc:mysql://%s:%s/%s?%s",host,port,database,queryString);
            connection= DriverManager.getConnection(url,username,password);

            //connection= DriverManager.getConnection("jdbc:mysql://dep10.lk:3306/dep10_git?createDatabaseIfNotExist=true&allowMultiQueries=true","root","mysql");
        } catch (FileNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,"Configuration file does not exists").showAndWait();
            System.exit(1);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to obtained a database connection,try again.if the problem persist contact developer").showAndWait();
            throw new RuntimeException(e);
        } catch (IOException e) {
            new Alert( Alert.AlertType.ERROR,"Failed to read Configurations").showAndWait();
            throw new RuntimeException(e);
        }
    }
    public static DBConnection getInstance(){
        return (dbconnection==null)? dbconnection=new DBConnection():dbconnection;
    }
    public Connection getConnection(){return connection;}
}