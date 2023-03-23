package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashbordViewController {

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnEmployee;

    @FXML
    private Button btnStudent;

    @FXML
    private Button btnTeacher;

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        Scene scene = new Scene( new FXMLLoader( getClass().getResource( "/view/CustomerView.fxml" ) ).load() );
        Stage stage = (Stage) btnCustomer.getScene().getWindow();
        stage.setTitle( "Customer Management" );
        stage.setScene( scene );
        stage.show();
        stage.centerOnScreen();

    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        Scene scene = new Scene( new FXMLLoader( getClass().getResource( "/view/StudentView.fxml" ) ).load() );
        Stage stage = (Stage) btnCustomer.getScene().getWindow();
        stage.setTitle( "Student Management" );
        stage.setScene( scene );
        stage.show();
        stage.centerOnScreen();

    }

    @FXML
    void btnStudentOnAction(ActionEvent event) {

    }

    @FXML
    void btnTeacherOnAction(ActionEvent event) {

    }

}