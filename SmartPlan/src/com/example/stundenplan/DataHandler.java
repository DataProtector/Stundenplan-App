package com.example.stundenplan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.util.Log;

/*
 * Data_management 
 * 
 * Laufzeit:
 * Speichert Stundenplan in Liste aus Listen 
 * Speichert Vorhandene Stunden in Liste aus LL_TimeTable
 * 
 * Datenbank:
 * Speichert Stundenplan in Datenbank
 * Läd Stundenplan bei Start der App in Liste aus Listen
 *
 */
public class DataHandler {
	static int Days = 5;
	static int Lessons = 12;
	static Subject[][] Timetable = new Subject[Days][Lessons];
	// Mehrdim. Array für Stundentabelle

	private static Hashtable<Integer, Subject> HT_definedSubjects = new Hashtable<Integer, Subject>();

	// Linkedlist aus Vordefinierten Fächern (Edit_Activity)

	// Füllt Timetable mit Werten und Fügt vorhandene vordefinierte Subjects in
	// LL_definedSubjects ein , sodass sie verwendet werden kann
	// Doppelte inhalte werden nicht in das Array aufgenommen und sofort von der
	// Datenbank gelöscht
	public static void FillFromDatabase(Context context) {

		// Alle Subjects von Datenbank lesen
		LinkedList<Subject> LL_Subjects = readAllSubjectsFromDatabase(context);

		// Alle Tage und Stunden von Datenbank lesen --> Reihenfolge ist wie bei
		// LL_Database
		LinkedList<int[]> LL_DaysAndLessons = readAllDaysAndLessonsFromDatabase(context);

		
		// Liste aus Doppelten Einträgen
		LinkedList<Integer> LL_toDelete = checkDoubleTimetable(
				LL_DaysAndLessons, context);

		// Doppelte Einträge löschen (Erstes wird gelöscht)
		for (int i = 0; i < LL_toDelete.size(); i++) {
			LL_Subjects.remove(LL_toDelete.get(i)-1);
			LL_DaysAndLessons.remove(LL_toDelete.get(i)-1);  
		}

		// Array mit Puffer füllen
		for (int i = 0; i < Days; i++) {
			for (int j = 0; j < Lessons; j++) {
				Timetable[i][j] = new Subject(-1, "-1", "-1", -1);
			}
		}

		// Array Iterieren und werte in Timetable einfügen
		for (int i = 0; i < LL_DaysAndLessons.size(); i++) {
			Timetable[LL_DaysAndLessons.get(i)[0]][LL_DaysAndLessons.get(i)[1]] = LL_Subjects
					.get(i);
		}

		// Hashtable füllen
		for (int i = 0; i < LL_Subjects.size(); i++) {
			if (!HT_definedSubjects.containsKey(LL_Subjects.get(i).getID())) {
				HT_definedSubjects.put(LL_Subjects.get(i).getID(),
						LL_Subjects.get(i));
			}
		}

	}// FillTimetableFromDatabase>

	static public void showTimetable() {
		for (int i = 0; i < Days; i++) {
			for (int j = 0; j < Lessons; j++) {
				Timetable[i][j].Show(i, j);
			}
		}
	}

	static public void showdefinedSubjects() {
		for (int i = 0; i < HT_definedSubjects.size(); i++) {
			String log = "SubjectID: " + HT_definedSubjects.get(i).getID()
					+ " ,Name: " + HT_definedSubjects.get(i).getName()
					+ " ,Teacher: " + HT_definedSubjects.get(i).getTeacher()
					+ " ColorID: " + HT_definedSubjects.get(i).getColor_ID();
			Log.d("SubjectList: ", log);
		}
	}

