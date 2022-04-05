package Education_System.pojo;

public class Clazz {
    protected Integer index;
    protected String id;
    protected String department;
    protected String clazz_name;
    protected String stage;
    protected String grade;

    public Clazz() {
    }

    public Clazz(Integer index, String id, String department, String clazz_name, String stage, String grade) {
        this.index = index;
        this.id = id;
        this.department = department;
        this.clazz_name = clazz_name;
        this.stage = stage;
        this.grade = grade;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getClazz_name() {
        return clazz_name;
    }

    public void setClazz_name(String clazz_name) {
        this.clazz_name = clazz_name;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "index=" + index +
                ", id='" + id + '\'' +
                ", department='" + department + '\'' +
                ", clazz_name='" + clazz_name + '\'' +
                ", stage='" + stage + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
