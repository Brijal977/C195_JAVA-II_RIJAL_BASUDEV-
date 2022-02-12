package DATA;

import MODEL.Apppointments;
import MODEL.Countries;
import MODEL.Customers;
import MODEL.Divisions;
import UTIL.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer_db {


    /**
     * returns the  ObservableList of all Customers in the SQL database
     *
     * @return Customer_List
     */
    public static ObservableList<Customers> get_All_Customers() {
        ObservableList<Customers> Customer_List = FXCollections.observableArrayList();
        try {
            String sql_query = "SELECT cs.Customer_ID, cs.Customer_Name, cs.Address, cs.Postal_Code, cs.Phone, cs.Division_ID, d.Division, d.Country_ID, ct.Country \n" +
                    "FROM ((customers cs\n" +
                    "inner join first_level_divisions d ON cs.Division_ID = d.Division_ID)\n" +
                    "inner join countries ct ON d.Country_ID = ct.Country_ID)";


            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql_query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int Customer_ID = rs.getInt("Customer_ID");
                String Customer_Name = rs.getString("Customer_Name");
                String Address = rs.getString("Address");
                String Postal_Code = rs.getString("Postal_Code");
                String Phone = rs.getString("Phone");

                int Division_ID = rs.getInt("Division_ID");
                String Division_Name = rs.getString("Division");
                int Country_ID = rs.getInt("Country_ID");
                String Country_Name = rs.getString("Country");
                Customers customer = new Customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID,Division_Name, Country_ID,Country_Name);
                Customer_List.add(customer);
            }

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Customer_List;

    }





    //======================================== add customer ============================================================

    /**
     * Inserts the new  records in Customer table in SQL database
     *
     * @param customer_Name customer_Name
     * @param address address
     * @param postal_Code postal_Code
     * @param phone phone
     * @param division_ID division_ID
     */

    public static void Add_Customer(String customer_Name, String address, String postal_Code, String phone, int division_ID )
    {
        try {

            String add_cust_query = "INSERT INTO customers VALUES (NULL, ?, ?, ?, ?, now(), 'user', now(), 'user', ?)";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(add_cust_query);
            PS.setString(1, customer_Name);
            PS.setString(2, address);
            PS.setString(3, postal_Code);
            PS.setString(4, phone);
            PS.setInt(5, division_ID);

            PS.execute();


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //======================================== update customer =========================================================

    /**
     * Updates the  existing records in Customer table in SQL database
     *
     * @param customer_Name customer_Name
     * @param address address
     * @param postal_Code postal_Code
     * @param phone phone
     * @param division_ID division_ID
     */
    public static void Update_Customer(String customer_Name, String address, String postal_Code, String phone, Integer division_ID, String customer_ID)
    {
        try {

            String update_cust_query = "UPDATE customers SET  Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(update_cust_query);
            PS.setString(1, customer_Name);
            PS.setString(2, address);
            PS.setString(3, postal_Code);
            PS.setString(4, phone);
            PS.setInt(5, division_ID);
            PS.setString(6, customer_ID);
            PS.execute();


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }





    //======================================== delete customer =========================================================

    /**
     * Deletes the Customer records from SQl database based on user provided Customer ID
     *
     * @param Customer_ID Customer_ID
     */
    public static void Delete_Customer(int Customer_ID)
    {
        try {
            String delete_cust_query = "DELETE FROM customers WHERE Customer_ID = (?)";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(delete_cust_query);
            PS.setInt(1, Customer_ID);
            PS.execute();


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }





    /**
     * returns the  ObservableList of all Countries in SQL database
     *
     * @return Country_List
     */
    //========================================get all countries ========================================================
    public static ObservableList<Countries> get_All_Countries() {
        ObservableList<Countries> Country_List = FXCollections.observableArrayList();
        try {
            String sql_query = "SELECT Country_ID, Country FROM countries";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(sql_query);
            ResultSet rs = PS.executeQuery();

            while(rs.next()) {
                int Country_ID = rs.getInt("Country_ID");
                String Country = rs.getString("Country");
                Countries country = new Countries(Country_ID, Country);
                Country_List.add(country);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Country_List;

    }




    /**
     * returns the  ObservableList of all First Level Divisions in SQL database
     *
     * @return Country_List
     */
    //========================================get all divisions ========================================================
    public static ObservableList<Divisions> get_All_Divisions() {
        ObservableList<Divisions> Division_List = FXCollections.observableArrayList();
        try {
            String sql_query = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(sql_query);
            ResultSet rs = PS.executeQuery();

            while(rs.next()) {
                int Division_ID = rs.getInt("Division_ID");
                String Division = rs.getString("Division");
                int Country_ID = rs.getInt("Country_ID");
                Divisions division = new Divisions(Division_ID, Division, Country_ID);
                Division_List.add(division);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Division_List;

    }



}
