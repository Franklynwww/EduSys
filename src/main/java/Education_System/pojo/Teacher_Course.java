package Education_System.pojo;

public class Teacher_Course {
    protected Integer index;
    protected String teacher_identity;
    protected String course_id;
    protected String clazz_id;
    protected String term;

    public Teacher_Course() {
    }

    public Teacher_Course(Integer index, String teacher_identity, String course_id, String clazz_id, String term) {
        this.index = index;
        this.teacher_identity = teacher_identity;
        this.course_id = course_id;
        this.clazz_id = clazz_id;
        this.term = term;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getTeacher_identity() {
        return teacher_identity;
    }

    public void setTeacher_identity(String teacher_identity) {
        this.teacher_identity = teacher_identity;
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

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    @Override
    public String toString() {
        return "Teacher_Course{" +
                "index=" + index +
                ", teacher_identity='" + teacher_identity + '\'' +
                ", course_id='" + course_id + '\'' +
                ", clazz_id='" + clazz_id + '\'' +
                ", term='" + term + '\'' +
                '}';
    }
}
