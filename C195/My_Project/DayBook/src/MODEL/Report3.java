package MODEL;

public class Report3 {

    /**
     * Local variables
     */
    public int Customer_ID;
    public String Customer_Name;
    public String Postal_Code;
    public String Phone;
    public String Country;
    public String Division;
    public String Address;


    /**
     *
     * @param customer_ID customer_ID
     * @param customer_name customer_name
     * @param postal_code postal_code
     * @param phone phone
     * @param country country
     * @param division  division
     * @param st_address st_address
     */
    public Report3(int customer_ID, String customer_name, String postal_code, String phone, String country, String division, String st_address) {
        Customer_ID = customer_ID;
        Customer_Name = customer_name;
        Postal_Code = postal_code;
        Phone = phone;
        Country = country;
        Division = division;
        Address = st_address;
    }


    /**
     * Getter
     * @return Customer_ID
     */
    public int getCustomer_ID() {return Customer_ID;}

    /**
     *Getter
     * @return Customer_Name
     */
    public String getCustomer_Name() {return Customer_Name;}

    /**
     *Getter
     * @return Postal_Code
     */
    public String getPostal_Code() {return Postal_Code;}

    /**
     *Getter
     * @return Phone
     */
    public String getPhone() {return Phone;}

    /**
     *Getter
     * @return Country
     */
    public String getCountry() {return Country;}

    /**
     *Getter
     * @return Division
     */
    public String getDivision() {return Division;}

    /**
     *Getter
     * @return Address
     */
    public String getAddress() {return Address;}



}
