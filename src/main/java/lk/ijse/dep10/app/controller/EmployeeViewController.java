package lk.ijse.dep10.app.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.app.db.DBConnection;
import lk.ijse.dep10.app.model.Employee;

import java.sql.*;

public class EmployeeViewController {

    @FXML
    private Button btnDelete;

    @FXML
    public Button btnNewEmployee;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<Employee> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label txtTittle;

    public void initialize() {

        tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblEmployee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        loadAllEmployees();
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            btnDelete.setDisable(current == null);
            if (current == null) return;
            txtId.setText(current.getId() + "");
            txtName.setText(current.getName());
            txtAddress.setText(current.getAddress());
            btnSave.setDisable(true);

        });
    }

    private void loadAllEmployees() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT  * FROM Employee");
            ObservableList<Employee> TeacherList = tblEmployee.getItems();

            while (rst.next()) {
                String id = rst.getString("id");
                String name = rst.getString("name");

                String address = rst.getString("address");

                TeacherList.add(new Employee(id, name, address));
            }
            Platform.runLater(btnNewEmployee::fire);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load details").showAndWait();
            Platform.exit();
        }
    }


    @FXML
    void btnNewEmployeeOnAction(ActionEvent event) {
        btnSave.setDisable(false);

        ObservableList<Employee> employeeList = tblEmployee.getItems();
        String str = txtId.getText();
        String text = String.format("E00%d", (employeeList.size() + 1));

        txtId.setText(text + "");
        txtName.clear();
        txtAddress.clear();

        tblEmployee.getSelectionModel().clearSelection();
        txtName.requestFocus();

        txtId.setText(text);
        txtName.clear();
        txtAddress.clear();
        tblEmployee.getSelectionModel().clearSelection();
        txtName.requestFocus();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (!isDataValid()) return;

        try {
            Employee employee = new Employee(txtId.getText(), txtName.getText(), txtAddress.getText());
            Connection connection = DBConnection.getInstance().getConnection();

            Statement stm = connection.createStatement();
            Employee selectedEmployee = tblEmployee.getSelectionModel().getSelectedItem();
            if (selectedEmployee == null) {
                String sql = "INSERT INTO Employee VALUES ('%s','%s','%s')";
                sql = String.format(sql, employee.getId(), employee.getName(), employee.getAddress());
                stm.executeUpdate(sql);

            } else {
                String sql = "UPDATE Employee SET name='%s', address='%s'";

                sql = String.format(sql, employee.getName(), employee.getAddress());
                stm.executeUpdate(sql);

                ObservableList<Employee> employeeList = tblEmployee.getItems();
                int selectedEmployeeIndex = employeeList.indexOf(selectedEmployee);
                employeeList.set(selectedEmployeeIndex, employee);
                tblEmployee.refresh();
            }
            tblEmployee.getItems().add(employee);
            btnNewEmployee.fire();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save details").showAndWait();
        }
    }

    private boolean isDataValid() {
        boolean dataValid = true;
        String name = txtName.getText().strip();
        String address = txtAddress.getText().strip();

        if (!address.matches("[A-Za-z0-9 ]+")) {
            txtAddress.requestFocus();
            txtAddress.selectAll();
            txtAddress.getStyleClass().add(".invalid");
            dataValid = false;
        }

        if (!name.matches("[A-Za-z ]+")) {
            txtName.requestFocus();
            txtName.selectAll();
            txtAddress.getStyleClass().add(".invalid");
            dataValid = false;
        }


        return dataValid;
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            String sql = "DELETE FROM  Employee WHERE id ='%s'";
            sql = String.format(sql, tblEmployee.getSelectionModel().getSelectedItem().getId());
            stm.executeUpdate(sql);
            tblEmployee.getItems().remove(tblEmployee.getSelectionModel().getSelectedItem());
            if (tblEmployee.getItems().isEmpty()) btnNewEmployee.fire();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to delete details").show();
        }
    }
}



