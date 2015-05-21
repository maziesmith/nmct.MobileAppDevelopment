package be.howest.nmct.politiekortrijk.data;

import android.database.Cursor;

/**
 * Created by kristofcolpaert on 21/05/15.
 */
public class SnelheidsMetingenData
{
    public static Cursor snelheidsMetingenCursor;

    public static Cursor getSnelheidsMetingenCursor()
    {
        return snelheidsMetingenCursor;
    }

    public static void setSnelheidsMetingenCursor(Cursor snelheidsMetingenCursor)
    {
        SnelheidsMetingenData.snelheidsMetingenCursor = snelheidsMetingenCursor;
    }
}
