# AsyncTaskLoader

```
public class VelocityLoader extends AsyncTaskLoader<Cursor>
{
    private Cursor velocityCursor;
    private static Object lock = new Object();
    private String[] columns = new String[]
    {
        BaseColumns._ID,
        Contract.VelocityContract.year,
        Contract.VelocityContract.month,
        Contract.VelocityContract.street,
        Contract.VelocityContract.zipcode,
        Contract.VelocityContract.city,
        Contract.VelocityContract.numberOfTrackings,
        Contract.VelocityContract.numberOfCars,
        Contract.VelocityContract.numberOfOffenses,
        Contract.VelocityContract.x,
        Contract.VelocityContract.y
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
        // Load cursor in background
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
```