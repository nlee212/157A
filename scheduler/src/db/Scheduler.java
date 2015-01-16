package db;

import java.util.ArrayList;

public class Scheduler {

	private ArrayList<ArrayList<Course>> completeSchedules; // list of complete
															// schedules. each
															// inner AL should
															// be of length
															// courseTotal


	public Scheduler() {
		completeSchedules = new ArrayList<ArrayList<Course>>();
	}

	/**
	 * Generate a list of possible schedules given a list of courses. each index
	 * in the outer ArrayList should be a different course, with the indexes in
	 * the inner array being the various sections each course has.
	 * 
	 * @param courseList
	 */
	public void generateSchedule(ArrayList<ArrayList<Course>> courseList) {
		makeSch(courseList, new ArrayList<Course>(), 0);
	}

	/**
	 * Recursive function for brute forcing all of the possible schedules.
	 * 
	 * @param courseList
	 *            List of remaining courses to add
	 * @param currentSch
	 *            The current schedule being processed
	 */
	private void makeSch(ArrayList<ArrayList<Course>> courseList,
			ArrayList<Course> currentSch, int pos) {
		if (pos < courseList.size()) {

			for (Course c : courseList.get(pos)) {
				if(fitsWithSch(currentSch,c)){
					ArrayList<Course> temp = new ArrayList<Course>();
					temp.addAll(currentSch);
					temp.add(c);
					makeSch(courseList,temp,pos+1);
				}

			}
		}
		else if(currentSch.size() == courseList.size()){
			completeSchedules.add(currentSch);
		}

	}

	/**
	 * Determines if a given schedule can contain the given course
	 * 
	 * @param currentSch
	 *            The current schedule to check against
	 * @param c
	 *            The course to check
	 * @return True if the course fits into the schedule, false otherwise
	 */
	private boolean fitsWithSch(ArrayList<Course> currentSch, Course c) {

		for (Course current : currentSch) {
			if (current.overlapsWith(c))
				return false;
		}
		return true;
	}

	/**
	 * Clears the complete schedule listing
	 */
	public void clearCurrentSchedule() {
		completeSchedules.clear();
	}

	/**
	 * Prints all schedules
	 */
	public void printSchedules() {
		for (ArrayList<Course> sch : completeSchedules) {
			for (Course c : sch) {
				System.out.println(c.toString());
			}
			System.out.println("===");
		}
	}

}
