package be.howest.nmct.studentenhuizen.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v4.content.AsyncTaskLoader;
import android.util.JsonReader;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by kristofcolpaert on 20/05/15.
 */
public class StudentHuizenLoader extends AsyncTaskLoader<Cursor>
{
    private Cursor studentHuizenCursor;
    private final String url = "http://data.kortrijk.be/studentenvoorzieningen/koten.json";
    private static Object lock = new Object();
    private String[] columns;

    public StudentHuizenLoader(Context context)
    {
        super(context);

        columns = new String[]
        {
            BaseColumns._ID,
            Contract.StudentHuizenContract.COLUMN_ADRES,
            Contract.StudentHuizenContract.COLUMN_HUISNR,
            Contract.StudentHuizenContract.COLUMN_GEMEENTE,
            Contract.StudentHuizenContract.COLUMN_AANTAL_KAMERS
        };
    }

    @Override
    protected void onStartLoading()
    {
        if(studentHuizenCursor == null)
            forceLoad();
    }

    @Override
    public Cursor loadInBackground()
    {
        synchronized (lock)
        {
            MatrixCursor cursor = new MatrixCursor(columns);
            JsonReader jsonReader = null;
            InputStream inputStream = null;

            try
            {
                inputStream = new URL(url).openStream();
                jsonReader = new JsonReader(new InputStreamReader(inputStream));

                int id = 0;
                String adres = "";
                String huisnummer = "";
                String gemeente = "";
                int aantalKamers = 0;

                jsonReader.beginArray();
                while(jsonReader.hasNext())
                {
                    jsonReader.beginObject();
                    while(jsonReader.hasNext())
                    {
                        String name = jsonReader.nextName();

                        if(name.equals("ADRES"))
                        {
                            adres = jsonReader.nextString();
                        }

                        else if(name.equals("HUISNR"))
                        {
                            huisnummer = jsonReader.nextString();
                        }

                        else if(name.equals("GEMEENTE"))
                        {
                            gemeente = jsonReader.nextString();
                        }

                        else if(name.equals("aantal kamers"))
                        {
                            aantalKamers = jsonReader.nextInt();
                        }

                        else
                        {
                            jsonReader.skipValue();
                        }
                    }
                    jsonReader.endObject();

                    cursor.newRow()
                            .add(id)
                            .add(adres)
                            .add(huisnummer)
                            .add(gemeente)
                            .add(aantalKamers);
                    id++;
                }
                jsonReader.endArray();
            }

            catch (Exception ex)
            {
                Log.d("An error occured: ", ex.getMessage());
            }

            finally
            {
                try
                {
                    jsonReader.close();
                }

                catch(Exception ex)
                {
                    Log.d("An error occured: ", ex.getMessage());
                }

                try
                {
                    inputStream.close();
                }

                catch(Exception ex)
                {
                    Log.d("An error occured: ", ex.getMessage());
                }
            }

            this.studentHuizenCursor = cursor;
            return studentHuizenCursor;
        }
    }
}
