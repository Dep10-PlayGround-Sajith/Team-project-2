package lk.ijse.dep10.app.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.app.db.DBConnection;
import lk.ijse.dep10.app.model.Customer;

import java.sql.*;

public class CustomerViewController {

    @FXML
    private Button BtnNewCustomer;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;


    public void initialize() {

        /*Column Mapping*/
        tblCustomers.getColumns().get( 0 ).setCellValueFactory( new PropertyValueFactory<>( "id" ) );
        tblCustomers.getColumns().get( 1 ).setCellValueFactory( new PropertyValueFactory<>( "name" ) );
        tblCustomers.getColumns().get( 2 ).setCellValueFactory( new PropertyValueFactory<>( "address" ) );

        loadAllStudent();

        /* Setup keyboard shortcuts */

        tblCustomers.getSelectionModel().selectedItemProperty().addListener( (value, old, current) -> {
            if (current == null) return;

            txtId.setText( current.getId() + "" );
            txtName.setText( current.getName() );
            txtAddress.setText( current.getAddress() );

        } );
    }

    private void loadAllStudent() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery( "SELECT *FROM Customer" );
            ObservableList<Customer> studentList = tblCustomers.getItems();

            while (rst.next()) {
                String id = rst.getString( "id" );
                String name = rst.getString( "name" );
                String address = rst.getString( "address" );

                Customer student = new Customer( id, name, address );
                studentList.add( student );
            }

            //Platform.runLater(()->btnNewStudent.fire());
            Platform.runLater( BtnNewCustomer::fire );
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert( Alert.AlertType.ERROR, "Failed to load the Customer File" ).show();
            Platform.exit();
        }

    }


    @FXML
    void BtnNewCustomerOnAction(ActionEvent event) {
        ObservableList<Customer> customerList = tblCustomers.getItems();
        String newID = (customerList.isEmpty() ? "C001" : String.format( "C%03d", Integer.parseInt( customerList.get( customerList.size() - 1 ).getId().substring( 1 ) ) + 1 ));
        txtId.setText( newID + "" );
        txtName.clear();
        txtAddress.clear();
        txtAddress.clear();
        tblCustomers.getSelectionModel().clearSelection();

        txtName.requestFocus();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try{
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            String sql ="DELETE FROM Customer WHERE id='%s'";
            sql=String.format( sql,tblCustomers.getSelectionModel().getSelectedItem().getId() );
            statement.executeUpdate( sql );

            tblCustomers.getItems().remove( tblCustomers.getSelectionModel().getSelectedItem() );
            if(tblCustomers.getItems().isEmpty()) BtnNewCustomer.fire();
        }catch (SQLException e) {
            e.printStackTrace();
            new Alert( Alert.AlertType.ERROR,"Failed to delete the customer , try again!" ).show();
        }


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (!isDataValid()) return;

        Customer customer = new Customer( txtId.getText(), txtName.getText(), txtAddress.getText() );

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            Customer selectedCustomer = tblCustomers.getSelectionModel().getSelectedItem();

            if (selectedCustomer == null) {
                String sql = "Insert INTO Customer VALUES ('%s','%s','%s')";
                sql = String.format( sql, customer.getId(), customer.getName(), customer.getAddress() );
                statement.executeUpdate( sql );
                tblCustomers.getItems().add( customer );
            } else {
                String sql = "UPDATE Customer SET name='%s', address='%s' WHERE id=%d";
                sql = String.format( sql, customer.getName(), customer.getAddress(), customer.getId() );
                statement.executeUpdate( sql );

                ObservableList<Customer> studentList = tblCustomers.getItems();
                int selectedCustomerIndex = studentList.indexOf( selectedCustomer );
                studentList.set( selectedCustomerIndex, customer );
                tblCustomers.refresh();
            }
            BtnNewCustomer.fire();

        } catch (SQLException e) {
            throw new RuntimeException( e );
        }
    }

    private boolean isDataValid() {
        boolean isDataValid = true;

        for (Node node : new Node[]{txtName, txtAddress}) {
            node.getStyleClass().remove( "invalid" );
        }

        String name = txtName.getText();
        String address = txtAddress.getText();


        if (address.strip().length() < 3) {
            isDataValid = false;
            txtAddress.requestFocus();
            txtAddress.getStyleClass().add( "invalid" );
        }

        if (!name.matches( "[A-Za-z ]+" )) {
            isDataValid = false;
            txtName.requestFocus();
            txtName.getStyleClass().add( "invalid" );
        }
        return isDataValid;
    }

}