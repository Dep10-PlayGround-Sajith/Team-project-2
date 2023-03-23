package lk.ijse.dep10.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.dep10.app.db.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch( args );
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene = new Scene( new FXMLLoader( getClass().getResource( "/view/DashbordView.fxml" ) ).load() );
        primaryStage.setScene( scene );
        primaryStage.setTitle( "Dashboard" );
        primaryStage.show();
        primaryStage.centerOnScreen();




    }
}