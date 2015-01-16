package db;

import java.util.Scanner;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class TagParser {
	private static final String SCHEMA = "(`course`, `course_name`, `section`, `class_nbr`, `units`, `type`, `days`, `times`, `location`, `instructor`)";
	
	public static void main(String[] args){
		toSQL("testTagFile.txt");
				
	}

	public static void toSQL(String filename) {
		Scanner in = null;
		BufferedWriter output = null;
		String statement = "";
		String currentTable = "";

		try {
			in = new Scanner(new File(filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			File file = new File("courses.sql");
			output = new BufferedWriter(new FileWriter(file));
		} catch (Exception e) {
			e.printStackTrace();

		}


		while (in.hasNextLine()) {
			String[] current = in.nextLine().split("\t");

			
			if(current[0].contains("<ParaStyle:Division>")){
				currentTable = current[0].substring(20,current[0].length());
			}
			else if(current[0].contains("<ParaStyle:Course>")){
				statement = "insert into `"+currentTable+ "`"+SCHEMA+" values("+
				"\'"+current[0].substring(18,current[0].length())+"\',"+ //course
				"\'"+current[1]+"\',"+ //course_name
				current[2]+","+ //section
				current[3]+","+ //class_nbr
				current[4]+","+ //units
				"\'"+current[7]+"\',"+ //type
				"\'"+current[11]+"\',"+ //days
				"\'"+current[12]+"\',"+ //times
				"\'"+current[14]+"\',"+ //location
				"\'"+current[15]+"\'"+ //instructor
				")";
				
			}
			try{
			output.write(statement);
			}
			catch(Exception e){
				e.printStackTrace();
			}

		}
		
		
		
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
