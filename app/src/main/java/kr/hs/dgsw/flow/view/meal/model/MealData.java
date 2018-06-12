package kr.hs.dgsw.flow.view.meal.model;

import org.hyunjun.school.School;
import org.hyunjun.school.SchoolException;
import org.hyunjun.school.SchoolMenu;

import java.util.Calendar;
import java.util.List;

import kr.hs.dgsw.flow.view.meal.util.MealTask;
import kr.hs.dgsw.flow.view.meal.model.Enum.MealType;

/**
 * Created by Administrator on 2018-04-02.
 */

public class MealData {

    public static final School.Type SCHOOL_TYPE = School.Type.HIGH;
    public static final School.Region REGION = School.Region.DAEGU;
    public static final String SCHOOL_CODE = "D100000282";

    public static final String STR_PARSE_EXCEPTION = "급식 파싱에 실패하였습니다";

    public static final int END_OF_HOUR_BREAKFAST = 7;
    public static final int END_OF_MINUTE_BREAKFAST = 50;
    public static final int END_OF_HOUR_LUNCH = 13;
    public static final int END_OF_MINUTE_LUNCH = 30;
    public static final int END_OF_HOUR_DINNER = 19;
    public static final int END_OF_MINUTE_DINNER = 30;

    /**
     * 급식을 가져오는 객체
     */
    private School school;

    /**
     * 학교 급식 메뉴 리스트
     */
    private List<SchoolMenu> menuList;

    private MealType mealType;

    private int mealYear;
    private int mealMonth;
    private int mealDay;

    public MealData(int year, int month, int day) {
        this.school = new School(SCHOOL_TYPE, REGION, SCHOOL_CODE);
        setMealDate(year, month, day);
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public void setMealDate(int year, int month, int day) {
        this.mealYear = year;
        this.mealMonth = month;
        this.mealDay = day;
        this.mealType = MealType.BREAKFAST;
    }

    public int getMealYear() {
        return mealYear;
    }

    public int getMealMonth() {
        return mealMonth;
    }

    public int getMealDay() {
        return mealDay;
    }

    public String getMeal(int dayOfMonth, MealType mealType) {
        if (menuList == null || menuList.isEmpty()) {
            return null;
        }

        SchoolMenu menu;
        try {
            menu = menuList.get(dayOfMonth - 1);
        } catch (NullPointerException e) {
            return null;
        }

        switch (mealType) {
            case BREAKFAST : return menu.breakfast;
            case LUNCH     : return menu.lunch;
            case DINNER    : return menu.dinner;
            default        : return null;
        }
    }

    /**
     * 다음 시간을 반환함
     * @param year
     * @param month
     * @param day
     * @return
     */
    public MealType getNextMealType(int year, int month, int day) {
        Calendar nowCal = Calendar.getInstance();
        // 날짜만 비교하기 위해서 클론으로 시간은 동일하게 설정
        Calendar requestedCal = (Calendar) nowCal.clone();
        requestedCal.set(year, month, day);

        MealType mealType;

        // 날짜 비교
        if ((nowCal.compareTo(requestedCal) == 0)) {
            Calendar breakfastCal = Calendar.getInstance();
            breakfastCal.set(year, month, day,
                    END_OF_HOUR_BREAKFAST, END_OF_MINUTE_BREAKFAST, 0);

            Calendar lunchCal = Calendar.getInstance();
            lunchCal.set(year, month, day,
                    END_OF_HOUR_LUNCH, END_OF_MINUTE_LUNCH, 0);

            Calendar dinnerCal = Calendar.getInstance();
            dinnerCal.set(year, month, day,
                    END_OF_HOUR_DINNER, END_OF_MINUTE_DINNER, 0);

            mealType = requestedCal.before(breakfastCal) ?
                    MealType.BREAKFAST : requestedCal.before(lunchCal) ?
                    MealType.LUNCH : requestedCal.before(dinnerCal) ?
                    MealType.DINNER : MealType.BREAKFAST;
        } else {
            // 날짜가 다를 경우엔 기본 아침
            mealType = MealType.BREAKFAST;
        }

        return mealType;
    }

    public void loadMeal(int day, int month, LoadCallback loadCallback) {
        new MealTask(school, day, month, new MealTask.Callback() {
            @Override
            public void onSuccess(List<SchoolMenu> responseMenuList) {
                menuList = responseMenuList;
                loadCallback.onLoad();
            }

            @Override
            public void onFailure(SchoolException e) {
                loadCallback.onFailure();
            }
        }).execute();
    }

    public interface LoadCallback {
        void onLoad();
        void onFailure();
    }
}
