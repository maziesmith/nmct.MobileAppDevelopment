package com.kristofcolpaert.week6oefening2.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student implements Comparable<Student> {

	private String naamStudent;
	private String voornaamStudent;

	private String emailStudent;
	private Map<String, ModulePunt> scoresStudent = new HashMap<String, ModulePunt>();

	public Student(String emailStudent) {
		super();
		this.emailStudent = emailStudent;
	}

	public Student(String emailStudent, String voornaam, String naam) {
		this(emailStudent);
		this.naamStudent = naam;
		this.voornaamStudent = voornaam;
	}

	public String getNaamStudent() {
		return naamStudent;
	}

	public void setNaamStudent(String naamStudent) {
		this.naamStudent = naamStudent;
	}

	public String getVoornaamStudent() {
		return voornaamStudent;
	}

	public void setVoornaamStudent(String voornaamStudent) {
		this.voornaamStudent = voornaamStudent;
	}

	public String getEmailStudent() {
		return emailStudent;
	}

	public void setEmailStudent(String emailStudent) {
		this.emailStudent = emailStudent;
	}

	public Map<String, ModulePunt> getScoresStudent() {
		return scoresStudent;
	}

	public void voegScoreToe(String sModuleNaam, double score) {
		ModulePunt mp = new ModulePunt(sModuleNaam, score);
		getScoresStudent().put(sModuleNaam, mp);
	}

	public void voegScoreToe(String sModuleNaam, int aantalStudiePunten,
			double score) {
		ModulePunt mp = new ModulePunt(sModuleNaam, aantalStudiePunten, score);
		getScoresStudent().put(sModuleNaam, mp);
	}

	public double getTotaleScoreStudent() {
		// alle modules overlopen. Ifv gewicht wordt de totale scorepercentage
		// berekenen.

		// stap 1: bepaal som van studiepunten
		int iTotaalStptn = 0;
		for (ModulePunt mp : this.getScoresStudent().values()) {
			iTotaalStptn += mp.getAantalStudiePunten();
		}

		// stap 2: scores overlopen. Elke score vermenigvuldigen met gewicht van
		// de module.
		// gewicht van module = aantal stptn delen door totale aantal
		// studiepunten
		double dTotaleScore = 0;

		for (ModulePunt mp : this.getScoresStudent().values()) {
			double dGewicht = (double) mp.getAantalStudiePunten()
					/ iTotaalStptn;
			dTotaleScore += (mp.getScore() * dGewicht);
		}

		return dTotaleScore;
	}

	@Override
	public String toString() {
		return "Student [emailStudent=" + emailStudent + ", scoresStudent="
				+ scoresStudent + "]";
	}

	@Override
	public int compareTo(Student other) {
		// TODO Auto-generated method stub

		// deze methode geeft een getal terug
		// ofwel 0: dan zijn beide objecten even groot voor hem
		// ofwel getal groter dan 0: je bent groter dan het object waarmee je
		// vergelijkt
		// ofwel getal kleind dan 0: je bent kleiner dan het object waarmee je
		// vergelijkt

		// eerst mijn totale score ophalen
		double mijnTotaleScore = this.getTotaleScoreStudent();

		// de andere zijn totale score
		double andereTotaleScore = other.getTotaleScoreStudent();

		// en die twee trekken we van elkaar af
		double dVerschil = mijnTotaleScore - andereTotaleScore;

		if (dVerschil > 0)
			return +1;
		if (dVerschil < 0)
			return -1;
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((emailStudent == null) ? 0 : emailStudent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (emailStudent == null) {
			if (other.emailStudent != null)
				return false;
		} else if (!emailStudent.equals(other.emailStudent))
			return false;
		return true;
	}

	// static methodes
	// 1 geef een lijst met alle scores van een module terug
	public static List<Double> getScoresModule(List<Student> studenten,
			String moduleNaam) {
		List<Double> scores = new ArrayList<Double>();
		for (Student student : studenten) {
			if (student.getScoresStudent().containsKey(moduleNaam)) {
				scores.add(student.getScoresStudent().get(moduleNaam).getScore());
			}
		}
		return scores;
	}

	// 2 bepaal gemiddelde van een module over verschillende studenten heen
	public static double getGemiddeldeScoreModule(List<Student> studenten,
			String moduleNaam) {
		double totaal = 0;
		List<Double> scores = getScoresModule(studenten, moduleNaam);
		for (Double score : scores) {
			totaal += score.doubleValue();
		}
		return totaal / scores.size();
	}

	// 3 sorteer studenten volgens hun totaal percentage
	public static void sorteerStudenten(List<Student> studenten) {
		Collections.sort(studenten);
	}

	//4 bepaal alle unieke modulenamen
	public static List<String> getModuleNamen(List<Student> studenten) {
		List<String> uniekeNamenModules = new ArrayList<String>();

		for (Student student : studenten) {
			for (String modulenaam : student.getScoresStudent().keySet()) {
				if (!uniekeNamenModules.contains(modulenaam)) {
					uniekeNamenModules.add(modulenaam);
				}
			}
		}
		return uniekeNamenModules;
	}

}
