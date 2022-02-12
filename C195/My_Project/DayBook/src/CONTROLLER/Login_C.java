package CONTROLLER;

import DATA.Appointment_db;
import UTIL.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;

import MODEL.Users;
import UTIL.Tools;
import UTIL.JDBC;


public class Login_C implements Initializable {

//========================= VARIABLE MAPPING============================================================================

    /**
     * VARIABLE MAPPING : assign variables to  Login.fxml file controls
     * (Controls used: Label, Textbox, Buttons.)
     *
     */

    @FXML private Label signin_lbl;
    @FXML private Label userName_lbl;
    @FXML private Label password_lbl;
    @FXML private TextField UserName_txt;
    @FXML private TextField Password_txt;
    @FXML public Label timezone;
    @FXML public Label tz;
    @FXML public Button Login_bttn;


    ResourceBundle rb = ResourceBundle.getBundle("Language");
    private String alertTitle;
    private String alertHeader;
    private String alertContext;




//=================================== BUTTON FUNCTIONS =================================================================

    /**
     * Navigates to Main view UI on valid login in attempt
     * Displays error message in invalid login attempt
     *
     * @param event
     * @throws IOException if exception  occurs
     * @throws SQLException if exception  occurs
     */

    @FXML
    public void Login_bttn_clicked(ActionEvent event) throws IOException, SQLException {
        String username = UserName_txt.getText();
        String password = Password_txt.getText();
        boolean validUser = Validator.valid_login(username, password);


       if(validUser) {
           //this.Active_user_name = username;
           Tools.scene_switch(event,"Main_View","Main_View");

       } else {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle(alertTitle);
           alert.setHeaderText(alertHeader);
           alert.setContentText(alertContext);
           alert.showAndWait();

        }
   }





//=================================== OTHER FUNCTIONS ================================================
    /**
     * holds the user_name of current logged in user
     */
        public static Users cur_User;

    /**
     *
     * @return cur_User
     */
    public static Users get_cur_user() {
            return cur_User;
        }






//===================================INITIALIZATIONS==================================================

    /**
     * initializes the login form
     * sets locale based on user's local machine's default setting
     * Change Display language based on user's local machine's default language setting
     *
     * @param url URL
     * @param rb  ResourceBundle
     */


    @Override
        public void initialize (URL url, ResourceBundle rb){

            Locale locale = Locale.getDefault();
            rb = ResourceBundle.getBundle("Language", locale);

            signin_lbl.setText(rb.getString("sign_in"));
            userName_lbl.setText(rb.getString("username_lbl"));
            password_lbl.setText(rb.getString("password_lbl"));

            UserName_txt.setPromptText(rb.getString("username_prompt"));
            Password_txt.setPromptText(rb.getString("password_prompt"));

            Login_bttn.setText(rb.getString("login"));

            timezone.setText(rb.getString("timezone"));
            String displayName = ZoneId.systemDefault().toString();
            tz.setText(displayName);


            alertTitle = rb.getString("alertTitle");
            alertHeader = rb.getString("alertHeader");
            alertContext = rb.getString("alertContext");


        }


//==============================   Logger   ============================================================================

    /**
     * logger method : creates a log of user's login activity.
     * records : username,  date, timestamp and login attempt activity.
     * appends new entries on existing file
     *
     * @param username User Name
     * @param loginAttempt
     * @throws IOException if exception  occurs
     */
    public static void write_log(String username, String loginAttempt ) throws IOException {
            
        LocalDateTime now = LocalDateTime.now();
        LocalDate Date = LocalDate.now();
        LocalTime Time = LocalTime.now();
        String FILENAME = "login_activity.txt";



        PrintWriter log_file = new PrintWriter(new FileWriter(FILENAME, true));

            log_file.println("--------------------------------------------------------------\n" );
            
            log_file.println("      Username : "+ username );
            System.out.println("      Username : "+ username );
           
            log_file.println("          Date : "+ Date );
            System.out.println("          Date : "+ Date);
            
            log_file.println("          Time : "+ Time );
            System.out.println("          Time : "+ Time);
            
            log_file.println(" Login activity : "+ loginAttempt+"\n");
            System.out.println(" Login activity : "+ loginAttempt+"\n");

            log_file.close();


        
    }

}





























