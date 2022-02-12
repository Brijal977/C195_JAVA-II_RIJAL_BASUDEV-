package MODEL;

public class Countries {

    /**
     * local variables
     */
    private Integer Country_ID;
    private String Country;


    /**
     * Countries Constructor
     * @param country_ID country_ID
     * @param country country
     */
    public Countries(Integer country_ID, String country) {
        Country_ID = country_ID;
        Country = country;
    }


    /**
     * Getter
     * @return Country_ID
     */
    public Integer getCountry_ID() {return Country_ID;}

    /**
     * Getter
     * @return Country
     */
    public  String getCountry_Name() {return Country;}




    /**
     *
     * @return Country (string)
     */
    @Override
    public String toString() {
        return Country;
    }

}
