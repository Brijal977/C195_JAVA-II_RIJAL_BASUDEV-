package CONTROLLER;

import DATA.Appointment_db;
import MODEL.*;
import UTIL.TimeMachine;
import UTIL.Tools;
import UTIL.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

import static UTIL.Validator.*;

public class Mod_Appt_C implements Initializable {

//========================= VARIABLE MAPPING========================================================

    /**
     *
     * VARIABLE MAPPING : assign variables to  Mod_Appt_View.fxml file controls (text-fields, labels,combo-box and buttons etc.)
     */

    @FXML private TextField Appt_ID_txt;
    @FXML private TextField Title_txt;
    @FXML private TextField Description_txt;
    @FXML private TextField Location_txt;
    public ComboBox<Contacts> Contact_Combo;
    @FXML private ComboBox<String> Types_Combo;
    @FXML  DatePicker Date_Picker;
    public  ComboBox<String>Start_hrs;
    public  ComboBox<String> End_hrs;
    public ComboBox<Customers> Customer_Combo;
    public ComboBox<Users> User_Combo;
    


//===================================INITIALIZATIONS==================================================

    /**
     * initializes Modify Appointment form
     * populates the list in form's combo boxes
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Contact_Combo.setItems(Appointment_db.allcontacts);
        Start_hrs.setItems(TimeMachine.Timeslots());
        End_hrs.setItems(TimeMachine.Timeslots());
        Customer_Combo.setItems(Appointment_db.allcustomers);
        Types_Combo.setItems(Appointment_db.appt_types);
        User_Combo.setItems(Appointment_db.allusers);

    }


    
    

//=================================== BUTTON FUNCTIONS ==============================================
    /**
     * verifies if the inputs fields are empty.
     * verifies the appointment timeframes are with in business hours of EST (08:00 - 20:00).
     * verifies the desired appointment time doesn't overlap with existing appointments of same customer.
     *
     * if appointment meets the validation criteria , updates appointment to the SQL database.
     * notifies user after completion of task
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */

    public void Update_Modappt_Clicked(ActionEvent event) throws IOException, SQLException {

        Validator.Appt_Validate(Title_txt,Description_txt,Location_txt,Types_Combo,Contact_Combo,Date_Picker,Start_hrs,End_hrs,Customer_Combo,User_Combo);
        if(!appt_error_flag) {


            String appointment_id = Appt_ID_txt.getText();
            String appointment_title = Title_txt.getText();
            String description = Description_txt.getText();
            String location = Location_txt.getText();

            String type = Types_Combo.getValue();
            Contacts contact = Contact_Combo.getValue();
            Customers customer = Customer_Combo.getValue();
            Users user = User_Combo.getValue();
            


            LocalDate input_date = Date_Picker.getValue();
            String input_ST = Start_hrs.getSelectionModel().getSelectedItem();
            String input_ET = End_hrs.getSelectionModel().getSelectedItem();
            LocalTime  Local_ST = LocalTime.parse(input_ST+":"+"00");
            LocalTime Local_ET = LocalTime.parse(input_ET+":"+"00");
            LocalDateTime Local_SDT = LocalDateTime.of(input_date, Local_ST.plusSeconds(00));
            LocalDateTime Local_EDT = LocalDateTime.of(input_date, Local_ET.plusSeconds(00));


            int current_customer_id = customer.getCustomer_ID();

            //Lamda expression :- assigns Appointment id 0 or number in Appt_ID_txt if it's not null :- avoids null exception error
            int current_appt_id = Appt_ID_txt.getText().isEmpty() ? 0 : Integer.parseInt(Appt_ID_txt.getText());

            Validator.Appt_time_Validate (Local_SDT, Local_EDT);
            Validator.Appt_overlap_Validate(current_customer_id,current_appt_id,Local_SDT, Local_EDT);

            if (!Appt_time_flag && !Appt_overlap_flag){
                Appointment_db.updateAppointment(appointment_title, description, location, type, Local_SDT, Local_EDT, customer.getCustomer_ID(), user.getUser_ID(), contact.getContact_ID(), appointment_id);
                Tools.scene_switch(event, "Appointments_View", "Appointments_View");
                System.out.printf("one appointment modified !!");
            } else {
                System.out.printf("Appointment error flag activated");
                Appt_time_flag = false;
                Appt_overlap_flag = false;

            }


        }
        else {
            appt_error_flag = false;


        }

    }









//=================================== OTHER FUNCTIONS ================================================

    /**
     * holds the records of selected appointment to be updated  from main appointments tableview
     * will be used to populate the Modify-appointment-form when initialized
     *
     * @param apppointments
     */


    public void Selected_Appt (Apppointments apppointments) {
        Appt_ID_txt.setText(String.valueOf(apppointments.getAppointment_ID()));
        Title_txt.setText(apppointments.getTitle());
        Description_txt.setText(apppointments.getDescription());
        Location_txt.setText(apppointments.getLocation());

        Types_Combo.setValue(apppointments.getType());
        
        Date_Picker.setValue(apppointments.getStart().toLocalDate());
        
        Start_hrs.setValue(String.valueOf(apppointments.getStart().toLocalTime()));
        End_hrs.setValue(String.valueOf(apppointments.getEnd().toLocalTime()));

        for (Contacts c : Contact_Combo.getItems()) {
            if (apppointments.getContact_ID() == c.getContact_ID()) {
                Contact_Combo.setValue(c);
                break;
            }
        }

        for (Customers customers : Customer_Combo.getItems()) {
            if (apppointments.getCustomer_ID() == customers.getCustomer_ID()) {
                Customer_Combo.setValue(customers);
                break;
            }
        }

        for (Users users : User_Combo.getItems()) {
            if (apppointments.getUser_ID() == users.getUser_ID()){
                User_Combo.setValue(users);
                break;
            }
        }


    }







//=================================== NAVIGATION'S ==================================================


    /**
     * Navigates to the main Appointments view user interface
     *
     * @param event
     * @throws IOException
     */

    public void Cancel_Modappt_Clicked(ActionEvent event) throws IOException {
        Tools.scene_switch(event,"Appointments_View","Appointments_View");
    }


}
