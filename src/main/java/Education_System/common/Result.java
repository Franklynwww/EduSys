package Education_System.common;

public class Result {
    private int code;
    private String msg;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
    public static Result success(){
        return new Result(1,"success");
    }
    public static Result error(int code,String msg){
        return new Result(code,msg);
    }
}
