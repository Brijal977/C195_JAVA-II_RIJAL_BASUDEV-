package DATA;
import MODEL.Apppointments;
import MODEL.Report1;
import MODEL.Report3;
import UTIL.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Reports_db {

    /**
     * returns the  ObservableList of all appointments by user's chosen Month and Type
     * @return r1List
     */
    public static ObservableList<Report1> Appt_by_monthtype() {
        ObservableList<Report1> r1List = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Type, MONTHNAME(Start) as Month, count(*) AS Count " +
            "FROM appointments GROUP BY Type, MONTHNAME(Start) ORDER BY Type, Start;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String Type = rs.getString("Type");
                String Month = rs.getString("Month");
                int Count = rs.getInt("Count");


                Report1 report1 = new Report1(Type,Month,Count);
               r1List.add(report1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

       return r1List;

    }


    /**
     * returns the  ObservableList of all Customers by user's chosen Country
     * @return r3List
     */
    public static ObservableList<Report3> Cusomer_by_Country() {
        ObservableList<Report3> r3List = FXCollections.observableArrayList();

        try {
            String sql = "SELECT C.Customer_ID, C.Customer_Name,  C.Postal_Code, C.Phone,  CU.Country, D.Division, C.Address\n" +
                    "from customers C\n" +
                    "JOIN first_level_divisions D\n" +
                    "ON C.Division_ID = D.Division_ID\n" +
                    "Join countries CU\n" +
                    "ON CU.Country_ID = D.COUNTRY_ID;";


            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Integer Customer_ID = rs.getInt("Customer_ID");
                String Customer_Name = rs.getString("Customer_Name");
                String Postal_Code = rs.getString("Postal_Code");
                String Phone = rs.getString("Phone");
                String Country = rs.getString("Country");
                String Division = rs.getString("Division");
                String Address = rs.getString("Address");




                Report3 report3 = new Report3(Customer_ID,Customer_Name,Postal_Code,Phone,Country,Division,Address);
                r3List.add(report3);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return r3List;

    }








}