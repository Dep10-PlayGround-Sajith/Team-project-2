package lk.ijse.dep10.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
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

    }
}
