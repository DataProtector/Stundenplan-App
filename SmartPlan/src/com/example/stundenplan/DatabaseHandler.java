package com.example.stundenplan;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//DATABASEHANDLER
//
//Verwaltet Datenbank mit 2 tables,
//1. table: Speichert definierte SUBJECTS ab
//2. table: Speichert positionen in Timetable
//
//F.S 13.6.13
public class DatabaseHandler extends SQLiteOpenHelper {
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "smartplan.dp";
	

	// Tabellen name
	private static final String TABLE_TIMETABLE = "timetable";
	private static final String TABLE_SUBJECTS = "subjects";

	// Columns names
	
	private static final String KEY_ID = "id";
	
	private static final String KEY_DAY_ID = "day_id";
	private static final String KEY_LESSON_ID = "lesson_id";
	private static final String KEY_NAME_ID = "subject_id";
	private static final String KEY_NAME = "subjectname";
	// private static final String KEY_TEACHER_ID= "teacher_name"; //TODO FÜR
	// VERTRETUNGSPLAN
	private static final String KEY_TEACHER_NAME = "teacher_id";
	
	private static final String KEY_COLOR_ID = "color_id";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Tabelle erstellen
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TIMETABLE  = "CREATE TABLE IF NOT EXISTS "
				+  TABLE_TIMETABLE + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ KEY_NAME_ID + " INTEGER,"
				+ KEY_DAY_ID + " INTEGER,"
				+ KEY_LESSON_ID + " INTEGER" + ")";
		db.execSQL(CREATE_TIMETABLE);
		
		String CREATE_SUBJECTS = "CREATE TABLE IF NOT EXISTS "
				+ TABLE_SUBJECTS  + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
				+ KEY_NAME_ID + " INTEGER,"
				// + KEY_TEACHER_ID + " INTEGER," //TODO FÜR VERTRETUNGSPLAN
				+ KEY_TEACHER_NAME + " TEXT,"
				+ KEY_COLOR_ID + " INTEGER" + ")";
		
		
		db.execSQL(CREATE_SUBJECTS);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE " + TABLE_TIMETABLE);
		db.execSQL("DROP TABLE " + TABLE_SUBJECTS);
		Log.d("Database: ", "Upgrade Database.. from " + oldVersion + " to " + newVersion + " --> Deleted and new Created");
		// Create tables again
		onCreate(db);
	}

	// HINZUFÜGEN

	void addSubject(Subject sub) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME_ID, sub.getID());
		values.put(KEY_NAME, sub.getName());
		// values.put(KEY_TEACHER_ID, sub.getTeacher()); //TODO FÜR
		// VERTRETUNGSPLAN
		values.put(KEY_TEACHER_NAME, sub.getTeacher());
		values.put(KEY_COLOR_ID, sub.getColor_ID());
		// Inserting Row
		db.insert( TABLE_SUBJECTS , null, values);
		db.close(); // Closing database connection
	}
	
	void addSubjectInTimeTable(Subject sub,int Day_ID, int Lesson_ID) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME_ID, sub.getID());
		values.put(KEY_DAY_ID, Day_ID );
		values.put(KEY_LESSON_ID, Lesson_ID);
		// Inserting Row
		db.insert(TABLE_TIMETABLE , null, values);
		db.close(); // Closing database connection
	}
	

	// LESEN
	// Alle Subjects auslesen und in Liste speichern
	public LinkedList<Subject> readAllSubjects() {
		LinkedList<Subject> SubjectList = new LinkedList<Subject>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				int NAME_ID = Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(KEY_NAME_ID)));
				String NAME = cursor.getString(cursor.getColumnIndex(KEY_NAME));
				String TEACHER = cursor.getString(cursor
						.getColumnIndex(KEY_TEACHER_NAME));
				int COLOR_ID = Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(KEY_COLOR_ID)));

				Subject result = new Subject(NAME_ID, NAME, TEACHER, COLOR_ID);
				// Adding Subject to list
				SubjectList.add(result);
			} while (cursor.moveToNext());
		}
		db.close();
		return SubjectList;
	}// readAllSubjects>
	
	public LinkedList<Integer> readAllSubjectEntryIDs() {
		LinkedList<Integer> EntryList = new LinkedList<Integer>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				int Key_ID = Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(KEY_ID)));
				// Adding
				EntryList.add(Key_ID);
			} while (cursor.moveToNext());
		}
		db.close();
		return EntryList;
	}//readAllSubjectEntryIDs>

	
	public LinkedList<int[]> readTimeTable() {
		LinkedList<int[]> SubjectList = new LinkedList<int[]>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_TIMETABLE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				int ID = Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(KEY_NAME_ID)));
				int Day_ID = Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(KEY_DAY_ID)));
				int Lesson_ID = Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(KEY_LESSON_ID)));
				int Entry_ID = Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(KEY_ID)));
				int[] i = new int[4];
				i[0] = Day_ID;
				i[1] = Lesson_ID;
				i[2] = ID;
				i[3] = Entry_ID;
				// Adding Subject to list
				SubjectList.add(i);
			} while (cursor.moveToNext());
		}
		db.close();
		return SubjectList;
	}


	// VERÄNDERN

	// Einzelnen Eintrag verändern
	public int updateSubject(Subject sub, int Day_ID, int Lesson_ID) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME_ID, sub.getID());
		values.put(KEY_DAY_ID, Day_ID );
		values.put(KEY_LESSON_ID, Lesson_ID);

		// updating row
		return db.update(TABLE_TIMETABLE, values, KEY_DAY_ID + " = " + Day_ID
				+ " AND " + KEY_LESSON_ID + " = " + Lesson_ID,
				null);
	}

	// LÖSCHEN
	public void deleteRow(int EntryID) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_SUBJECTS, KEY_ID +" = " + String.valueOf(EntryID), null);
	    db.close();
	}

	// Gesammte Datenbank löschen
	public void deleteDatabase(Context context) {
		context.deleteDatabase(DATABASE_NAME);
	}


	
}
