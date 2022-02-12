package CONTROLLER;

import DATA.Appointment_db;
import MODEL.Apppointments;
import UTIL.Tools;
import javafx.collections.ObservableList;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.lang.*;


public class Appointments_C implements Initializable {

    /**
     * Declares the local string variable
     * the values of sql_query_filter will be used throughout the controller to switch between different sql commands
     */
    String sql_query_filter = "all";


//========================= VARIABLE MAPPING============================================================================

    /**
     *
     * VARIABLE MAPPING : assign variables to  Appointments_View.fxml file controls
     * (Controls used: Tableview, Table columns, radio buttons.)
     */

    @FXML RadioButton on_all_rad;
    @FXML RadioButton on_month_rad;
    @FXML RadioButton on_week_rad;

    @FXML private TableView<Apppointments> Appointments_Tableview;
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






//=================================== INITIALIZATIONS ==================================================================
    /**
     * initializes main Appointment form
     * populates records (rows) in Appointments tableview from sql database
     * assigns the toggle-group to the radio buttons
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ToggleGroup tg = new ToggleGroup();
        on_all_rad.setToggleGroup(tg);
        on_month_rad.setToggleGroup(tg);
        on_week_rad.setToggleGroup(tg);
        on_all_rad.setSelected(true);


        sql_query_filter = "all";

        Appointments_Tableview.setItems(Appointment_db.get_All_Appts(sql_query_filter));
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




    }








    //=================================== UPDATE, DELETE -  BUTTON FUNCTIONS ===========================================

    /**
     * Navigates to Edit Appointments UI when clicked
     * Auto populates the Edit Appointment form with selected record in appointments tableview
     * displays error message if user clicked the edit button without selecting any appointment
     *
     *
     * @param event
     * @throws IOException
     */


    public void Edit_Appt_Clicked(ActionEvent event)throws IOException{

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/VIEWS/Mod_Appt_View.fxml"));
            loader.load();

            Mod_Appt_C controller = loader.getController();
            controller.Selected_Appt(Appointments_Tableview.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("No Appointment Selected");

            alert.showAndWait();
        }


    }


    /**
     * Deletes the desired Appointment from the Sql database
     * Warns user and prompts to verify the action before deletion of appointment
     * refreshes the main Appointments tableview after deletion
     *
     *
     * @param event
     * @throws IOException
     */
    public void Delete_Appt_Clicked(ActionEvent event)throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("This will delete the selected appointment permanently  !!");
        alert.setContentText(" Do you want to proceed anyway ?");
        Optional<ButtonType> confirm = alert.showAndWait();

        if (confirm.get() == ButtonType.OK) {

            Integer APT_ID = Appointments_Tableview.getSelectionModel().getSelectedItem().getAppointment_ID();
            String APT_TYPE = Appointments_Tableview.getSelectionModel().getSelectedItem().getType();

            Tools.Alert_msg(" The following Appointment has been deleted successfully !  \n\n"  +
                    "Appointment ID :  "+ APT_ID +"\n" +
                    "Appointment TYPE :  "+ APT_TYPE +"\n");

            Appointment_db.delete_Appointment(Appointments_Tableview.getSelectionModel().getSelectedItem().getAppointment_ID());
            Tools.scene_switch(event,"Appointments_View","Appointments_View");
        }

    }




//=================================== Radio filter -  FUNCTIONS ========================================================

    /**
     * performs tableview record filter based on users radio buttons selection
     * Displays the all appointments , monthly appointments and weekly appointments as desired
     *
     * @param event
     * @throws IOException
     */

    public void View_by_filter(ActionEvent event)  throws IOException{
        if (this.on_all_rad.isSelected()) {
            sql_query_filter = "all";
            this.Appointments_Tableview.setItems(Appointment_db.get_All_Appts(sql_query_filter));

        } else if (this.on_month_rad.isSelected()) {
            sql_query_filter = "month";
            this.Appointments_Tableview.setItems(Appointment_db.get_All_Appts(sql_query_filter));

        } else if (this.on_week_rad.isSelected()) {
            sql_query_filter = "week";
            this.Appointments_Tableview.setItems(Appointment_db.get_All_Appts(sql_query_filter));
        }

    }







    //=================================== NAVIGATION'S =================================================================
    /**
     * Navigates to the Add Appointments view user interface when clicked
     *
     * @param event
     * @throws IOException
     */
    public void Add_Appt_Clicked(ActionEvent event)throws IOException {
        Tools.scene_switch(event,"Add_Appt_View","Add_Appt_View");
    }

    /**
     * Navigates to the main  view user interface when clicked
     *
     * @param event
     * @throws IOException
     */
    public void appt_main_bttn_clicked(ActionEvent event) throws IOException {
        Tools.scene_switch(event, "Main_View","Main_View");
    }

    /**
     * closes the program when clicked
     * @param event
     */
    public void appt_exit_bttn_clicked(ActionEvent event) {
        System.exit(0);
    }



}
