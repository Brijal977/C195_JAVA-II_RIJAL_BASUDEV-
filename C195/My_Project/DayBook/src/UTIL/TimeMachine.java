package UTIL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.*;

public class TimeMachine {


    /**
     * Converts local Date - time to UTC time
     *
     * @param time LocalDatetime
     * @return UTC ZonedDatetime
     */
    public static ZonedDateTime LOCAL_to_UTC(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
    }


    /**
     *
     * @param LDT LocalDatetime
     * @return ETC ZonedDatetime
     */
    public static ZonedDateTime LOCAL_to_ETC(LocalDateTime LDT) {
        return LDT.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
    }


    /**
     * Generates the hour-minute string time intervals
     *
     * @return ObservableList (hours, Minutes)
     */
    public static ObservableList<String> Timeslots () {
        ObservableList<String> times = FXCollections.observableArrayList();

        String[] interval = {"00","15","30","45"};


        for(int i = 0; i < 24; i++) {
            for(int j = 0; j < 4; j++) {
                String time = String.format("%02d", i )+ ":" + interval[j];
                times.add(time);
            }
        }
        return times;
    }




}
