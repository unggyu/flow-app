package kr.hs.dgsw.flow.view.meal.presenter;

import kr.hs.dgsw.flow.base.BasePresenter;
import kr.hs.dgsw.flow.view.meal.model.Enum.MealType;

/**
 * Created by Administrator on 2018-03-31.
 */

public interface IMealContract {

    interface View {
        void showProgress(final boolean show);
        void showDate(int year, int month, int day, MealType mealType);
        void showMeal(String meal);
        void showDatePickerDialog(int defaultYear, int defaultMonth, int defaultDay);
        void showMessage(String msg);

        void setSelectedMealButton(MealType mealType);

        void showMessageToast(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void loadViewState();

        void onDatePickButtonClick();
        void onDatePickerDialogDateSet(int year, int month, int dayOfMonth);
        void onMealButtonClick(MealType mealType);

        void showMealData(int year, int month, int day);
        void showMealData(int year, int month, int day, MealType mealType);
        void loadAndShow(int year, int month, int day);
    }
}
