package Education_System.pojo;

public class Student_User extends User {
    protected String clazz_id;

    public Student_User() {
    }

    public Student_User(Integer index, String identity, String name, String password, String email, String clazz_id) {
        super(index, identity, name, password, email);
        this.clazz_id = clazz_id;
    }

    @Override
    public String getClazz_id() {
        return clazz_id;
    }

    @Override
    public void setClazz_id(String clazz_id) {
        this.clazz_id = clazz_id;
    }

    @Override
    public String toString() {
        return "Student_User{" +
                "index=" + index +
                ", identity='" + identity + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", clazz_id='" + clazz_id + '\'' +
                '}';
    }
}
