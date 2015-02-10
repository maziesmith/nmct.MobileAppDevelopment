package kristofcolpaert.com.week1oefening1;

/**
 * Created by kristofcolpaert on 09/02/15.
 */
public class TestStopAfstandInfo {
    //psvm = automatisch deze methode genereren.
    public static void main(String[] args)
    {
        StopAfstandInfo sai = new StopAfstandInfo();
        sai.setSnelheid(100);
        sai.setReactietijd(1.5f);
        sai.setWegType(StopAfstandInfo.WegType.NAT);

        System.out.println(sai);
    }
}
