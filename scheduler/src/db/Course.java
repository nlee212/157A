package db;

/**
 * Represents a single course with a specific time and section
 * 
 * @author Nicholas
 * 
 */
public class Course {
	private static final int[] CHAR_LOOKUP = { 1, 0, 0, 0, 0, 0, 0, 16, 0, 0,
			0, 0, 2, 0, 8, 0, 0, 4 }; // used to convert MTWRF into the
	// corresponding bit
	private static final int LOOKUP_OFFSET = 70; // ascii value of F, which
													// is the lowest value out
	// of MTWRF
	private String course;
	private String id;
	private String section;
	private String dayStr;
	private byte days; // bitstrng representing days of the week
	private int startTime;
	private int endTime;
	private String location;
	private String instructor;

	/**
	 * Creates a new Course based off of given parameters. If days is TBA, value
	 * is set to 0.
	 * 
	 * @param Course
	 *            Course name (CS 151)
	 * @param id
	 *            Unique Section ID
	 * @param section
	 *            Section number
	 * @param days
	 *            Days the course occurs on in MTWRF format
	 * @param startTime
	 *            Start time of the course using Military Time
	 * @param endTime
	 *            End time of the course using Military Time
	 */
	public Course(String course, String id, String section, String days,
			int startTime, int endTime, String location, String instructor) {
		this.course = course;
		this.id = id;
		this.section = section;
		this.dayStr = days;
		if (!days.equals("TBA"))
			this.days = convertDayString(days);
		else
			this.days = 0;
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location;
		this.instructor = instructor;

	}

	/**
	 * Converts a string representation of the days a course occurs on into a
	 * bitstring held in a byte. "MWF" results in 00010101 where each bit
	 * represents a day, with the least significant bit representing Friday and
	 * each preceding bit representing a different day.
	 * 
	 * @param days
	 *            the string representing the days in MTWRF format
	 * @return the byte containing the converted day string
	 */
	private byte convertDayString(String days) {
		byte result = 0;
		char[] day = days.toCharArray();
		for (char c : day) {
			int val = c - LOOKUP_OFFSET;
			result += CHAR_LOOKUP[val];
		}
		return result;

	}

	/**
	 * Checks whether this Course overlaps with a given Course still needs to
	 * check for day overlap
	 * 
	 * @param c
	 *            the given course to check for overlaps
	 * @return true if this classes overlaps with the given course, false
	 *         otherwise
	 */
	public boolean overlapsWith(Course c) {
		
		if ((this.days & c.days) != 0) { // if any of the days overlap, you
											// will get a nonzero answer for the
											// and
			if (this.startTime <= c.startTime && this.endTime >= c.startTime
					|| c.startTime <= this.startTime
					&& c.endTime >= this.startTime)
				return true;
		}
		return false;

	}

	public String toString() {
		return course +" "+id + " " + section + " " + dayStr + " " + startTime + "-"
				+ endTime +" "+location+" "+instructor;
	}

}
