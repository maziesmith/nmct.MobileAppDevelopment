package kristofcolpaert.com.week1oefening3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kristofcolpaert on 09/02/15.
 */
public class Student implements Comparable<Student>{
    private String emailStudent;
    private HashMap<String, ModulePunt> scoresStudent;

    public String getEmailStudent() {
        return emailStudent;
    }

    public HashMap<String, ModulePunt> getScoresStudent() {
        return scoresStudent;
    }

    public void setEmailStudent(String emailStudent) {
        this.emailStudent = emailStudent;
    }

    public Student(String emailStudent, HashMap<String, ModulePunt> scoresStudent) {
        this.emailStudent = emailStudent;
        this.scoresStudent = scoresStudent;
    }

    public void voegScoreToe(String sModuleNaam, double score)
    {
        ModulePunt mdp = new ModulePunt(sModuleNaam, 6, score);
        scoresStudent.put(sModuleNaam, mdp);
    }

    public float getTotaleScoreStudent()
    {
        int somStudiePunten = 0;
        for(String key : scoresStudent.keySet())
        {
            somStudiePunten += scoresStudent.get(key).getAantalStudiePunten();
        }

        float totaalPercentage = 0.0f;
        for(String key : scoresStudent.keySet())
        {
            float moduleGewicht = scoresStudent.get(key).getAantalStudiePunten() / somStudiePunten;
            totaalPercentage += scoresStudent.get(key).getScore() * moduleGewicht;
        }

        return totaalPercentage;
    }

    @Override
    public String toString() {
        return "Email: " + emailStudent + ", totaalpercentage: " + getTotaleScoreStudent();
    }

    public int compareTo(Student andereStudent) {
        if(andereStudent.getTotaleScoreStudent() > this.getTotaleScoreStudent())
            return 0;
        return 1;
    }

    public static List<Double> getScoresModule(List<Student> studenten, String moduleNaam)
    {
        List<Double> modulePunten = new ArrayList<Double>();
        for(Student student: studenten)
        {
            ModulePunt mp = student.getScoresStudent().get(moduleNaam);
            if(mp != null)
            {
                modulePunten.add(mp.getScore());
            }
        }
        return modulePunten;
    }

    public static double getGemiddeldeScoreModule(List<Student> studenten, String moduleNaam)
    {
        double sum = 0.0f;
        int count = 0;

        for(Student student : studenten)
        {
            ModulePunt mp = student.getScoresStudent().get(moduleNaam);
            if(mp != null)
            {
                sum += mp.getScore();
                count ++;
            }
        }

        return sum/count;
    }

    public static void sorteerStudenten(List<Student> studenten)
    {
        studenten
    }
}
