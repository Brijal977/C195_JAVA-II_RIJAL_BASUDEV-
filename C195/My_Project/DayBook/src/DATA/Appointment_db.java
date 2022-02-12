package DATA;

import CONTROLLER.Add_Appt_C;
import MODEL.Apppointments;
import MODEL.Contacts;
import MODEL.Customers;
import MODEL.Users;
import UTIL.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.*;




public class Appointment_db {



//================================QUICK LISTS ==========================================================================
    /**
     * reusable  ObservableList to be user through the program
     */

    public static ObservableList<Contacts> allcontacts = Contacts_db.get_All_Contacts();
    public static ObservableList<Customers> allcustomers = Customer_db.get_All_Customers();
    public static ObservableList<Users> allusers = User_db.get_All_Users();
    public static ObservableList<String> appt_types = FXCollections.observableArrayList("Initial","Follow up","Lab","Radiology");






//==================================  VIEW BY FILTERS ======================================

    /**
     * returns the  ObservableList of all / monthly / weekly  Appointments  in the SQL database
     *
     *
     * @param view_filter view_filter
     * @return aList - Appointments  ObservableList
     */
    public static ObservableList<Apppointments> get_All_Appts(String view_filter) {
        ObservableList<Apppointments> aList = FXCollections.observableArrayList();

        try {

            String sql = null;

            if (view_filter.equals("all")) {
                sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments";}


            else if (view_filter.equals("month")){
                sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments WHERE MONTH(Start) = MONTH(current_date())";}


            else if(view_filter.equals("week")){
                sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments WHERE WEEK(Start) = WEEK(current_date())";}


            else if (view_filter.equals("customer_ID")){

                sql = "SELECT * FROM appointments WHERE Customer_ID = current_customer_id";
            }


            else{
                System.out.printf("invalid input");}


            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Location = rs.getString("Location");
                String Type = rs.getString("Type");


                LocalDateTime Start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime End = rs.getTimestamp("End").toLocalDateTime();


                int Customer_ID = (rs.getInt("Customer_ID"));
                int User_ID = rs.getInt("User_ID");
                int Contact_ID = rs.getInt("Contact_ID");
                Apppointments a = new Apppointments(Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID);
                aList.add(a);


            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return aList;

    }


    /**
     * returns the  ObservableList of   Appointments  associated with a particular customer in the SQL database
     *
     * @param Cust_id Cust_id
     * @return aList -  - Appointments  ObservableList
     * @throws SQLException
     */
    public static ObservableList<Apppointments> get_cust_Appts(int Cust_id) throws SQLException {
        ObservableList<Apppointments> aList = FXCollections.observableArrayList();
        String sql = ("SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Customer_ID= '" + Cust_id + "'");

        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Location = rs.getString("Location");
                String Type = rs.getString("Type");


                LocalDateTime Start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime End = rs.getTimestamp("End").toLocalDateTime();


                int Customer_ID = (rs.getInt("Customer_ID"));
                int User_ID = rs.getInt("User_ID");
                int Contact_ID = rs.getInt("Contact_ID");
                Apppointments a = new Apppointments(Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID);
                aList.add(a);

                //System.out.print(aList);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return  null;
        }

        return aList;

    }




 // ==================================================== 15 minute Alert ===============================================

    /**
     * Alerts user of upcoming appointments if they start within 15 minute of login
     * Notifies if there is no upcoming any appointments   within 15 minute of login
     * Displays Brief Description  of upcoming appointments
     *
     *
     * @throws SQLException
     */
    public static void alert_Appt() throws SQLException{
        LocalDateTime local_now = LocalDateTime.now();
        LocalDateTime local_now_plus15 = local_now.plusMinutes(15);
        ObservableList<Apppointments> upcoming_appts = FXCollections.observableArrayList();


        ObservableList<Apppointments> all_appointments = get_All_Appts("all");

        Alert appt_alert = new Alert(Alert.AlertType.INFORMATION);
        appt_alert.setTitle("appointment Alert");

        if (all_appointments != null) {
            for (Apppointments appt: all_appointments) {
                if (appt.getStart().isAfter(local_now) && appt.getStart().isBefore(local_now_plus15)) {
                    upcoming_appts.add(appt);

                    if(upcoming_appts.size() > 0) {

                        appt_alert.setContentText(" There is upcoming appointment with in next 15 minute ! \n\n" +
                                "*  Appointment ID :-  " + appt.getAppointment_ID() + "\n" +
                                "*  Appointment Start time :-  " + appt.getStart() + "\n" +
                                "*  Appointment End time :-  " + appt.getStart() + "\n\n");


                    }else{
                        appt_alert.setContentText(" There is  no upcoming appointment with in next 15 minute ! \n\n" );
                    }

                    appt_alert.setResizable(true);
                    appt_alert.showAndWait();
                }



            }
        }


    }




    //====================================ADD===========================================================================

    /**
     * Inserts the new  records in Appointments table in SQL database
     *
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param start start
     * @param end end
     * @param customer_ID customer_ID
     * @param user_ID user_ID
     * @param contact_ID contact_ID
     */

    public static void addAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customer_ID, Integer user_ID, Integer contact_ID) {
        try {
            String sql = "INSERT INTO appointments VALUES (NULL, ?, ?, ?, ?, ?, ?, now(), 'user', now(), 'user', ?, ?, ?)";
            PreparedStatement psaa = JDBC.getConnection().prepareStatement(sql);
            psaa.setString(1, title);
            psaa.setString(2, description);
            psaa.setString(3, location);
            psaa.setString(4, type);

            psaa.setTimestamp(5, Timestamp.valueOf(start));
            psaa.setTimestamp(6, Timestamp.valueOf(end));

            psaa.setInt(7, customer_ID);
            psaa.setInt(8, user_ID);
            psaa.setInt(9, contact_ID);
            psaa.execute();


        } catch (SQLException var11) {
            var11.printStackTrace();
        }

    }





    /**
     * Inserts the existing  records in Appointments table in SQL database
     *
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param start start
     * @param end end
     * @param customer_ID customer_ID
     * @param user_ID user_ID
     * @param contact_ID contact_ID
     */

    public static void updateAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customer_ID, Integer user_ID, Integer contact_ID, String appointment_ID) {
        try {
            String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?";
            PreparedStatement psua = JDBC.getConnection().prepareStatement(sql);
            psua.setString(1, title);
            psua.setString(2, description);
            psua.setString(3, location);
            psua.setString(4, type);
            psua.setTimestamp(5, Timestamp.valueOf(start));
            psua.setTimestamp(6, Timestamp.valueOf(end));
            psua.setInt(7, customer_ID);
            psua.setInt(8, user_ID);
            psua.setInt(9, contact_ID);
            psua.setString(10, appointment_ID);
            psua.execute();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

    }


    /**
     * Deletes the records from Appointments table in SQL database
     *
     * @param Appointment_ID
     */
    public static void delete_Appointment(int Appointment_ID) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = (?)";
            PreparedStatement psda = JDBC.getConnection().prepareStatement(sql);
            psda.setInt(1, Appointment_ID);
            psda.execute();
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }






}
