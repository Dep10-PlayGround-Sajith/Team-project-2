package lk.ijse.dep10.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.dep10.app.db.DBConnection;

import java.io.IOException;
import java.sql.SQLException;

public class AppInitializer extends Application {

    public static void main(String[] args) throws SQLException {
        launch(args);
        DBConnection.getInstance().getConnection().close();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(new FXMLLoader(getClass().getResource("/view/EmployeeView.fxml")).load()));
        primaryStage.show();
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Employee Details");

        try {
            Scene scene = new Scene(new FXMLLoader().load(getClass().getResource("/view/ManageStudentScene.fxml")));
            primaryStage.setScene(scene);
            primaryStage.setTitle("Student Add");
            primaryStage.show();
            primaryStage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FXML LOADR error");
        }
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/TeacherView.fxml"))));
        primaryStage.setTitle("JDBC Assignment");
        primaryStage.show();
        primaryStage.centerOnScreen();

    }

}