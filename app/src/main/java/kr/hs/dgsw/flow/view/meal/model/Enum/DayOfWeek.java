package kr.hs.dgsw.flow.view.meal.model.Enum;

/**
 * Created by Administrator on 2018-03-27.
 */

public enum DayOfWeek {
    SUNDAY("일"),
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토");

    private String name;

    DayOfWeek(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
