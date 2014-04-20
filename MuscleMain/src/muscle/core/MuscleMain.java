package muscle.core;

import java.text.DateFormat;


public class MuscleMain {
	  
	public static final DateFormat SHORT_DATE = DateFormat.getDateInstance(DateFormat.SHORT);
	//sounds WIERD but can't think of a better name 
	public static final int SAFE_DAY = 5;
	public static final String LINE_BREAK = "\n";
	
	public static void main(String[] args) {
		//Wrapper 
		//GUI contains I/O which contains IM and PM
		//iomanager parses profile and (muscle, activity) pairs
		IOManager io = new IOManager("save.txt", "items.txt");

		//this gets sent to gui, which displays all the info
		new GUIManager(io);
	}
}