package Education_System.pojo;

import java.sql.Timestamp;


public class Exam {
    protected Integer index;
    protected String id;
    protected String course_id;
    protected String clazz_id;
    protected String classroom;
    protected Timestamp start_time;
    protected Timestamp end_time;

    public Exam() {
    }

    public Exam(Integer index, String id, String course_id, String clazz_id, String classroom, Timestamp start_time, Timestamp end_time) {
        this.index = index;
        this.id = id;
        this.course_id = course_id;
        this.clazz_id = clazz_id;
        this.classroom = classroom;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "index=" + index +
                ", id='" + id + '\'' +
                ", course_id='" + course_id + '\'' +
                ", clazz_id='" + clazz_id + '\'' +
                ", classroom='" + classroom + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                '}';
    }
}
