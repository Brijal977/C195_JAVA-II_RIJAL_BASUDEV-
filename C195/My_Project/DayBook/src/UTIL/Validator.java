package UTIL;

import DATA.Appointment_db;
import MODEL.Apppointments;
import MODEL.Users;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;

import static CONTROLLER.Login_C.cur_User;
import static CONTROLLER.Login_C.write_log;


public class Validator {


    /**
     * Local Boolean variables
     * Validate functions use these static variables
     */
    public static Boolean  login_error_flag = false;
    public static Boolean  customer_error_flag = false;
    public static Boolean  appt_error_flag = false;

    public static Boolean Appt_time_flag = false;
    public static Boolean Appt_overlap_flag = false;

    //==================================================== User Login - Input Validation ====================================================================================
    /*


    /**
     * Validates user's loging credentials with sql database user credentials
     * Authenticate access to program on valid login
     * records logins attempts in login_activity.txt file
     *
     * @param username username
     * @param password password
     * @return
     * @throws SQLException
     * @throws IOException
     */



    public static Boolean valid_login(String username, String password) throws SQLException, IOException {
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String sql_query = "SELECT * FROM users WHERE User_Name='" + username + "' AND Password='" + password + "'";
            ResultSet query_results = statement.executeQuery(sql_query);



            if(query_results.next()) {
                cur_User = new Users();
                cur_User.setUser_ID(Integer.valueOf(query_results.getString("User_ID")));
                cur_User.setUser_Name(query_results.getString("User_Name"));
                cur_User.setPassword(query_results.getString("Password"));
                statement.close();

                if(password != null)
                {
                    if(cur_User.getUser_Name().equals(username) && (cur_User.getPassword().equals(password)))
                    {
                        write_log(username,"Login Successful !");
                        return true;
                    }
                }


            } else {
                write_log(username,"Invalid Credentials, Login Failed !");
                return false;
            }
        } catch (SQLException | IOException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }

        return false;
    }








    //==================================================== Customer Add/Modify - Input Validation ===========================================================================

    /**
     * Customer input validator
     * checks whether required inputs are empty during customer add - modify operations
     * Displays Alert messages
     *
     * @param Name Name
     * @param Street Street
     * @param Phone Phone
     * @param Postal Postal
     * @param Country Country
     * @param State State
     */
    public static void Customer_Validate ( TextField Name, TextField Street, TextField Phone, TextField Postal, ComboBox Country, ComboBox State) {

        Alert error_msg = new Alert(Alert.AlertType.ERROR);
        error_msg.setHeaderText("Invalid Input");
        String error = "";

        String[] input_array = new String[]{ Name.getText(), Street.getText(), Phone.getText(), Postal.getText()};
        String[] input_array_title = new String[]{"Name", "Street Address", "Phone", "Postal Code"};

        for (int i = 0; i < input_array.length; i++) {
            if (input_array[i].isBlank()) {
                error = (error + "\n * Customer " + input_array_title[i] + "  Input field cannot be empty or null");
                customer_error_flag = true;

            }
        }

        if (Country.getSelectionModel().isEmpty()){
            error = (error + "\n *  Country input box  cannot be empty or null");
            customer_error_flag = true;
        }
        if (State.getSelectionModel().isEmpty()){
            error = (error + "\n *  State input box  cannot be empty or null");
            customer_error_flag = true;
        }
        if (error.isBlank()) {
            error_msg.hide();
        } else {
            error_msg.setContentText(error);
            error_msg.showAndWait();

        }

    }



    //==================================================== Appointments - Input Validation ===========================================================================

    /**
     * Appointment input validator
     * checks whether required inputs are empty during Appointment add - modify operations
     * Displays Alert messages
     *
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param contacts  contacts
     * @param date_picker date_picker
     * @param start_hrs start_hrs
     * @param end_hrs end_hrs
     * @param customer_id customer_id
     * @param user_id user_id
     */
    public static void Appt_Validate (TextField title, TextField description, TextField location,ComboBox type, ComboBox contacts,  DatePicker date_picker,
                                      ComboBox start_hrs, ComboBox end_hrs, ComboBox customer_id, ComboBox user_id) {

        Alert error_msg = new Alert(Alert.AlertType.ERROR);
        error_msg.setHeaderText("Invalid Input");
        String error = "";

        String[] testbox_array = new String[]{title.getText(), description.getText(), location.getText()};
        String[] textbox_title = new String[]{"Title", "Description", "Location"};

        for (int i = 0; i < testbox_array.length; i++) {
            if (testbox_array[i].isBlank()) {
                error = (error + "\n * Customer " + textbox_title[i] + "  Input field cannot be empty or null");
                appt_error_flag = true;

            }
        }

        if (type.getSelectionModel().isEmpty()) {
            error = (error + "\n *  Type input box  cannot be empty or null");
            appt_error_flag = true;
        }

        if (contacts.getSelectionModel().isEmpty()) {
            error = (error + "\n *  Country input box  cannot be empty or null");
            appt_error_flag = true;
        }
        if(date_picker.getEditor().getText().isEmpty()){
            error = (error + "\n *  Date  cannot be empty or null");
            appt_error_flag = true;
        }

        if (start_hrs.getSelectionModel().isEmpty()) {
            error = (error + "\n *  Start Hour box  cannot be empty or null");
            appt_error_flag = true;
        }
        if (end_hrs.getSelectionModel().isEmpty()) {
            error = (error + "\n * End Hour box  cannot be empty or null");
            appt_error_flag = true;
        }
        if (customer_id.getSelectionModel().isEmpty()) {
            error = (error + "\n *  Customer ID box  cannot be empty or null");
            appt_error_flag = true;
        }
        if (user_id.getSelectionModel().isEmpty()) {
            error = (error + "\n *  User ID box  cannot be empty or null");
            appt_error_flag = true;
        }


        if (error.isBlank()) {
            error_msg.hide();
        } else {
            error_msg.setContentText(error);
            error_msg.showAndWait();

        }

    }



