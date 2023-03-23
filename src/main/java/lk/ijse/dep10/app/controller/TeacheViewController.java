package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TeacheViewController {

    @FXML
    public Button btnDelete;

    @FXML
    public Button btnNewTeacher;

    @FXML
    public Button btnSave;

    @FXML
    public TableColumn<?, ?> tblTeachers;

    @FXML
    public TextField txtAddress;

    @FXML
    public TextField txtID;

    @FXML
    public TextField txtName;
    public TableView tblteachers;

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    public void btnNewTeacherOnAction(ActionEvent event) {

    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) {

    }

}
