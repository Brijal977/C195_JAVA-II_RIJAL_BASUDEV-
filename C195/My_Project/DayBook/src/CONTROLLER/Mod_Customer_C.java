package CONTROLLER;

import DATA.Customer_db;
import MODEL.Countries;
import MODEL.Customers;
import MODEL.Divisions;
import UTIL.Tools;
import UTIL.Validator;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static UTIL.Validator.customer_error_flag;


public class Mod_Customer_C implements Initializable {


    //========================= VARIABLE MAPPING========================================================================
    /**
     *
     * VARIABLE MAPPING : assign variables to  Mod_Customer_View.fxml file controls (text-fields, labels,combo-box and buttons etc.)
     */
    @FXML public  TextField Customer_ID_txt;
    @FXML private TextField Customer_Name_txt;
    @FXML private TextField Address_Street_txt;
    public ComboBox<Countries> Select_Country;
    public  ComboBox<Divisions> Select_State;
    @FXML private TextField Phone_txt;
    @FXML private TextField Postal_Code_txt;


    //===================================INITIALIZATIONS================================================================
    /**
     * initializes Modify Customer form
     * populates the list in form's Country and Division combo boxes
     * Disables Customer's ID field
     *
     * @param url
     * @param resourceBundle
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Select_Country.setVisibleRowCount(5);
        Select_Country.setPromptText("Select Country...");
        Select_Country.setItems(Customer_db.get_All_Countries());
        Select_State.setVisibleRowCount(5);
        Select_State.setPromptText("Select Division");
        Select_State.setItems(Customer_db.get_All_Divisions());

        Customer_ID_txt.setEditable(false);
        Customer_ID_txt.setDisable(true);
    }





    //=================================== FUNCTION BUTTONS  ============================================================
    /**
     * verifies if  the input fields are empty.
     * if input fields meets the criteria updates the customer records to the SQL database
     * notifies user after completion of task
     *
     *
     *
     * @param event
     * @throws IOException
     */
    public void Update_Modcust_Clicked(ActionEvent event) throws IOException  {

        Validator.Customer_Validate(Customer_Name_txt,Address_Street_txt,Phone_txt,Postal_Code_txt,Select_Country,Select_State);

        if (!customer_error_flag) {
            String customer_ID = Customer_ID_txt.getText();
            String customer_Name = Customer_Name_txt.getText();
            String address = Address_Street_txt.getText();
            String postal_code = Postal_Code_txt.getText();
            String phone = Phone_txt.getText();
            Divisions division = Select_State.getValue();


            Customer_db.Update_Customer( customer_Name, address, postal_code, phone, division.getDivision_ID(),customer_ID);
            Tools.scene_switch(event,"Customer_View","Customer_View");

            System.out.printf("one customer updated !!");
        }

        // Had the logical error here : if user input became invalid on first attempt it was not adding or updating the customer after that : the error_flag stayed true

        else{
            customer_error_flag = false;
        }


    }




    //=================================== OTHER FUNCTIONS ==============================================================

    /**
     * creates the unique lists of first level divisions based on selection to the country combo box
     * @return
     */
    public ObservableList<Divisions> divisionFilter() {
        ObservableList<Divisions> allDivisions = Customer_db.get_All_Divisions();
        FilteredList<Divisions> filteredDivisionList = new FilteredList<>(allDivisions, i -> i.getCountry_ID() == Select_Country.getSelectionModel().getSelectedItem().getCountry_ID());
        return filteredDivisionList;
    }

    /**
     * populates the division combo box based on selection on Country combo box
     * @param actionEvent
     */
    public void onCountrySelect(ActionEvent actionEvent) {
        Select_State.setItems(divisionFilter());

    }

    /**
     *  holds the records of selected customer to be updated  from main customers tableview
     *  will be used to populate the Modify-customer-form when initialized
     *
     *
     *
     * @param customers
     */
    public void Selected_Customer (Customers customers) {
        Customer_ID_txt.setText(String.valueOf(customers.getCustomer_ID()));
        Customer_Name_txt.setText(customers.getCustomer_Name());
        Address_Street_txt.setText(customers.getAddress());
        Postal_Code_txt.setText(customers.getPostal_Code());
        Phone_txt.setText(customers.getPhone());


        for (Countries c : Select_Country.getItems()) {
            if (customers.getCountry_ID() == c.getCountry_ID()) {
                Select_Country.setValue(c);
                break;
            }
        }
        for (Divisions d : Select_State.getItems()) {
            if (customers.getDivision_ID() == d.getDivision_ID()) {
                Select_State.setValue(d);
                break;
            }
        }
    }




    //=================================== NAVIGATION BUTTONS ===========================================================
    /**
     * Navigates to the main Customers view user interface
     *
     * @param event
     * @throws IOException
     */

    public void Cancel_Modcust_Clicked(ActionEvent event) throws IOException  {
        Tools.scene_switch(event,"Customer_View","Customer_View");
    }


}