	// Prüft in parameterliste ob doppelte einträge vorhanden sind und gibt
	// index als ID zurück
	private static LinkedList<Integer> checkDoubleTimetable(
			LinkedList<int[]> LL_DaysAndLessons, Context context) {
		LinkedList<Integer> LL_toDelete = new LinkedList<Integer>();

		for (int i = 0; i < LL_DaysAndLessons.size(); i++) {
			for (int j = 0; j < LL_DaysAndLessons.size(); j++) {
				// selbst überprüfung und ob kleiner
				if (i == j || j < i) {
					continue;
				}
				// Tag und Stunde sind gleich --> in LL_todelete
				if (LL_DaysAndLessons.get(i)[0] == LL_DaysAndLessons.get(j)[0]
						&& LL_DaysAndLessons.get(i)[1] == LL_DaysAndLessons
								.get(j)[1]) {
					LL_toDelete.add(LL_DaysAndLessons.get(i)[2]);
				}
			}
		}

		// Zu löschende Einträge löschen
		for (int i = 0; i < LL_toDelete.size(); i++) {
			DeleteRow(LL_toDelete.get(i), context);
		}
		return LL_toDelete;
	}// checkDouble

	// **** GETTERS
	static Subject[][] getTimetable() {
		return Timetable;
	}

	static Hashtable<Integer, Subject> getSubjects() {
		return HT_definedSubjects;
	}

	/* ****************************************************
	 * DATABASE **************************************************
	 */

	// Einzelnes Object zur Datenbank hinzufügen
	static void addToDatabase(Subject sub, int Day_ID, int Lesson_ID,
			Context context) {
		// DATENBANK
		DatabaseHandler db = new DatabaseHandler(context);
		Log.d("Database: ", "Inserting ..");
		db.addSubject(sub, Day_ID, Lesson_ID);
	}// addToDatabase>

	// Einzelnes Subject von Datenbank lesen
	static Subject readFromDatabase(int Day_id, int Lesson_ID, Context context) {

		Log.d("Database: ", "Reading.. Tag_ID: " + Day_id + " Stunde(+1): "
				+ Lesson_ID);
		DatabaseHandler db = new DatabaseHandler(context);
		Subject result = db.readSubject(Day_id, Lesson_ID);

		String log = "Subject ID: " + result.getID() + " ,Name: "
				+ result.getName() + " ,Teacher: " + result.getTeacher()
				+ " Color_ID: " + result.getColor_ID();
		Log.d("Database: ", "Read: " + log);

		return result;
	}// readFromDatabase>

	// alle Subjects von Datenbank lesen
	private static LinkedList<Subject> readAllSubjectsFromDatabase(
			Context context) {

		Log.d("Database: ", "Reading.. All Subjects");
		DatabaseHandler db = new DatabaseHandler(context);
		LinkedList<Subject> LL_result = db.readAllSubjects();

		for (int i = 0; i < LL_result.size(); i++) {
			String log = "Subject ID: " + LL_result.get(i).getID() + " ,Name: "
					+ LL_result.get(i).getName() + " ,Teacher: "
					+ LL_result.get(i).getTeacher() + " Color_ID: "
					+ LL_result.get(i).getColor_ID();
			Log.d("Database: ", "Read: " + log);
		}
		return LL_result;
	}// readFromDatabase>

	// Alle Tage und dazugehörige Stunden lesen und in Array abspeichern
	// (Reihenfolge ist die gleiche wie readAllFromDatabase)
	private static LinkedList<int[]> readAllDaysAndLessonsFromDatabase(
			Context context) {

		Log.d("Database: ", "Reading.. All Days and Lessons");
		DatabaseHandler db = new DatabaseHandler(context);
		LinkedList<int[]> LL_result = db.readAllDaysAndLessons();
		return LL_result;
	}// readFromDatabase>

	// Anhand der EintragsID eine Spalte der Datenbank löschen
	private static void DeleteRow(int EntryID, Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		db.deleteRow(EntryID);
		Log.d("Database: ", "Delete Row: " + EntryID);
	}

	// Datenbank löschen
	public static void DeleteDatabase(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		db.deleteDatabase(context);
		Log.d("Database: ", "Delete Database..");
	}

}// class Data_management>
