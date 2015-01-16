package db;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		if (args.length == 1) {
			Access acc = new Access();
			String[] courses = args[0].substring(0,args[0].length()-1).split(",");
			Scheduler sch = new Scheduler();
			acc.connect();
			ArrayList<ArrayList<Course>> list = acc.formatInput(courses);
			if(list!=null){
				
				sch.generateSchedule(list);
				sch.printSchedules();
			}
			acc.close();
		}

	}

}
