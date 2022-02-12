package DATA;

import MODEL.Users;
import UTIL.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class User_db {


    /**
     * returns the list of all users in SQL Database
     *
     * @return uList
     */


    public static ObservableList<Users> get_All_Users() {
        ObservableList<Users> uList = FXCollections.observableArrayList();
        try {
            String sqlgetallusers = "SELECT User_ID, User_Name, Password FROM users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlgetallusers);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int User_ID = rs.getInt("User_ID");
                String User_Name = rs.getString("User_Name");
                String Password = rs.getString("Password");
                Users u  = new Users(User_ID, User_Name, Password);
                uList.add(u);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return uList;

    }
}
