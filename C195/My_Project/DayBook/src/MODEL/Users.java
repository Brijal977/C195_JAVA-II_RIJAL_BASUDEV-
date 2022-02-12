package MODEL;

public class Users {

    /**
     * Local variables
     */
    private int User_ID;
    private  String User_Name;
    private  String Password;


    /**
     * Users Constructor
     * @param user_id user_id
     * @param user_name user_name
     * @param password password
     */
    public Users(int user_id, String user_name, String password) {
        User_ID = user_id;
        User_Name = user_name;
        Password = password;
    }

    public Users() {

    }


    /**
     * Getter
     * @return User_ID
     */
    public  int getUser_ID() {return User_ID;}

    /**
     *Getter
     * @return User_Name
     */
    public String getUser_Name() {return User_Name;}

    /**
     *Getter
     * @return Password
     */
    public  String getPassword() {return Password;}


    /**
     * Setter
     * @param user_ID
     */
    public  void setUser_ID(Integer user_ID) {this.User_ID = user_ID;}

    /**
     * Setter
     * @param password
     */
    public void setPassword(String password) {this.Password = password;}

    /**
     * Setter
     * @param user_Name
     */
    public  void setUser_Name(String user_Name) {this.User_Name = user_Name;}


    /**
     *
     * @return User_ID (string)
     */

    @Override
    public String toString() {
        return String.valueOf(User_ID);
    }


}
