package MODEL;

public class Divisions {

    /**
     * Local variables
     */
    public Integer Division_ID;
    public String Division;
    public Integer Country_ID;


    /**
     * Getter
     * @return Division_ID
     */
    public Integer getDivision_ID() {return Division_ID;}

    /**
     * Getter
     * @return Division
     */
    public String getDivision() {return Division;}

    /**
     * Getter
     * @return Country_ID
     */
    public Integer getCountry_ID() {return Country_ID;}

    /**
     * Divisions Constructor
     * @param division_ID  division_ID
     * @param division division
     * @param country_ID country_ID
     */

    public Divisions(Integer division_ID, String division, Integer country_ID) {
        Division_ID = division_ID;
        Division = division;
        Country_ID = country_ID;
    }


    /**
     *
     * @return Division (string)
     */
    @Override
    public String toString() {
        return Division;
    }
}
