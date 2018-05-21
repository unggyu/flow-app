package kr.hs.dgsw.flow.data.model;

public class EditData {
    private String data;
    private boolean isValid;

    public EditData() {
        this.data = "";
        this.isValid = false;
    }

    public EditData(String data) {
        this.data = data;
        this.isValid = false;
    }

    public EditData(String data, boolean isValid) {
        this.data = data;
        this.isValid = isValid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
