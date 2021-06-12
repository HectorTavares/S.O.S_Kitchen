package util;

import java.sql.Date;
import java.time.LocalDate;

public class ConversaoDeData {

    public static LocalDate dateToLocalDate(Date dataEmDate) {
        try{
            return dataEmDate.toLocalDate();
        }catch (NullPointerException e){
            return null;
        }
    }

    public static Date localDateToDate(LocalDate dataEmLocalDate) {
        try{
            return Date.valueOf(dataEmLocalDate);
        }catch (NullPointerException e){
            return null;
        }

    }

}

