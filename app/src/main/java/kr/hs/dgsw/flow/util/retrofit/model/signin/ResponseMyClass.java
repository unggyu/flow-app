package kr.hs.dgsw.flow.util.retrofit.model.signin;

import com.google.gson.annotations.SerializedName;

public class ResponseMyClass {
    @SerializedName("grade")
    private Integer grade;

    @SerializedName("class_room")
    private Integer classRoom;

    @SerializedName("class_number")
    private Integer classNumber;

    public ResponseMyClass(Integer grade, Integer classRoom, Integer classNumber) {
        this.grade = grade;
        this.classRoom = classRoom;
        this.classNumber = classNumber;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(Integer classRoom) {
        this.classRoom = classRoom;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(Integer classNumber) {
        this.classNumber = classNumber;
    }

    @Override
    public String toString() {
        return "{" +
                "grade=" + grade +
                ", classRoom=" + classRoom +
                ", classNumber=" + classNumber +
                '}';
    }
}
