package DATA;

import MODEL.Contacts;
import UTIL.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Contacts_db {


    /**
     * returns the  ObservableList of all Contacts in the SQL database
     *
     * @return coList
     */
    public static ObservableList<Contacts> get_All_Contacts() {
        ObservableList<Contacts> coList = FXCollections.observableArrayList();
        try {
            String sqlgetallcontacts = "SELECT Contact_ID, Contact_Name FROM contacts";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlgetallcontacts);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int Contact_ID = rs.getInt("Contact_ID");
                String Contact_Name = rs.getString("Contact_Name");
                Contacts co = new Contacts(Contact_ID, Contact_Name);
                coList.add(co);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return coList;

    }

}
