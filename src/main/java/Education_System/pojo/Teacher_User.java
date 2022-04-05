package Education_System.pojo;

public class Teacher_User extends User {
    protected String department;
    protected String title;

    public Teacher_User() {
    }

    public Teacher_User(Integer index, String identity, String name, String password, String email, String department, String title) {
        super(index, identity, name, password, email);
        this.department = department;
        this.title = title;
    }

    @Override
    public String getDepartment() {
        return department;
    }

    @Override
    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Teacher_User{" +
                ", index=" + index +
                ", identity='" + identity + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
