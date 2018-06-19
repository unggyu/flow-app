package kr.hs.dgsw.flow.util.retrofit.model.signin;

import com.google.gson.annotations.SerializedName;

public class ResponseUser {
    @SerializedName("email")
    private String email;

    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private String gender;

    @SerializedName("mobile")
    private String cellphone;

    @SerializedName("my_class")
    private ResponseMyClass myClass;

    public ResponseUser(String email, String name, String gender, String cellphone, ResponseMyClass myClass) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.cellphone = cellphone;
        this.myClass = myClass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public ResponseMyClass getMyClass() {
        return myClass;
    }

    public void setMyClass(ResponseMyClass myClass) {
        this.myClass = myClass;
    }

    @Override
    public String toString() {
        return "{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", myClass=" + myClass.toString() +
                '}';
    }
}
