package be.howest.nmct.snelheidscontroleskortrijk2.data;

import android.database.Cursor;

/**
 * Created by kristofcolpaert on 19/05/15.
 */
public class SnelheidsControlesData
{
    private static Cursor snelheidsControlesCursor;

    public static Cursor getSnelheidsControlesCursor()
    {
        return snelheidsControlesCursor;
    }

    public static void setSnelheidsControlesCursor(Cursor snelheidsControlesCursor)
    {
        SnelheidsControlesData.snelheidsControlesCursor = snelheidsControlesCursor;
    }
}
