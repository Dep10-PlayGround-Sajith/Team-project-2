package lk.ijse.dep10.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
<<<<<<< HEAD

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);

=======
import lk.ijse.dep10.app.db.DBConnection;

import java.io.IOException;
import java.sql.SQLException;

public class AppInitializer extends Application {

    public static void main(String[] args) throws SQLException {
        launch(args);
        DBConnection.getInstance().getConnection().close();
>>>>>>> refs/remotes/origin/main
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
<<<<<<< HEAD
        primaryStage.setScene(new Scene(new FXMLLoader(getClass().getResource("/view/EmployeeView.fxml")).load()));
        primaryStage.show();
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Employee Details");

    }
}
=======
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/TeacherView.fxml"))));
        primaryStage.setTitle("JDBC Assignment");
        primaryStage.show();
        primaryStage.centerOnScreen();
    }}
>>>>>>> refs/remotes/origin/main
