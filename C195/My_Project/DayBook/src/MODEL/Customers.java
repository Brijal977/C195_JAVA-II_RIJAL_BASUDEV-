package MODEL;


public class Customers {

    /**
     * Local variables
     */

    private  int Customer_ID;
    private  String Customer_Name;
    private  String Address;
    private  String Phone;
    private String Postal_Code;
    private  int Division_ID;
    private  int Country_ID;
    private  String Division_Name;
    private  String Country_Name;


    /**
     * Customers Constructor
     * @param customer_id customer_id;
     * @param customer_name customer_name
     * @param  address address
     * @param postal_code postal_code
     * @param phone phone
     * @param division_id division_id
     * @param division_name division_name
     * @param country_id country_id
     * @param country_name country_name
     */
    public Customers(int customer_id, String customer_name, String address, String postal_code, String phone,
                     int division_id, String division_name, int country_id, String country_name) {

        Customer_ID = customer_id;
        Customer_Name = customer_name;
        Address = address;
        Phone = phone;
        Postal_Code = postal_code;
        Division_ID = division_id;
        Division_Name = division_name;
        Country_ID = country_id;
        Country_Name = country_name;
    }


    //====================================== GETTERS =================================================================

    /**
     * getter
     * @return Customer_ID
     */
    public  int getCustomer_ID() {return Customer_ID;}

    /**
     * getter
     * @return Customer_Name
     */
    public String getCustomer_Name() {return Customer_Name;}

    /**
     *getter
     * @return Address
     */
    public String getAddress() {return Address;}

    /**
     *getter
     * @return Postal_Code
     */
    public String getPostal_Code() {return Postal_Code;}

    /**
     *getter
     * @return Phone
     */
    public String getPhone() {return Phone;}

    /**
     *getter
     * @return Division_ID
     */
    public int getDivision_ID(){return Division_ID;}

    /**
     *getter
     * @return Country_ID
     */
    public int getCountry_ID(){return Country_ID;}

    /**
     *getter
     * @return Division_Name
     */
    public String getDivision_Name() {return Division_Name;}

    /**
     *getter
     * @return Country_Name
     */
    public String getCountry_Name() {return Country_Name;}


    /**
     * getter
     * @return Customer_ID (string)
     */
    @Override
    public String toString() {
        return String.valueOf(Customer_ID);
    }



}


