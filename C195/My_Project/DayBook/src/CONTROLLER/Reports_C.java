package CONTROLLER;

import DATA.Appointment_db;
import DATA.Contacts_db;
import DATA.Customer_db;
import DATA.Reports_db;
import MODEL.*;
import UTIL.Tools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.ResourceBundle;

public class Reports_C implements Initializable {



//========================= VARIABLE MAPPING============================================================================

    /**
     * UI-Controller VARIABLE MAPPING
     * Generates 3 different types reports
     * Tab 1 : Report 1 : Appointments by month & type
     * Tab 2 : Report 2 : Appointments by Contact
     * Tab3  : Report 3 : Customer by Country
     *
     */

//========================= Report   1  MAPPING=========================================================================
    @FXML private TableView<Report1> Report1_Tableview;
    @FXML private TableColumn<Report1, Integer> Appt_type_col;
    @FXML private TableColumn<Report1, String> Appt_month_col;
    @FXML private TableColumn<Report1, String> Appt_count_col;


//========================= Report   2  MAPPING=========================================================================
    private final ObservableList<Contacts> all_Contacts = Contacts_db.get_All_Contacts();
    private final ObservableList<Countries> all_Countries = Customer_db.get_All_Countries();

    @FXML public ComboBox<Contacts> R2_Contact_Combo;
    @FXML public Label Display_board;


    @FXML private TableView<Apppointments> Contact_appt_tableview;
    @FXML private TableColumn<Apppointments, Integer> Appt_ID_col;
    @FXML private TableColumn<Apppointments, String> Title_col;
    @FXML private TableColumn<Apppointments, String> Description_col;
    @FXML private TableColumn<Apppointments, String> Location_col;
    @FXML private TableColumn<Apppointments, Integer> Contact_col;
    @FXML private TableColumn<Apppointments, String> Type_col;
    @FXML private TableColumn<Apppointments, String> Start_DT_col;
    @FXML private TableColumn<Apppointments, String> End_DT_col;
    @FXML private TableColumn<Apppointments, Integer> Customer_ID_col;
    @FXML private TableColumn<Apppointments, Integer> User_ID_col;



//========================= Report   3  MAPPING=========================================================================

    @FXML public ComboBox<Countries> R3_Country_Combo;

    @FXML private TableView<Report3> R3_Customer_Tableview;
    @FXML private TableColumn<Report3, Integer> Customer_ID2_col;
    @FXML private TableColumn<Report3, String> Customer_Name_col;
    @FXML private TableColumn<Report3, String> Phone_col;
    @FXML private TableColumn<Report3, String> Postal_Code_col;
    @FXML private TableColumn<Report3, String> Street_Address_col;
    @FXML private TableColumn<Report3, String> Division_col;
    @FXML private TableColumn<Report3, String> Country_col;


    /**
     * Initializes the Reports UI
     * populates the Tableviews and Combo boxes
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Report  1
        Report1_Tableview.setItems(Reports_db.Appt_by_monthtype());
        Appt_type_col.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Appt_month_col.setCellValueFactory(new PropertyValueFactory<>("Month"));
        Appt_count_col.setCellValueFactory(new PropertyValueFactory<>("Count"));


        //Report  2
        R2_Contact_Combo.setItems(all_Contacts);



        //Report 3
        R3_Country_Combo.setVisibleRowCount(5);
        R3_Country_Combo.setPromptText("Select Country...");
        R3_Country_Combo.setItems(all_Countries);

    }





   // ========================================= Report Tabs ============================================================

    /**
     * shows the appointments of specific contact
     * Displays the contact's Appointments in tableview
     *
     *
     *
     * @param actionEvent: Search2_bttn_Clicked
     * @throws SQLException
     */
//Report2 - Appointments By contact

    public void Search2_bttn_Clicked(ActionEvent actionEvent) throws SQLException {
        int selected_contact_id = 0;
        Apppointments contact_appts;
        
        String selected_contact_name = R2_Contact_Combo.getSelectionModel().getSelectedItem().toString();
        if (selected_contact_name != null) {


            for (Contacts contact : all_Contacts) {
                if (selected_contact_name.equals(contact.getContact_Name())) {
                    selected_contact_id = contact.getContact_ID();
                }
            }

            String  sql_query_filter = "all";

            ObservableList<Apppointments> all_Appts = Appointment_db.get_All_Appts(sql_query_filter);
            ObservableList<Apppointments> appt_for_contact = FXCollections.observableArrayList();

            for (Apppointments appointment : all_Appts) {
                if (selected_contact_id == appointment.getContact_ID()) {
                    contact_appts = appointment;
                    appt_for_contact.add(contact_appts);
                }
            }
            Contact_appt_tableview.setItems(appt_for_contact);
            Appt_ID_col.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
            Title_col.setCellValueFactory(new PropertyValueFactory<>("Title"));
            Description_col.setCellValueFactory(new PropertyValueFactory<>("Description"));
            Location_col.setCellValueFactory(new PropertyValueFactory<>("Location"));
            Type_col.setCellValueFactory(new PropertyValueFactory<>("Type"));
            Start_DT_col.setCellValueFactory(new PropertyValueFactory<>("Start"));
            End_DT_col.setCellValueFactory(new PropertyValueFactory<>("End"));
            Customer_ID_col.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            User_ID_col.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
            Contact_col.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));


            Integer rows = Contact_appt_tableview.getItems().size();
            // System.out.println(rows);

            Display_board.setText("Total "+ rows+ " Appointments found for "+ selected_contact_name);
        }

    }


    /**
     * Displays the Customers in tableview based on their country
     *
     * @param actionEvent
     */
//Report - By Location
    public void Search3_bttn_Clicked(ActionEvent actionEvent) {
        int selected_country_id = 0;
        String selected_country =  R3_Country_Combo.getSelectionModel().getSelectedItem().toString();
        Report3 coutry_customers;
        
        if(selected_country != null){



            ObservableList<Report3> all_customer = Reports_db.Cusomer_by_Country();
            ObservableList<Report3> country_customer_list = FXCollections.observableArrayList();

            for (Report3 report : all_customer) {
                if (selected_country.equals(report.getCountry())) {
                    coutry_customers = report;
                    country_customer_list.add(coutry_customers);
                }
            }

            R3_Customer_Tableview.setItems(country_customer_list);
            Customer_ID2_col.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            Customer_Name_col.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
            Postal_Code_col.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
            Phone_col.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            Country_col.setCellValueFactory(new PropertyValueFactory<>("Country"));
            Division_col.setCellValueFactory(new PropertyValueFactory<>("Division"));
            Street_Address_col.setCellValueFactory(new PropertyValueFactory<>("Address"));

        }
        

    }


    //======================================== Navigation ==============================================================

    /**
     * Navigates to the main  view user interface when clicked
     *
     * @param event
     * @throws IOException
     */
    public void Main_bttn_clicked(ActionEvent event) throws IOException {
        Tools.scene_switch(event, "Main_View","Main_View");
    }



    /**
     * closes the program when clicked
     * @param event
     */
    public void Exit_bttn_Clicked(ActionEvent event) {
        System.exit(0);
    }

}
