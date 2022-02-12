package CONTROLLER;

import DATA.Appointment_db;
import MODEL.Users;
import UTIL.Tools;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static CONTROLLER.Login_C.cur_User;

public class Main_C  implements Initializable {



    //=================================== NAVIGATION'S ==================================================

    /**
     * Navigates to the Login view user interface when clicked
     * updates the login_activity log upon logout
     *
     * @param event
     * @throws IOException
     */
    public void logout_Clicked(ActionEvent event) throws IOException {
        Tools.scene_switch(event,"Login","Login");
        Login_C. write_log(cur_User.getUser_Name(),"Log Out Successful !");

    }

    /**
     * closes the program when clicked
     * @param event ActionEvent
     */
    public void main_exit_Clicked(ActionEvent event)  {
        System.exit(0);
    }


    /**
     * Navigates to the main Customers view user interface when clicked
     *
     * @param event
     * @throws IOException when exception occurs
     */
    public void Customer_Clicked(ActionEvent event)throws IOException {
        Tools.scene_switch(event,"Customer_View","Customer_View");

    }

    /**
     * Navigates to the Reports view user interface when clicked
     *
     * @param event
     * @throws IOException
     */
    public void Reports_Clicked(ActionEvent event)throws IOException {
       Tools.scene_switch(event,"Reports_View","Reports_View");
    }

    /**
     * Navigates to the main Appointments view user interface when clicked
     *
     * @param event
     * @throws IOException
     */
    public void Appointment_Clicked(ActionEvent event)throws IOException {
        Tools.scene_switch(event,"Appointments_View","Appointments_View");
    }


    /**
     * Alerts the user if there is upcoming appointment within 15 minute of login time
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //===================================INITIALIZATIONS==================================================
        try {
            Appointment_db.alert_Appt();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
