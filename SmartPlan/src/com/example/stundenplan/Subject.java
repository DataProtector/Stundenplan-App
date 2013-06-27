package com.example.stundenplan;

import android.util.Log;

public class Subject {

	private String name, teacher; //name & lehrer
	private int Name_ID,Color_ID;//ID in vordefinierten Fächern & Farb_ID

	// Konstruktor
	public Subject(int Name_ID,String name, String teacher,int Color_ID) {
		this.name = name;
		this.teacher = teacher;
		this.Name_ID = Name_ID;
		this.Color_ID = Color_ID;
	}// Konstruktor>

	/* **** SETTERS 
	 * Re-Initialisierung der einzelnen Variablen (z.B bei
	 * Lehreränderung zum Halbjahr)
	 */
	
	public void setID(int ID) {
		this.Name_ID = ID;
	}
	public void setSubject(String name) {
		this.name = name;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	public void setColor_ID(int Color_ID) {
		this.Color_ID = Color_ID;
	}

	// **** GETTERS
	public int getID(){
		return Name_ID;
	}

	public String getName() {
		return name;
	}//

	public String getTeacher() {
		return teacher;
	}
	
	public int getColor_ID(){
		return Color_ID;
	}

	public void Show(int Day_id,int Lesson_id) {
		String log = "ID: " + getID() + " ,Name: "
				+ getName() + " ,Teacher: "
				+ getTeacher() + " ColorID: "
				+getColor_ID();
		if(getID() == -1){
			log = "0";
		}
		Log.d("TimeTable: ", "Subject["+Day_id+"]["+Lesson_id+"] " + log);
	}
}//class Subject
