package com.example.stundenplan;

import java.util.LinkedList;

import android.content.Context;
import android.util.Log;

public class Timetable {

	Context context;
	// Konstruktor 
	public Timetable(Context c) {
		context = c;
	}// Konstruktor>
	
	//ließt Daten aus Datenbank und füllt Tabelle
	public void readTable(){
		DataHandler.FillFromDatabase(context);
	}//readTable

	// Gibt die ersten drei Buchstaben des Faches wieder; diese können dann z.B.
	// in die Buttons der MainActivity eingetragen werden
	/*
	 * public static String substrSubject(int DayNumberInWeek, int ) { return
	 * field[x][y].getName().substring(0, 3); }
	 */

	//
	// public String getTeacherBySubject(String searchsubject){
	// String searchteacher = null;
	//
	// for (int i = 0; i < daynr; i++){
	// for (int j = 0; j < hournr; j++){
	// String subject = field[i][j].getSubject();
	// if (subject.compareToIgnoreCase(searchsubject) == 0){
	// searchteacher = field[i][j].getTeacher();
	// break;
	// }
	// }
	// }
	//
	// return searchteacher;
	// }

	// **** SETTERS

	/*
	 * SetSubjectAt
	 * 
	 * Setzt Neues Fach an Bestimmten Steckplatz der Liste SubjectID: ID des
	 * Subjects DayNumber: Tag in der Woche beginnend bei 1 lessonNumber:
	 * Stunden beginnend mit 1
	 */
	public static void setSubjectAt(int SubjectID, int DayNumber,
			int lessonNumber) {
		// Zahlen anpassen
		lessonNumber--;
		DayNumber--;
		Subject insertSubject = null;
		// Vorhandene Stunden durchsuchen und neues Subject daraus Erzeugen
			insertSubject = DataHandler.getSubjects().get(SubjectID);
			
			Log.d("Debug: ",""+ SubjectID);
		// positionen in Listen Ersetzen
		if(insertSubject != null){
			DataHandler.getTimetable()[DayNumber][lessonNumber] = insertSubject;
		} else {
			insertSubject = new Subject(-1, "-1", "-1", -1);
			DataHandler.getTimetable()[DayNumber][lessonNumber] = insertSubject;
		}
	}// setSubjectAt
	
	

	// **** GETTERS
	
	//Gibt Bestimmten eintrag im stundenplan wieder anhand von Tag und Stunde
	public static Subject getSubjectAt(int DayNumber,int lessonNumber) {
		// Zahlen anpassen
		lessonNumber--;
		DayNumber--;
	
		return DataHandler.getTimetable()[DayNumber][lessonNumber];
	}// setSubjectAt
	
	//Gibt Bestimmten eintrag anhand der ID in der Subject Hashtable wieder
	public static Subject getSubjectIDAt(int SubjectID) {

		return DataHandler.getSubjects().get(SubjectID);
	}// getSubjectIDAt

	// SHOW gibt liste als text aus in log View unter dem Tag "Show"
	public static void showtable() {
		DataHandler.showTimetable();
		DataHandler.showdefinedSubjects();
	}

}// class Timetable>
