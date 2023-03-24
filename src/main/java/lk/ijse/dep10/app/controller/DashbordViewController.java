package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
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
        Stage stage = new Stage();
        stage.initModality( Modality.APPLICATION_MODAL );
        stage.setTitle( "Customers Management" );
        stage.setScene( scene );
        stage.show();
        stage.centerOnScreen();

    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        Scene scene = new Scene( new FXMLLoader( getClass().getResource( "/view/EmployeeView.fxml" ) ).load() );
        Stage stage = new Stage();
        stage.initModality( Modality.APPLICATION_MODAL );
        stage.setTitle( "Employees Management" );
        stage.setScene( scene );
        stage.show();
        stage.centerOnScreen();

    }

    @FXML
    void btnStudentOnAction(ActionEvent event) throws IOException {
        Scene scene = new Scene( new FXMLLoader( getClass().getResource( "/view/ManageStudentScene.fxml" ) ).load() );
        Stage stage = new Stage();
        stage.initModality( Modality.APPLICATION_MODAL );
        stage.setTitle( "Students Management" );
        stage.setScene( scene );
        stage.show();
        stage.centerOnScreen();

    }

    @FXML
    void btnTeacherOnAction(ActionEvent event) throws IOException {
        Scene scene = new Scene( new FXMLLoader( getClass().getResource( "/view/TeacherView.fxml" ) ).load() );
        Stage stage = new Stage();
        stage.initModality( Modality.APPLICATION_MODAL );
        stage.setTitle( "Teachers Management" );
        stage.setScene( scene );
        stage.show();
        stage.centerOnScreen();

    }

}