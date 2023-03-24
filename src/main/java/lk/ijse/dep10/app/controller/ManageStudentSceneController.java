package lk.ijse.dep10.app.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep10.app.db.DBConnection;
import lk.ijse.dep10.app.model.Student;

import java.sql.*;

public class ManageStudentSceneController {

    public Button btnSave;
    public Button btnDelete;
    public AnchorPane root;
    @FXML
    private Button btnNewStudent;

    @FXML
    private TableView<Student> tblStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    public void initialize() {

        btnDelete.setDisable(true);
        btnSave.setDisable(true);
        txtName.setDisable(true);
        txtAddress.setDisable(true);

//        txtSearch.textProperty().addListener((value,previous,current)->{
//            Connection connection= DBConnection.getInstance().getConnection();
//            try {
//                Statement stm=connection.createStatement();
//                String sql="SELECT * FROM Student WHERE first_name LIKE '%1$s' OR last_name LIKE '%1$s'";
//                sql = String.format(sql,"%"+current+"%");
//                ResultSet rst=stm.executeQuery(sql);
//
//                ObservableList<Student> studentList=tblStudents.getItems();
//                studentList.clear();
//                while (rst.next()){
//                    int id=rst.getInt("id");
//                    String firstName=rst.getString("first_name");
//                    String lastName=rst.getString("last_name");
//                    String address=rst.getString("address");
//                    Gender gender=Gender.valueOf(rst.getString("gender"));
//                    LocalDate dob=rst.getDate("dob").toLocalDate();
//                    studentList.add(new Student(id,firstName,lastName,address,gender,dob));
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        });
        /* Column mapping */
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));


        loadAllStudents();

        /* Setup keyboard shortcuts */
        Platform.runLater(() -> {
            root.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_ANY), () -> btnNewStudent.fire());
            root.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_ANY), () -> btnSave.fire());
            root.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.F1), () -> tblStudent.requestFocus());
        });

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observableValue, student, current) -> {
            btnDelete.setDisable(current==null);
            if (current==null)return;

            txtId.setText(current.getId()+"");
            txtName.setText(current.getName());
            txtAddress.setText(current.getAddress());
        });
    }
    private void loadAllStudents(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm=connection.createStatement();
            ResultSet rst=stm.executeQuery("SELECT * FROM Student");
            ObservableList<Student> studentList=tblStudent.getItems();


            while (rst.next()){
                String id=rst.getString("id");
                String name=rst.getString("name");
                String address=rst.getString("address");


                studentList.add(new Student(id, name, address));
            }

            Platform.runLater(()-> btnNewStudent.fire());

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to load Student details").showAndWait();
            Platform.exit();
        }
    }
    private boolean isDataValid(){
        boolean isDataValid=true;
        for(Node node:new Node[]{txtName,txtAddress,txtId}){
            node.getStyleClass().remove("invalid");
        }
        String name=txtName.getText( );
        String address=txtAddress.getText( );

        if (address.strip().length()<3){
            isDataValid=false;
            txtAddress.requestFocus();
            txtAddress.selectAll();
            txtAddress.getStyleClass().add("invalid");
        }

        if (!name.strip().matches("[A-Za-z .]+")){
            isDataValid=false;
            txtName.getStyleClass().add("invalid");
            txtName.selectAll();
            txtName.requestFocus();

        }
        return isDataValid;
    }


    @FXML
    void btnNewStudentOnAction(ActionEvent event) {
        ObservableList<Student> studentList=tblStudent.getItems();
        String newId=(studentList.isEmpty()? "S001":String.format("S%03d",Integer.parseInt(studentList.get(studentList.size()-1).getId().substring(1))+1));

        txtAddress.setDisable(false);
        txtName.setDisable(false);
        txtId.setText(newId+"");
        txtName.clear();
        txtAddress.clear();
        btnSave.setDisable(false);
        btnDelete.setDisable(false);

        tblStudent.getSelectionModel().clearSelection();

        txtName.requestFocus();
    }

    public void btnDeleteOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm=connection.createStatement();
            String sql="DELETE FROM Student WHERE id='%s'";
            sql=String.format(sql,tblStudent.getSelectionModel().getSelectedItem().getId());
            stm.executeUpdate(sql);
            tblStudent.getItems().remove(tblStudent.getSelectionModel().getSelectedItem());
            if (tblStudent.getItems().isEmpty()) btnNewStudent.fire();

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to Delete the Student,try Again!").show();
        }
    }

    public void btnSaveOnAction(ActionEvent event) {
        if (!isDataValid())return;
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Student student = new Student(txtId.getText(), txtName.getText(),txtAddress.getText());

            Statement stm = connection.createStatement();

            Student selectedStudent=tblStudent.getSelectionModel().getSelectedItem();

            if (selectedStudent==null) {
                String sql="INSERT INTO Student VALUES('%s','%s','%s')";
                sql = String.format(sql, student.getId(), student.getName(), student.getAddress());
                stm.executeUpdate(sql);
                tblStudent.getItems().add(student);

            }else {
                String sql ="UPDATE Student SET name='%s',address='%s' WHERE id=%d";
                sql=String.format(sql,student.getName(),student.getAddress(),student.getId());
                stm.executeUpdate(sql);

                ObservableList<Student> studentList=tblStudent.getItems();
                int selectedStudentIndex=studentList.indexOf(selectedStudent);
                studentList.set(selectedStudentIndex, student);
                tblStudent.refresh();

            }
            btnNewStudent.fire();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to save Student details").showAndWait();
            Platform.exit();
        }
    }
}
