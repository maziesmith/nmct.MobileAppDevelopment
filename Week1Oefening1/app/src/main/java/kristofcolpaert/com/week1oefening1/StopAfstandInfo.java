package kristofcolpaert.com.week1oefening1;

/**
 * Created by kristofcolpaert on 09/02/15.
 */
public class StopAfstandInfo {
    /**
     * Enkel klasses starten in Java met een hoofdletter.
     * Methodes en variabelen met een kleine letter.
     */
    private int snelheid;
    private float reactietijd;
    private float stopafstand;
    private WegType wegType;

    public StopAfstandInfo()
    {
        this(100, 0.5f, 0.0f, WegType.DROOG);
    }

    public StopAfstandInfo(int snelheid, float reactietijd, float stopafstand, WegType wegType) {
        this.snelheid = snelheid;
        this.reactietijd = reactietijd;
        this.stopafstand = stopafstand;
        this.wegType = wegType;
    }

    public int getSnelheid() {
        return snelheid;
    }

    public void setSnelheid(int snelheid) {
        this.snelheid = snelheid;
    }

    public float getReactietijd() {
        return reactietijd;
    }

    public void setReactietijd(float reactietijd) {
        this.reactietijd = reactietijd;
    }

    public WegType getWegType() { return wegType; }

    public void setWegType(WegType wegType) { this.wegType = wegType; }

    /**
     * Geeft de remafstand terug in meter op basis van de formule:
     * snelheid * reactietijd + (snelheid)^2 / (2 * remvertraging)
     *
     * @return remafstand in meter
     */
    public float getStopafstand() {
        float tempRemvertraging = getWegType().getRemAfstand();
        float snelheidInMeter = getSnelheid() * 1000/3600;
        return (snelheidInMeter * getReactietijd()) + (float) Math.pow(snelheidInMeter, 2) / (2 * tempRemvertraging);
    }

    @Override
    public String toString() {
        return "De stopafstand is :" + getStopafstand();
    }

    /**
     * Enum klasse, kan ook als apart bestand.
     */
    public enum WegType
    {
        DROOG(8.0f), NAT(5.0f);

        private float remAfstand;

        WegType(float remAfstand)
        {
            this.remAfstand = remAfstand;
        }

        public float getRemAfstand()
        {
            return remAfstand;
        }
    }
}
