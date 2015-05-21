package be.howest.nmct.politiekortrijk.data;

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
 * Created by kristofcolpaert on 21/05/15.
 */
public class SnelheidsMetingenLoader extends AsyncTaskLoader<Cursor>
{
    private Cursor snelheidsMetingenCursor;
    private static Object lock = new Object();
    private String[] columns = new String[]
            {
                    BaseColumns._ID,
                    Contract.SnelheidsMetingenContract.COLUMN_JAAR,
                    Contract.SnelheidsMetingenContract.COLUMN_MAAND,
                    Contract.SnelheidsMetingenContract.COLUMN_STRAAT,
                    Contract.SnelheidsMetingenContract.COLUMN_POSTCODE,
                    Contract.SnelheidsMetingenContract.COLUMN_GEMEENTE,
                    Contract.SnelheidsMetingenContract.COLUMN_AANTAL_CONTROLES,
                    Contract.SnelheidsMetingenContract.COLUMN_GEPASSEERDE_VOERTUIGEN,
                    Contract.SnelheidsMetingenContract.COLUMN_VTG_IN_OVERTREDING,
                    Contract.SnelheidsMetingenContract.COLUMN_X,
                    Contract.SnelheidsMetingenContract.COLUMN_Y
            };
    private static final String url = "http://data.kortrijk.be/snelheidsmetingen/pz_vlas.json";

    public SnelheidsMetingenLoader(Context context)
    {
        super(context);
    }

    @Override
    protected void onStartLoading()
    {
        if(snelheidsMetingenCursor == null)
            forceLoad();
    }

    @Override
    public Cursor loadInBackground()
    {
        snelheidsMetingenCursor = loadData();
        return snelheidsMetingenCursor;
    }

    private Cursor loadData()
    {
        synchronized(lock)
        {
            if(snelheidsMetingenCursor != null)
                return snelheidsMetingenCursor;

            MatrixCursor temporaryCursor = new MatrixCursor(columns);
            InputStream inputStream = null;
            JsonReader jsonReader = null;

            int id = 1;
            int jaar = 0;
            int maand = 0;
            String straat = "";
            int postcode = 0;
            String gemeente = "";
            int aantalControles = 0;
            int gepasseerdeVoertuigen = 0;
            int vtgInOvertreding = 0;
            double x = 0.0;
            double y = 0.0;

            try
            {
                inputStream = new URL(url).openStream();
                jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

                jsonReader.beginArray();
                while(jsonReader.hasNext())
                {
                    jsonReader.beginObject();
                    while(jsonReader.hasNext())
                    {
                        String name = jsonReader.nextName();

                        if(name.equals("Jaar"))
                        {
                            jaar = jsonReader.nextInt();
                        }

                        else if(name.equals("Maand"))
                        {
                            maand = jsonReader.nextInt();
                        }

                        else if(name.equals("Straat"))
                        {
                            straat = jsonReader.nextString();
                        }

                        else if(name.equals("Postcode"))
                        {
                            postcode = jsonReader.nextInt();
                        }

                        else if(name.equals("Gemeente"))
                        {
                            gemeente = jsonReader.nextString();
                        }

                        else if(name.equals("Aantal controles"))
                        {
                            aantalControles = jsonReader.nextInt();
                        }

                        else if(name.equals("Gepasseerde voertuigen"))
                        {
                            gepasseerdeVoertuigen = jsonReader.nextInt();
                        }

                        else if(name.equals("Vtg in overtreding"))
                        {
                            vtgInOvertreding = jsonReader.nextInt();
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

                    temporaryCursor.newRow()
                            .add(id)
                            .add(jaar)
                            .add(maand)
                            .add(straat)
                            .add(postcode)
                            .add(gemeente)
                            .add(aantalControles)
                            .add(gepasseerdeVoertuigen)
                            .add(vtgInOvertreding)
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
            return temporaryCursor;
        }
    }
}
