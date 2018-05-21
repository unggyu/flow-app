package kr.hs.dgsw.flow.view.meal.presenter;

import android.support.annotation.NonNull;

import java.util.Calendar;

import kr.hs.dgsw.flow.view.meal.model.Enum.MealType;
import kr.hs.dgsw.flow.view.meal.model.MealData;

/**
 * Created by Administrator on 2018-03-31.
 */

public class MealPresenterImpl implements IMealContract.Presenter {

    private IMealContract.View mView;

    private MealData mMealData;

    public MealPresenterImpl(@NonNull IMealContract.View view) {
        this.mView = view;

        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        this.mMealData = new MealData(year, month, day);

        loadAndShow(year, month, day);
    }

    @Override
    public void setView(@NonNull IMealContract.View view) {
        this.mView = view;
    }

    @Override
    public void onDestroy() {
        this.mView = null;
    }

    @Override
    public void loadViewState() {
        int year = mMealData.getMealYear();
        int month = mMealData.getMealMonth();
        int day = mMealData.getMealDay();
        MealType mealType = mMealData.getMealType();

        showMealData(year, month, day, mealType);
    }

    @Override
    public void onDatePickButtonClick() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        mView.showDatePickerDialog(year, month, day);
    }

    @Override
    public void onDatePickerDialogDateSet(int year, int month, int dayOfMonth) {
        int mealYear = mMealData.getMealYear();
        int mealMonth = mMealData.getMealMonth();

        if (mealYear == year && mealMonth == month) {
            showMealData(year, month, dayOfMonth);
        } else {
            loadAndShow(year, month, dayOfMonth);
        }
    }

    @Override
    public void onMealButtonClick(MealType mealType) {
        if (mealType != null) {
            int dayOfMonth = mMealData.getMealDay();
            String meal = mMealData.getMeal(dayOfMonth, mealType);

            int year = mMealData.getMealYear();
            int month = mMealData.getMealMonth();
            int day = mMealData.getMealDay();

            mView.showDate(year, month, day, mealType);
            mView.showMeal(meal);

            mMealData.setMealType(mealType);
            mMealData.setMealDate(year, month, day);
        }
    }

    @Override
    public void showMealData(int year, int month, int day) {
        MealType mealType = mMealData.getMealTypeToShow(year, month, day);
        showMealData(year, month, day, mealType);
    }

    /**
     * 인자 값으로 들어온 날짜에 해당하는 급식 메뉴를 보여줌
     * @param year 년
     * @param month 월
     * @param day 일
     * @param mealType 급식 타입
     */
    @Override
    public void showMealData(int year, int month, int day, MealType mealType) {
        String meal = mMealData.getMeal(day, mealType);

        mView.showDate(year, month, day, mealType);
        mView.showMeal(meal);

        mMealData.setMealType(mealType);
        mMealData.setMealDate(year, month, day);
    }

    @Override
    public void loadAndShow(int year, int month, int day) {
        mView.showProgress(true);
        mMealData.loadMeal(year, month, new MealData.LoadCallback() {
            @Override
            public void onLoad() {
                showMealData(year, month, day);
                mView.showProgress(false);
            }

            @Override
            public void onFailure() {
                mView.showMessageToast(MealData.STR_PARSE_EXCEPTION);
            }
        });
    }
}
