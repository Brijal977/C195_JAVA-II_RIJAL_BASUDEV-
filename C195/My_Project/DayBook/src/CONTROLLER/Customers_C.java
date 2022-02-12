package CONTROLLER;

import DATA.Customer_db;
import MODEL.Customers;
import UTIL.Tools;
import UTIL.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static UTIL.Validator.cust_appt_check;


public class Customers_C implements Initializable {
    Stage stage;

//========================= VARIABLE MAPPING============================================================================
    /**
     *UI-Controller VARIABLE MAPPING : assign variables to  Appointments_View.fxml file controls
     * (Controls used : Tableview, Table columns)
     */

    @FXML private TableView<Customers> Customers_Tableview;
    @FXML private TableColumn<Object, Object> Customer_ID_col;
    @FXML private TableColumn<Object, Object> Customer_Name_col;
    @FXML private TableColumn<Object, Object> Address_col;
    @FXML private TableColumn<Object, Object> Postal_Code_col;
    @FXML private TableColumn<Object, Object> Phone_col;
    @FXML private TableColumn<Object, Object> Division_col;
    @FXML private TableColumn<Object, Object> Country_col;



//===================================INITIALIZATIONS====================================================================
    /**
     * initializes main Customers form
     * populates records (rows) in Customers tableview from sql database
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Customers_Tableview.setItems(Customer_db.get_All_Customers());
        Customer_ID_col.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        Customer_Name_col.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        Address_col.setCellValueFactory(new PropertyValueFactory<>("Address"));
        Postal_Code_col.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        Phone_col.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        Division_col.setCellValueFactory(new PropertyValueFactory<>("Division_Name"));
        Country_col.setCellValueFactory(new PropertyValueFactory<>("Country_Name"));



    }





//=================================== BUTTON FUNCTIONS =================================================================

    /**
     * Navigates to Edit Customer record UI when clicked
     * Auto populates the Edit Customer form with selected record in Customers tableview
     * displays error message if user clicked the edit button without selecting any Customer
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void ECust_bttn_clicked(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/VIEWS/Modify_Customer_View.fxml"));
            loader.load();

            Mod_Customer_C controller = loader.getController();
            controller.Selected_Customer(Customers_Tableview.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("No Customer Selected");

            alert.showAndWait();
        }
    }


    /**
     * ONLY Deletes the desired Customer from the Sql database if customer do not have any appointment records
     * Warns user and prompts to verify the action before deletion of customer record
     * refreshes the main Customers tableview after deletion
     *
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void DCust_bttn_clicked(ActionEvent event) throws IOException, SQLException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("This will delete the customer permanently  !!");
        alert.setContentText(" Do you want to proceed anyway ?");
        Optional<ButtonType> confirm = alert.showAndWait();

        if (confirm.get() == ButtonType.OK) {
            Customers selected_customer = Customers_Tableview.getSelectionModel().getSelectedItem();

            if(!cust_appt_check(selected_customer.getCustomer_ID())){
                Customer_db.Delete_Customer(selected_customer.getCustomer_ID());
                Tools.scene_switch(event,"Customer_View","Customer_View");

            }else{
                Tools.Alert_msg("the selected customer have active appointments, delete them before deleting customer !!");
            }

        }

    }




    //=================================== NAVIGATION'S =================================================================
    /**
     * Navigates to the Add Customers view user interface when clicked
     *
     * @param event
     * @throws IOException
     */
    public void ACust_bttn_clicked(ActionEvent event) throws IOException {
        Tools.scene_switch(event,"Add_Customer_View","Add_Customer_View");
    }

    /**
     * Navigates to the main  view user interface when clicked
     *
     * @param event
     * @throws IOException
     */
    public void cust_main_bttn_clicked(ActionEvent event) throws IOException {
        Tools.scene_switch(event, "Main_View","Main_View");
    }

    /**
     * closes the program when clicked
     * @param event
     */
    public void cust_exit_bttn_clicked(ActionEvent event)  {
        System.exit(0);
    }







}