//==================================================== Appointments - time validation ===========================================================================


    /**
     * Validates : if time selections for new appointments
     * checks whether new appointment time are in the bussiness hours
     *
     *
     * @param start start
     * @param end end
     */
    public static void Appt_time_Validate (LocalDateTime start, LocalDateTime end) {

        Alert Appt_time_error = new Alert(Alert.AlertType.ERROR);
        Appt_time_error.setHeaderText("Invalid Time selected");
        String error = "";


        int startHour = TimeMachine.LOCAL_to_ETC(start).getHour();
        int endHour = TimeMachine.LOCAL_to_ETC(end).getHour();



        if ((start.isBefore(ChronoLocalDateTime.from(ZonedDateTime.now())) && (end.isBefore(ChronoLocalDateTime.from(ZonedDateTime.now()))))){

            error = (error + "\n * The selected  date - time  for current appointment  is in the PAST !");
            Appt_time_flag = true;

        } else if ((startHour < 8 || startHour > 22 ) || (endHour < 8 || endHour > 22 )){

            error = (error + "\n * The Selected  Appointment timeframe is outside the office operation hours !");
            Appt_time_flag = true;

        } else if (startHour > endHour){
            error = (error + "\n * The Appointment End date cannot be before Start date");
            Appt_time_flag = true;

        }


        if (error.isBlank()) {
            Appt_time_error.hide();
        } else {
            Appt_time_error.setContentText(error);
            Appt_time_error.showAndWait();

        }

    }


    /**
     * Validates of new appointment overlaps with customer's existing appointment time
     * Displays the alert messages
     *
     * @param cus_ID cus_ID
     * @param apt_ID apt_ID
     * @param start start
     * @param end  end
     * @throws SQLException
     */
    public static void Appt_overlap_Validate (int cus_ID, int apt_ID, LocalDateTime start, LocalDateTime end) throws SQLException {

        Alert Appt_overlap_error = new Alert(Alert.AlertType.ERROR);
        Appt_overlap_error.setHeaderText("Selected time Overlaps with existing appointment");
        String error = "";


        ObservableList<Apppointments> appointments = Appointment_db.get_cust_Appts(cus_ID);


        for (Apppointments a : appointments) {

            // Don't compare an appointment being edited to itself
            if (apt_ID == a.getCustomer_ID()) {
                continue;
            }

            if (a.getStart().isBefore(end) && start.isBefore(a.getEnd())) {
                error = ("Appointment input appointment date time conflicts with  appointment ID " + a.getAppointment_ID());
                Appt_overlap_flag = true;
            }


        }

        if (error.isBlank()) {
            Appt_overlap_error.hide();


        } else {
            Appt_overlap_error.setContentText(error);
            Appt_overlap_error.showAndWait();

        }


    }



    //================================ Customer delete - Appointment check =======================================

    /**
     * checks if particular customer have any appointment records in database
     * Display alert messages
     *
     * @param cus_ID
     * @return
     */
    public static boolean cust_appt_check(int  cus_ID) {
        try {
            ObservableList<Apppointments> appointments = Appointment_db.get_cust_Appts(cus_ID);
            if (appointments != null && appointments.size() > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }






}
