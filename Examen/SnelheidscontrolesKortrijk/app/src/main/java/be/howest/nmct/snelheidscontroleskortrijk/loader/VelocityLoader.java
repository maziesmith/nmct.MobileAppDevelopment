package be.howest.nmct.snelheidscontroleskortrijk.loader;

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

import be.howest.nmct.snelheidscontroleskortrijk.contract.Contract;

/**
 * Created by kristofcolpaert on 19/05/15.
 */
public class VelocityLoader extends AsyncTaskLoader<Cursor>
{
    private Cursor velocityCursor;
    private static Object lock = new Object();
    private String[] columns = new String[]
    {
        BaseColumns._ID,
        Contract.VelocityContract.COLUMN_YEAR,
        Contract.VelocityContract.COLUMN_MONTH,
        Contract.VelocityContract.COLUMN_STREET,
        Contract.VelocityContract.COLUMN_ZIPCODE,
        Contract.VelocityContract.COLUMN_CITY,
        Contract.VelocityContract.COLUMN_NUMBER_OF_TRACKINGS,
        Contract.VelocityContract.COLUMN_NUMBER_OF_CARS,
        Contract.VelocityContract.COLUMN_NUMBER_OF_OFFENSES,
        Contract.VelocityContract.COLUMN_X,
        Contract.VelocityContract.COLUMN_Y
    };
    private static final String url = "http://data.kortrijk.be/snelheidsmetingen/pz_vlas.json";

    public VelocityLoader(Context context)
    {
        super(context);
    }

    @Override
    protected void onStartLoading()
    {
        if(velocityCursor == null)
            forceLoad();
    }

    @Override
    public Cursor loadInBackground()
    {
        velocityCursor = loadVelocityData();
        return velocityCursor;
    }

    private Cursor loadVelocityData()
    {
        synchronized(lock)
        {
            if(velocityCursor != null)
                return velocityCursor;

            MatrixCursor temporaryVelocityCursor = new MatrixCursor(columns);
            InputStream inputStream = null;
            JsonReader jsonReader = null;

            try
            {
                inputStream = new URL(url).openStream();
                jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

                int id = 1;
                int year = 0;
                int month = 0;
                String street = "";
                String zipcode = "";
                String city = "";
                int numberOfTrackings = 0;
                int numberOfCars = 0;
                int numberOfOffenses = 0;
                double x = 0.0;
                double y = 0.0;

                //Start reading
                jsonReader.beginArray();
                while(jsonReader.hasNext())
                {
                    jsonReader.beginObject();
                    while(jsonReader.hasNext())
                    {
                        String name = jsonReader.nextName();

                        if(name.equals("Jaar"))
                        {
                            year = jsonReader.nextInt();
                        }

                        else if(name.equals("Maand"))
                        {
                            month = jsonReader.nextInt();
                        }

                        else if(name.equals("Straat"))
                        {
                            street = jsonReader.nextString();
                        }

                        else if(name.equals("Postcode"))
                        {
                            zipcode = jsonReader.nextString();
                        }

                        else if(name.equals("Gemeente"))
                        {
                            city = jsonReader.nextString();
                        }

                        else if(name.equals("Aantal controles"))
                        {
                            numberOfTrackings = jsonReader.nextInt();
                        }

                        else if(name.equals("Gepasseerde voertuigen"))
                        {
                            numberOfCars = jsonReader.nextInt();
                        }

                        else if(name.equals("Vtg in overtreding"))
                        {
                            numberOfOffenses = jsonReader.nextInt();
                        }

                        else if(name.equals("X"))
                        {
                            x = jsonReader.nextDouble();
                        }

                        else if(name.equals("Y"))
                        {
                            y = jsonReader.nextDouble();
                        }

                        else
                        {
                            jsonReader.skipValue();
                        }
                    }
                    jsonReader.endObject();

                    MatrixCursor.RowBuilder row = temporaryVelocityCursor.newRow()
                            .add(id)
                            .add(year)
                            .add(month)
                            .add(street)
                            .add(zipcode)
                            .add(city)
                            .add(numberOfTrackings)
                            .add(numberOfCars)
                            .add(numberOfOffenses)
                            .add(x)
                            .add(y);
                    id++;
                }
                jsonReader.endArray();
            }

            catch(Exception ex)
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
            return temporaryVelocityCursor;
        }
    }
}
