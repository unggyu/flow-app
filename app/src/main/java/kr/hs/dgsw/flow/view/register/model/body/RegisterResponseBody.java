package kr.hs.dgsw.flow.view.register.model.body;

public class RegisterResponseBody {
    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RegisterResponseBody{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
