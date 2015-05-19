package be.howest.nmct.snelheidscontroleskortrijk.data;

import android.database.Cursor;

/**
 * Created by kristofcolpaert on 19/05/15.
 */
public class VelocityData
{
    private static Cursor velocityCursor;

    public static Cursor getVelocityCursor()
    {
        return velocityCursor;
    }

    public static void setVelocityCursor(Cursor velocityCursor)
    {
        VelocityData.velocityCursor = velocityCursor;
    }
}
