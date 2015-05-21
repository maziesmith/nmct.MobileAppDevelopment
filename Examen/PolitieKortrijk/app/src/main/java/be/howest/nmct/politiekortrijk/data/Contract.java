package be.howest.nmct.politiekortrijk.data;

/**
 * Created by kristofcolpaert on 21/05/15.
 */
public class Contract
{
    public interface SnelheidsMetingenContract
    {
        public static final String COLUMN_JAAR = "Jaar";
        public static final String COLUMN_MAAND = "Maand";
        public static final String COLUMN_STRAAT = "Straat";
        public static final String COLUMN_POSTCODE = "Postcode";
        public static final String COLUMN_GEMEENTE = "Gemeente";
        public static final String COLUMN_AANTAL_CONTROLES = "AantalControles";
        public static final String COLUMN_GEPASSEERDE_VOERTUIGEN = "GepasseerdeVoertuigen";
        public static final String COLUMN_VTG_IN_OVERTREDING = "VtgInOvertreding";
        public static final String COLUMN_X = "x";
        public static final String COLUMN_Y = "y";
    }
}
