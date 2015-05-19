package be.howest.nmct.snelheidscontroleskortrijk.data;

/**
 * Created by kristofcolpaert on 19/05/15.
 */
public enum Month
{
    JANUARI(1, "Januari"), FEBRUARI(2, "Februari"), MAART(3, "Maart"), APRIL(4, "April"),
    MEI(5, "Mei"), JUNI(6, "Juni"), JULI(7, "Juli"), AUGUSTUS(8, "Augustus"), SEPTEMBER(9, "SEPTEMBER"),
    OKTOBER(10, "Oktober"), NOVEMBER(11, "November"), DECEMBER(12, "December");

    private String monthName;
    private int monthNumber;

    Month(int monthNumber, String monthName)
    {
        this.monthName = monthName;
        this.monthNumber = monthNumber;
    }

    public int getMonthNumber()
    {
        return monthNumber;
    }

    public static Month getMonthByNumber(int monthNumber)
    {
        for(Month month : Month.values())
        {
            if(month.monthNumber == monthNumber)
            {
                return month;
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return monthName;
    }
}
