package Education_System.pojo;

public class timetable {
    protected Integer index;
    protected String course_id;
    protected String clazz_id;
    protected String classroom;
    protected Integer start_week;
    protected Integer end_week;
    protected String week_day;
    protected String section;

    public timetable() {
    }

    public timetable(Integer index, String course_id, String clazz_id, String classroom, Integer start_week, Integer end_week, String week_day, String section) {
        this.index = index;
        this.course_id = course_id;
        this.clazz_id = clazz_id;
        this.classroom = classroom;
        this.start_week = start_week;
        this.end_week = end_week;
        this.week_day = week_day;
        this.section = section;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getClazz_id() {
        return clazz_id;
    }

    public void setClazz_id(String clazz_id) {
        this.clazz_id = clazz_id;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public Integer getStart_week() {
        return start_week;
    }

    public void setStart_week(Integer start_week) {
        this.start_week = start_week;
    }

    public Integer getEnd_week() {
        return end_week;
    }

    public void setEnd_week(Integer end_week) {
        this.end_week = end_week;
    }

    public String getWeek_day() {
        return week_day;
    }

    public void setWeek_day(String week_day) {
        this.week_day = week_day;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return "timetable{" +
                "index=" + index +
                ", course_id='" + course_id + '\'' +
                ", clazz_id='" + clazz_id + '\'' +
                ", classroom='" + classroom + '\'' +
                ", start_week=" + start_week +
                ", end_week=" + end_week +
                ", week_day='" + week_day + '\'' +
                ", section='" + section + '\'' +
                '}';
    }
}
