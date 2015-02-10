package kristofcolpaert.com.week1oefening3;

/**
 * Created by kristofcolpaert on 09/02/15.
 */
public class ModulePunt
{
    private String moduleNaam;
    private int aantalStudiePunten;
    private double score;

    public String getModuleNaam() {
        return moduleNaam;
    }

    public void setModuleNaam(String moduleNaam) {
        this.moduleNaam = moduleNaam;
    }

    public int getAantalStudiePunten() {
        return aantalStudiePunten;
    }

    public void setAantalStudiePunten(int aantalStudiePunten) {
        this.aantalStudiePunten = aantalStudiePunten;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public ModulePunt()
    {
        this("Design", 6, 16);
    }

    public ModulePunt(String moduleNaam, int aantalStudiePunten, double score)
    {
        this.moduleNaam = moduleNaam;
        this.aantalStudiePunten = aantalStudiePunten;
        this.score = score;
    }

    @Override
    public String toString()
    {
        return "Vak: " + getModuleNaam() + ", Studiepunten:" + ", Score:" + getScore();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModulePunt that = (ModulePunt) o;

        if (moduleNaam != null ? !moduleNaam.equals(that.moduleNaam) : that.moduleNaam != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return moduleNaam != null ? moduleNaam.hashCode() : 0;
    }
}


