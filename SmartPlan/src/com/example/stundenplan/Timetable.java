package com.example.stundenplan;

import java.util.Enumeration;
import java.util.LinkedList;

import android.content.Context;
import android.util.Log;
// 	Timetable
//
// 	Adapterklasse zur Verwaltung der Datenbank und Verwaltungsstrukturen des Stundenplans
//
//	F.S 25.6.13
public abstract class Timetable {

	//nicht verwendet da statische Methoden
	public Timetable() {
		
	}// Konstruktor>
	
	//ließt Daten aus Datenbank und füllt statische Tabelle
	//Bei start der App
	public static void readTable(Context c){
		DataHandler.FillFromDatabase(c);
	}//readTable
	
	// **** SETTERS
	
	/*
	 *  addSubject(Subject)
	* 	
	* Fügt in Hashtable und Datenbank neues Subject Hinzu 
	* --> ID des Subjects wird Automatisch bestimmt
	* 
	* Subj: Subject, welches neu definiert 
	* 		wurde und in Hashtable und Datenbank 
	* 		aufgenommen werden soll
	*/
	public static void addSubject(Subject Subj,Context c){
		//vorhandene Keys auslesen
		Enumeration<Integer> Keys = DataHandler.getSubjects().keys();
		int newID = 0;
		//vorhandene Keys iterieren und freienPlatz suchen
		for(int i =0;i<DataHandler.getSubjects().size()+1;i++){
			boolean contains = false;
			while(Keys.hasMoreElements()){
				if(Keys.nextElement() == i){
					contains = true;
					break;
				}
			}
			if(!contains){
				newID = i;
				break;
			}
		}
		Subj.setID(newID);
		//neues Subject in Hashtable einfügen
		DataHandler.addSubject(Subj, c);
	}//addSubject>
	
	
	/*
	 *  changeSubject(int,Subject)
	* 	
	* Ersetzt Eintrag in Hashtable durch Subj
	* --> ID des Subjects wird Automatisch bestimmt
	* 
	* ID: ID des Subjects
	* Subj: Subject, welches neu definiert 
	* 		wurde und in Hashtable und Datenbank 
	* 		aufgenommen werden soll
	*/
	public static void changeSubject(int ID,Subject Subj,Context c){
			Subj.setID(ID);
			DataHandler.addSubject(Subj, c);
	}
	
	/*
	 * setTimeTableAt(ID)
	 * 
	 * Setzt Fach an bestimmten Steckplatz der Liste 
	 * Speichert änderung in Datenbank
	 * 
	 * SubjectID: ID des Subjects 
	 * DayNumber: Tag in der Woche beginnend bei 1 
	 * lessonNumber: Stunden beginnend mit 1
	 */
	public static void setTimeTableAt(int SubjectID, int DayNumber,int lessonNumber,Context c) {
		// Zahlen anpassen
		lessonNumber--;
		DayNumber--;
		// Vorhandene Stunden durchsuchen und neues Subject daraus Erzeugen
		Subject insertSubject = DataHandler.getSubjects().get(SubjectID);

		// positionen in Listen Ersetzen
		if(insertSubject == null){
			insertSubject = new Subject(-1, "-1", "-1", -1);
			Log.d("TimeTable: ","SubjectID: "+ SubjectID +" nicht vorhanden");
		} else {
			
			DataHandler.getTimetable()[DayNumber][lessonNumber] = insertSubject;
		}
		
		DataHandler.getTimetable()[DayNumber][lessonNumber] = insertSubject;
		DataHandler.setSubjectAt(insertSubject, DayNumber, lessonNumber, c);
	}// setTimeTableAt>
	
	/*
	 * setTimeTableAt(String) --> Nur verwenden wenn SetSubjectAt(ID) nicht sinnvoll
	 * 
	 * Setzt Fach an bestimmten Steckplatz der Liste 
	 * Speichert änderung in Datenbank
	 * 
	 * SubjectName: Name des Subjects 
	 * DayNumber: Tag in der Woche beginnend bei 1 
	 * lessonNumber: Stunden beginnend mit 1
	 * 
	 */
	public static void setTimeTableAt(String SubjectName, int DayNumber,int lessonNumber,Context c) {
		// Zahlen anpassen
		lessonNumber--;
		DayNumber--;
		// Vorhandene Stunden durchsuchen und neues Subject daraus Erzeugen
		Subject insertSubject = DataHandler.getSubjectByName(SubjectName);

		// positionen in Listen Ersetzen
		if(insertSubject == null){
			insertSubject = new Subject(-1, "-1", "-1", -1);
			Log.d("TimeTable: ","SubjectID: "+ SubjectName +" nicht vorhanden");
		} else {
			
			DataHandler.getTimetable()[DayNumber][lessonNumber] = insertSubject;
		}
		
		DataHandler.getTimetable()[DayNumber][lessonNumber] = insertSubject;
		DataHandler.setSubjectAt(insertSubject, DayNumber, lessonNumber, c);
	}// setTimeTableAt>

	// **** GETTERS
	
	//Gibt Bestimmten Eintrag im stundenplan wieder anhand von Tag und Stunde
	public static Subject getTimeTableAt(int DayNumber,int lessonNumber) {
		// Zahlen anpassen
		lessonNumber--;
		DayNumber--;
	
		return DataHandler.getTimetable()[DayNumber][lessonNumber];
	}// setSubjectAt>
	
	//Gibt Bestimmten eintrag anhand der ID in der Subject Hashtable wieder
	public static Subject getTimeTableByID(int SubjectID) {
		return DataHandler.getSubjects().get(SubjectID);
	}// getTimeTableByID>

	// SHOW gibt liste als text aus in log View unter dem Tag "TimeTable" und "SubjectList"
	public static void showtable() {
		DataHandler.showTimetable();
		DataHandler.showdefinedSubjects();
	}
	
	/*Erforderliche Filter in LogCat zur Darstellung
	*
	*	" Database: " --> zeigt Veränderungen in der Datenbank
	*	" TimeTable: " --> zeigt Veränderungen in Timetable
	*	" SubjectList: " --> zeigt Veränderungen in der Hashtable
	*/
	public static void DebugTest(Context c){
		DataHandler.DeleteDatabase(c);
		Timetable.readTable(c);
		Subject sub = new Subject(0,"Informatik","KOL",0);
		Subject sub2 = new Subject(0,"Deutsch","Hr. Mayer",1);
		Subject sub3 = new Subject(0,"Mathe","Keil",2);
		
		readTable(c);
		addSubject(sub, c);
		addSubject(sub3, c);
		addSubject(sub2, c);
		changeSubject(0,sub,c);
		changeSubject(2,new Subject(0,"Deutsch","Fr. Mühlbauer",1),c);
		DataHandler.showdefinedSubjects();
		
		setTimeTableAt(0, 1, 1, c);
		setTimeTableAt(1, 2, 1, c);
		setTimeTableAt(1, 2, 2, c);
		setTimeTableAt(2, 3, 1, c);
		DataHandler.showTimetable();
	}

}// class Timetable>
