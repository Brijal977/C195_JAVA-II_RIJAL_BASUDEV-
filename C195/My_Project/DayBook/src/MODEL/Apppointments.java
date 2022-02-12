package MODEL;

import java.time.LocalDateTime;


public class Apppointments {

    /**
     * Local variables
     */
    private int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private LocalDateTime Start;
    private LocalDateTime End;
    private Integer Customer_ID;
    private Integer User_ID;
    private Integer Contact_ID;


    /**
     *
     * @param appointment_ID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customer_ID
     * @param user_ID
     * @param contact_ID
     */
    public Apppointments(int appointment_ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customer_ID, Integer user_ID, Integer contact_ID) {
        Appointment_ID = appointment_ID;
        Title = title;
        Description = description;
        Location = location;
        Type = type;
        Start = start;
        End = end;
        Customer_ID = customer_ID;
        User_ID = user_ID;
        Contact_ID = contact_ID;
    }

    public Apppointments(String type, String month, int count) {

    }


    /**
     * Getter
     * @return Appointment_ID
     */
    public int getAppointment_ID() {return Appointment_ID;}

    /**
     * Getter
     * @return Title
     */
    public String getTitle() {return Title;}

    /**
     * Getter
     * @return Description
     */
    public String getDescription() {return Description;}

    /**
     * Getter
     * @return Location
     */
    public String getLocation() {return Location;}

    /**
     * Getter
     * @return Type
     */
    public String getType() {return Type;}

    /**
     * Getter
     * @return
     */
    public LocalDateTime getStart() {return Start;}

    /**
     * Getter
     * @return Start
     */
    public LocalDateTime getEnd() {return End;}

    /**
     * Getter
     * @return Customer_ID
     */
    public Integer getCustomer_ID() {return Customer_ID;}

    /**
     * Getter
     * @return User_ID
     */
    public Integer getUser_ID() {return User_ID;}

    /**
     * Getter
     * @return Contact_ID
     */
    public Integer getContact_ID() {return Contact_ID;}


}
