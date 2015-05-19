package be.howest.nmct.snelheidscontroleskortrijk2.loader;

/**
 * Created by kristofcolpaert on 19/05/15.
 */
public class Contract
{
    public interface SnelheidsControlesContract
    {
        public final static String COLUMN_JAAR = "Jaar";
        public final static String COLUMN_MAAND = "Maand";
        public final static String COLUMN_STRAAT ="Straat";
        public final static String COLUMN_POSTCODE = "Postcode";
        public final static String COLUMN_GEMEENTE = "Gemeente";
        public final static String COLUMN_AANTAL_CONTROLES = "AantalControles";
        public final static String COLUMN_GEPASSEERDE_VOERTUIGEN = "GepasseerdeVoertuigen";
        public final static String COLUMN_VTG_IN_OVERTREDING = "VtgInOvertreding";
        public final static String COLUMN_X = "X";
        public final static String COLUMN_Y = "Y";
    }
}
