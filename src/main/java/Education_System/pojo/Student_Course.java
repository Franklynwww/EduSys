package Education_System.pojo;

public class Student_Course {
    protected Integer index;
    protected String student_identity;
    protected String course_id;
    protected int score;
    protected String state;

    public Student_Course() {
    }

    public Student_Course(Integer index, String student_identity, String course_id, int score, String state) {
        this.index = index;
        this.student_identity = student_identity;
        this.course_id = course_id;
        this.score = score;
        this.state = state;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getStudent_identity() {
        return student_identity;
    }

    public void setStudent_identity(String student_identity) {
        this.student_identity = student_identity;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Student_Course{" +
                "index=" + index +
                ", student_identity='" + student_identity + '\'' +
                ", course_id='" + course_id + '\'' +
                ", score=" + score +
                ", state='" + state + '\'' +
                '}';
    }
}
