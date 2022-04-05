package Education_System.pojo;

public class Manager{
    protected Integer index;
    protected String identity;
    protected String password;

    public Manager() {
    }

    public Manager(Integer index, String identity, String password) {
        this.index = index;
        this.identity = identity;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "index=" + index +
                ", identity='" + identity + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
