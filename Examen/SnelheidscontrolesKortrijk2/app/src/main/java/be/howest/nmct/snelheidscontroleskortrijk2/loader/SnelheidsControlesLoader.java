package be.howest.nmct.snelheidscontroleskortrijk2.loader;

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
 * Created by kristofcolpaert on 19/05/15.
 */
public class SnelheidsControlesLoader extends AsyncTaskLoader<Cursor>
{
    private Cursor snelheidsControlesCursor;
    private static Object lock = new Object();
    private String[] columns;
    private final String url;

    public SnelheidsControlesLoader(Context context)
    {
        super(context);

        columns = new String[]
        {
                BaseColumns._ID,
                Contract.SnelheidsControlesContract.COLUMN_JAAR,
                Contract.SnelheidsControlesContract.COLUMN_MAAND,
                Contract.SnelheidsControlesContract.COLUMN_STRAAT,
                Contract.SnelheidsControlesContract.COLUMN_POSTCODE,
                Contract.SnelheidsControlesContract.COLUMN_GEMEENTE,
                Contract.SnelheidsControlesContract.COLUMN_AANTAL_CONTROLES,
                Contract.SnelheidsControlesContract.COLUMN_GEPASSEERDE_VOERTUIGEN,
                Contract.SnelheidsControlesContract.COLUMN_VTG_IN_OVERTREDING,
                Contract.SnelheidsControlesContract.COLUMN_X,
                Contract.SnelheidsControlesContract.COLUMN_Y
        };
        url = "http://data.kortrijk.be/snelheidsmetingen/pz_vlas.json";
    }

    @Override
    protected void onStartLoading()
    {
        if(snelheidsControlesCursor == null)
            forceLoad();
    }

    @Override
    public Cursor loadInBackground()
    {
        synchronized (lock)
        {
            JsonReader jsonReader = null;
            InputStream inputStream = null;

            MatrixCursor tempCursor = new MatrixCursor(columns);

            try
            {
                inputStream = new URL(url).openStream();
                jsonReader = new JsonReader(new InputStreamReader(inputStream));

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

                        else if(name.equals("PostCode"))
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

                    MatrixCursor.RowBuilder row = tempCursor.newRow()
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
                try {
                    jsonReader.close();
                } catch (Exception ex) {
                    Log.d("An error occured: ", ex.getMessage());
                }

                try {
                    inputStream.close();
                } catch (Exception ex) {
                    Log.d("An error occured: ", ex.getMessage());
                }
            }

            return tempCursor;
        }
    }
}
