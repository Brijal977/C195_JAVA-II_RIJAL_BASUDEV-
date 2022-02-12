package MODEL;

public class Contacts {
    private Integer Contact_ID;
    private String Contact_Name;

    /**
     *
     * @param contact_ID contact_ID
     * @param contact_Name contact_Name
     */
    public Contacts(Integer contact_ID, String contact_Name) {
        Contact_ID = contact_ID;
        Contact_Name = contact_Name;
    }

    /**
     * Getter
     * @return Contact_ID
     */
    public Integer getContact_ID() {return Contact_ID;}


    /**
     * Getter
     * @return Contact_Name
     */
    public String getContact_Name() {return Contact_Name;}


    /**
     *
     * @return Contact_Name (string)
     */
    @Override
    public String toString() {
        return Contact_Name;
    }
}
