package kr.hs.dgsw.flow.view.meal.model.Enum;

/**
 * Created by Administrator on 2018-03-20.
 */

public enum MealType {
    BREAKFAST("아침"),
    LUNCH("점심"),
    DINNER("저녁"),
    NEXT_DAY_BREAKFAST("아침");

    private String mealType;

    MealType(String mealType) {
        this.mealType = mealType;
    }

    public String getMealType() {
        return this.mealType;
    }
}
