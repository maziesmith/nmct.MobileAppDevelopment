package be.howest.nmct.snelheidscontroleskortrijk2.data;

import java.util.ArrayList;

/**
 * Created by kristofcolpaert on 19/05/15.
 */
public enum Months
{
    JANUARI(1, "Januari"), FEBRUARI(2, "Februari"), MAART(3, "Maart"), APRIL(4, "April"), MEI(5, "Mei"),
    JUNI(6, "Juni"), JULI(7, "Juli"), AUGUSTUS(8, "Augustus"), SEPTEMBER(9, "September"),
    OKTOBER(10, "Oktober"), NOVEMBER(11, "November"), DECEMBER(12, "December");

    private int monthNumber;
    private String monthName;

    Months(int monthNumber, String monthName)
    {
        this.monthName = monthName;
        this.monthNumber = monthNumber;
    }

    public int getMonthNumber()
    {
        return monthNumber;
    }

    public String getMonthName()
    {
        return monthName;
    }

    public static Months getMonthByNumber(int number)
    {
        for(Months month : Months.values())
        {
            if(month.monthNumber == number)
            {
                return month;
            }
        }
        return null;
    }

    public static ArrayList<String> getListOfMonthNames()
    {
        ArrayList<String> arrayList = new ArrayList<String>();
        for(Months month : Months.values())
        {
            arrayList.add(month.getMonthName());
        }
        return arrayList;
    }
}
