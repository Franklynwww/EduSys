package Education_System.pojo;

public class Course {
    protected Integer index;
    protected String id;
    protected String course_name;
    protected float credit;
    protected String reg_department;
    protected String stage;

    public Course() {
    }

    public Course(Integer index, String id, String course_name, float credit, String reg_department, String stage) {
        this.index = index;
        this.id = id;
        this.course_name = course_name;
        this.credit = credit;
        this.reg_department = reg_department;
        this.stage = stage;
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

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public String getReg_department() {
        return reg_department;
    }

    public void setReg_department(String reg_department) {
        this.reg_department = reg_department;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    @Override
    public String toString() {
        return "Course{" +
                "index=" + index +
                ", id='" + id + '\'' +
                ", course_name='" + course_name + '\'' +
                ", credit=" + credit +
                ", reg_department='" + reg_department + '\'' +
                ", stage='" + stage + '\'' +
                '}';
    }
}
