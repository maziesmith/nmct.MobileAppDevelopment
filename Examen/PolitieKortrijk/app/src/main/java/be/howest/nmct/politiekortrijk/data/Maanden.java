package be.howest.nmct.politiekortrijk.data;

/**
 * Created by kristofcolpaert on 21/05/15.
 */
public enum Maanden
{
    JANUARI(1, "Januari"), FEBRUARI(2, "Februari"), MAART(3, "Maart"), APRIL(4, "April"),
    MEI(5, "Mei"), JUNI(6, "Juni"), JULI(7, "Juli"), AUGUSTUS(8, "Augustus"), SEPTEMBER(9, "SEPTEMBER"),
    OKTOBER(10, "Oktober"), NOVEMBER(11, "November"), DECEMBER(12, "December");

    private String monthName;
    private int monthNumber;

    Maanden(int monthNumber, String monthName)
    {
        this.monthName = monthName;
        this.monthNumber = monthNumber;
    }

    public int getMonthNumber()
    {
        return monthNumber;
    }

    public static Maanden getMonthByNumber(int monthNumber)
    {
        for(Maanden month : Maanden.values())
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
