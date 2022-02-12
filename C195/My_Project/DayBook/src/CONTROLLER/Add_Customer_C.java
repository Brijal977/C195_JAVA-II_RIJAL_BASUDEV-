package CONTROLLER;

import DATA.Customer_db;
import MODEL.*;
import UTIL.Tools;


import UTIL.Validator;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static UTIL.Validator.customer_error_flag;


public class Add_Customer_C  implements Initializable {


    //========================= VARIABLE MAPPING========================================================================
    /**
     *
     * VARIABLE MAPPING : assign variables to  Add_Customer_View.fxml file controls
        (text-fields, labels,combo-box and buttons etc.)
     */
    @FXML private TextField Customer_ID_txt;
    @FXML private TextField Customer_Name_txt;
    @FXML private TextField Address_Street_txt;
    public  ComboBox<Countries> Select_Country;
    public  ComboBox<Divisions> Select_State;
    @FXML private TextField Phone_txt;
    @FXML private TextField Postal_Code_txt;



    //===================================INITIALIZATIONS================================================================
    /**
     * initializes Add Customer form
     * populates the list in form's Country and Division combo boxes
     * Disables Customer's ID field
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Select_Country.setVisibleRowCount(5);
        Select_Country.setPromptText("Select Country...");
        Select_Country.setItems(Customer_db.get_All_Countries());
        Select_State.setVisibleRowCount(5);
        Select_State.setPromptText("Select Division");

        Customer_ID_txt.setEditable(false);
        Customer_ID_txt.setDisable(true);

    }





//=================================== BUTTON FUNCTIONS =================================================================
    /**
     * verifies if  the input fields are empty.
     * if input fields meets the criteria adds the customer records to the SQL database
     * notifies user after completion of task
     *
     *
     *
     * @param event
     * @throws IOException

     */

    public void Add_Addcust_Clicked(ActionEvent event) throws IOException {

        Validator.Customer_Validate(Customer_Name_txt,Address_Street_txt,Phone_txt,Postal_Code_txt,Select_Country,Select_State);
        if (!customer_error_flag) {

            String customer_Name = Customer_Name_txt.getText();
            String address = Address_Street_txt.getText();
            String postal_code = Postal_Code_txt.getText();
            String phone = Phone_txt.getText();
            Divisions division = Select_State.getValue();


            Customer_db.Add_Customer(customer_Name, address, postal_code, phone, division.getDivision_ID());
            Tools.scene_switch(event,"Customer_View","Customer_View");

            System.out.printf("one customer added !!");

        }
        else{
            customer_error_flag = false;
        }

    }



//=================================== OTHER FUNCTIONS ==================================================================


    /**
     * creates the unique lists of first level divisions based on selection to the country combo box
     *
     * Lambda Expression 2 : creates the filtered list based on user's selection on country
     * improves readability and data accuracy
     * @return filteredDivisionList
     */
    public ObservableList<Divisions> division_Filter() {
        ObservableList<Divisions> all_Divisions = Customer_db.get_All_Divisions();

        //Lambda 2 : creates the filtered list based on user's selection on country
        // improves readability and data accuracy

        FilteredList<Divisions> filteredDivisionList = new FilteredList<>(all_Divisions, i -> i.getCountry_ID() == Select_Country.getSelectionModel().getSelectedItem().getCountry_ID());
        return filteredDivisionList;
    }


    /**
     * populates the division combo box based on selection on Country combo box
     *
     * Uses the return value of  Lambda Expression 2 to populate the Division Combo box
     * @param actionEvent
     */
    public void onCountrySelect(ActionEvent actionEvent) {
        Select_State.setItems(division_Filter());

    }





//=================================== NAVIGATION'S =====================================================================

    /**
     * Navigates to the main Customers view user interface
     *
     * @param event
     * @throws IOException
     */

    public void Cancel_Addcust_Clicked(ActionEvent event) throws IOException {
        Tools.scene_switch(event,"Customer_View","Customer_View");
    }

}
