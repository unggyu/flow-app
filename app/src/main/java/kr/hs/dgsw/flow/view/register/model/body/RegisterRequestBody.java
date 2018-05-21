package kr.hs.dgsw.flow.view.register.model.body;

import com.google.gson.annotations.SerializedName;

public class RegisterRequestBody {

    /**
     * 이메일 주소
     */
    @SerializedName("email")
    private String email;

    /**
     * 비밀번호
     */
    @SerializedName("pw")
    private String password;

    /**
     * 이름
     */
    @SerializedName("name")
    private String name;

    /**
     * 성별
     */
    @SerializedName("gender")
    private String gender;

    /**
     * 휴대폰 번호
     */
    @SerializedName("mobile")
    private String cellphone;

    /**
     * 반
     */
    @SerializedName("class_idx")
    private Integer _class;

    /**
     * 번호
     */
    @SerializedName("class_number")
    private Integer number;

    public RegisterRequestBody(String email,
                               String password,
                               String name,
                               String gender,
                               String cellphone,
                               int _class,
                               int number) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.cellphone = cellphone;
        this._class = _class;
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int get_class() {
        return _class;
    }

    public void set_class(int _class) {
        this._class = _class;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
