package db;

import java.util.Scanner;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class CopyOfTagParser {
	private static final String INSERT_SCHEMA = "(`course`, `course_name`, `section`, `class_nbr`, `units`, `type`, `days`, `times`, `location`, `instructor`, `department`)";
	private static final String CREATE_TABLE_SCHEMA = "(course varchar(255),course_name varchar(255),section int,class_nbr int,units varchar(255),type varchar(255),days varchar(255),times varchar(255),location varchar(255),instructor varchar(255), department varchar(255))";

	public static void main(String[] args) {
		toSQL("class.tag");

	}

	/**
	 * Converts the tag file into SQL statements. Things with ' , & , a comma, or ':' have been stripped. & was replaced with AND
	 * 
	 * @param filename
	 *            file to be read from
	 */
	public static void toSQL(String filename) {
		Scanner in = null;
		BufferedWriter output = null;
		String statement = "";
		String currentTable = "courses";

		try {
			in = new Scanner(new File(filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			File file = new File("courses2.sql");
			output = new BufferedWriter(new FileWriter(file));
		} catch (Exception e) {
			e.printStackTrace();

		}

		
		try { 
			output.write("CREATE TABLE "+currentTable+CREATE_TABLE_SCHEMA+";\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String department = "";
		while (in.hasNextLine()) {
			String[] current = in.nextLine().split("\t");
			for(int i = 0; i < current.length; i++){
				current[i] = current[i].replace("\'","").replace("**", "0");
			}
			

			if (current[0].contains("<ParaStyle:Division>")) { // reads the
																// Division tag,
																// which
																// indicates a
																// new table
																// must be
																// created
				department = current[0].substring(20, current[0].length())
						.replace(" ", "_").replace("&", "AND").replace(":","").replace(",","").replace("-","");
				
			}

			else if (current[0].contains("<ParaStyle:Course>")
					&& !current[1].equals("")) {
				// this will need to be changed if the schema changes
				statement = "insert into `" + currentTable + "`"
						+ INSERT_SCHEMA + " values(" + "\'"
						+ current[0].substring(18, current[0].length()) + "\',"
						+ // course
						"\'" + current[1] + "\'," + // course_name
						current[2] + "," + // section
						current[3] + "," + // class_nbr
						current[4] + "," + // units
						"\'" + current[7] + "\'," + // type
						"\'" + current[11] + "\'," + // days
						"\'" + current[12]+ "\'" ; // times

				if(current.length == 14){
					statement += ","+"\'" + "TBA" + "\'," + // location
					"\'" + "TBA" + "\'";// instructor
				}
				
				if (current.length == 15) {
					if (current[14].equals(""))
						current[14] = "TBA";
					statement += ","+"\'" + current[14] + "\'," + // location
							"\'" + "TBA" + "\'";// instructor
				
				} else if (current.length == 16) {
					if (current[14].equals(""))
						current[14] = "TBA";
					statement += ","+"\'" + current[14] + "\'," + // location
							"\'" + current[15] + "\'"; // instructor
							
				}
				statement+=",\'"+department+"\');";
				try {
					output.write(statement + "\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			else if (current[0].contains("<ParaStyle:CourseNight>")
					&& !current[1].equals("")) {
				// this will need to be changed if the schema changes
				statement = "insert into `" + currentTable + "`"
						+ INSERT_SCHEMA + " values(" + "\'"
						+ current[0].substring(23, current[0].length()) + "\',"
						+ // course
						"\'" + current[1] + "\'," + // course_name
						current[2] + "," + // section
						current[3] + "," + // class_nbr
						current[4] + "," + // units
						"\'" + current[7] + "\'," + // type
						"\'" + current[11] + "\'," + // days
						"\'" + current[12]+ "\'"; // times

				if(current.length == 14){
					statement += ","+"\'" + "TBA" + "\'," + // location
					"\'" + "TBA" + "\'";// instructor
				}
				
				if (current.length == 15) {
					if (current[14].equals(""))
						current[14] = "TBA";
					statement += ","+"\'" + current[14] + "\'," + // location
							"\'" + "TBA" + "\'"; // instructor

				} else if (current.length == 16) {
					if (current[14].equals(""))
						current[14] = "TBA";
					statement += ","+"\'" + current[14] + "\'," + // location
							"\'" + current[15] + "\'"; // instructor

				}
				if (current[0].contains("<ParaStyle:CourseNight>CMPE 295")) {
					in.nextLine();
					in.nextLine();
				}
				
				statement+=",\'"+department+"\');";
				try {
					output.write(statement + "\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			else if (current[0].contains("<ParaStyle:CourseMUSE>")
					&& !current[1].equals("")) {
				// this will need to be changed if the schema changes
				statement = "insert into `" + currentTable + "`"
						+ INSERT_SCHEMA + " values(" + "\'"
						+ current[0].substring(22, current[0].length()) + "\',"
						+ // course
						"\'" + current[1] + "\'," + // course_name
						current[2] + "," + // section
						current[3] + "," + // class_nbr
						current[4] + "," + // units
						"\'" + current[7] + "\'," + // type
						"\'" + current[11] + "\'," + // days
						"\'" + current[12]+ "\'"; // times

				if(current.length == 14){
					statement += ","+"\'" + "TBA" + "\'," + // location
					"\'" + "TBA" + "\'";// instructor
				}
				
				if (current.length == 15) {
					if (current[14].equals(""))
						current[14] = "TBA";
					statement += ","+ "\'" + current[14] + "\'," + // location
							"\'" + "TBA" + "\'";  // instructor
						
				} else if (current.length == 16) {
					if (current[14].equals(""))
						current[14] = "TBA";
					statement +=","+ "\'" + current[14] + "\'," + // location
							"\'" + current[15] + "\'"; // instructor
						
				}
				statement+=",\'"+department+"\');";
				try {
					output.write(statement + "\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
