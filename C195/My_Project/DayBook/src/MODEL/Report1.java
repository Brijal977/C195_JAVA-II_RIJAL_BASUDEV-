package MODEL;

public class Report1 {

    /**
     * Local variables
     */
    public int Count;
    public String Type;
    public String Month;


    /**
     * Getter
     * @return Type
     */
    public String getType() {return Type;}

    /**
     * Getter
     * @return Month
     */
    public String getMonth() {return Month;}

    /**
     * Getter
     * @return Count
     */
    public int getCount() {return Count;}


    /**
     * Report1 Constructor
     * @param type type
     * @param month month
     * @param count count
     */
    public Report1(String type, String month, int count) {
        this.Month = month;
        this.Type = type;
        this.Count = count;
    }



}
