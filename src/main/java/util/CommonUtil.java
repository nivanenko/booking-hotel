package util;


import java.sql.Date;

public class CommonUtil {
    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public static boolean isDatesOverLapped(Date startDate1, Date endDate1,
                                            Date startDate2, Date endDate2)
            throws NullPointerException {
        return startDate1.before(endDate2) && startDate2.before(endDate1);
    }
}
