package lk.ijse.dep10.app.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.app.db.DBConnection;
import lk.ijse.dep10.app.model.Teacher;

import java.sql.*;
import java.time.LocalDate;

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
    public TableView<Teacher> tblteachers;


    public void initialize() {


        tblteachers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblteachers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblteachers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        loadAllTeachers();
        tblteachers.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            btnDelete.setDisable(current == null);
            if (current == null) return;
            txtID.setText(current.getId() + "");
            txtName.setText(current.getName());
            txtAddress.setText(current.getAddress());


        });


    }

    private void loadAllTeachers() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT  * FROM Teacher");
            ObservableList<Teacher> TeacherList = tblteachers.getItems();

            while (rst.next()) {
                String id = rst.getString("id");
                String name = rst.getString("name");

                String address = rst.getString("address");

                TeacherList.add(new Teacher(id, name, address));
            }


            Platform.runLater(btnNewTeacher::fire);


        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load details").showAndWait();
            Platform.exit();

        }
    }


    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Statement stm = connection.createStatement();
            String sql = "DELETE FROM Teacher WHERE name = '%s'";
            sql = String.format(sql, tblteachers.getSelectionModel().getSelectedItem().getName());
            stm.executeUpdate(sql);

            tblteachers.getItems().remove(tblteachers.getSelectionModel().getSelectedItem());

            if (tblteachers.getItems().isEmpty()) btnNewTeacher.fire();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to delete details").show();
        }
    }


    @FXML
    public void btnNewTeacherOnAction(ActionEvent event) {
        ObservableList<Teacher> TeacherList = tblteachers.getItems();

        System.out.println(TeacherList.size());
        String str = txtID.getText();


        String text = String.format("T00%d", (TeacherList.size() + 1));


        txtID.setText(text + "");
        txtName.clear();
        txtAddress.clear();

        tblteachers.getSelectionModel().clearSelection();

        txtName.requestFocus();

    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) {
        if (!isDataValid()) return;

        try {
            Teacher teacher = new Teacher(txtID.getText(), txtName.getText(), txtAddress.getText());
            Connection connection = DBConnection.getInstance().getConnection();


            Statement stm = connection.createStatement();  //regular statement
            Teacher selectedTeacher = tblteachers.getSelectionModel().getSelectedItem();
            if (selectedTeacher == null) {


                String sql = "INSERT INTO Teacher VALUES ('%s','%s', '%s')";
                sql = String.format(sql, teacher.getId(), teacher.getName(), teacher.getAddress());
                stm.executeUpdate(sql);

            } else {
                String sql = "UPDATE Teacher SET name='%s', address ='%s' WHERE id=%d ";

                sql = String.format(sql, teacher.getId(), teacher.getName(), teacher.getAddress());
                stm.executeUpdate(sql);


                ObservableList<Teacher> teacherlist = tblteachers.getItems();
                int selectedteacherIndex = teacherlist.indexOf(selectedTeacher);
                teacherlist.set(selectedteacherIndex, teacher);
                tblteachers.refresh();


            }
            tblteachers.getItems().add(teacher);
            btnNewTeacher.fire();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save details").showAndWait();
        }


    }

    private boolean isDataValid() {
        boolean isDataValid = true;
        for (Node node : new Node[]{txtName, txtAddress}) {
            node.getStyleClass().remove("invalid");
        }


        String Name = txtName.getText();

        String address = txtAddress.getText();


        if (address.strip().length() < 3) {
            isDataValid = false;
            txtAddress.requestFocus();
            txtAddress.selectAll();
            txtAddress.getStyleClass().add("invalid");

        }


        if (!Name.matches("[A-Za-z ]+")) {
            isDataValid = false;
            txtName.requestFocus();
            txtName.selectAll();
            txtName.getStyleClass().add("invalid");

        }
        return isDataValid;
    }


}
