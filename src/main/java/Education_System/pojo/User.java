package Education_System.pojo;

public abstract class User {
    protected Integer index;
    protected String identity;
    protected String name;
    protected String password;
    protected String email;

    public User() {
    }

    public User(Integer index, String identity, String name, String password, String email) {
        this.index = index;
        this.identity = identity;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClazz_id(){return null;}
    public void setClazz_id(String clazz_id){}
    public String getDepartment(){return null;}
    public void setDepartment(String department){}
    public String getTitle(){return null;}
    public void setTitle(String title){}
}
