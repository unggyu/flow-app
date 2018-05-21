package kr.hs.dgsw.flow.view.meal.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.Calendar;

import kr.hs.dgsw.flow.view.meal.model.Enum.DayOfWeek;
import kr.hs.dgsw.flow.view.meal.model.Enum.MealType;

/**
 * Created by Administrator on 2018-03-27.
 */

public class MealDateTextView extends AppCompatTextView {
    public MealDateTextView(Context context) {
        super(context);
    }

    public MealDateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MealDateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setMealDate(int year, int month, int day, MealType mealType) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        int dayOfWeekNum = cal.get(Calendar.DAY_OF_WEEK);

        DayOfWeek dayOfWeek;
        try {
            dayOfWeek = DayOfWeek.values()[dayOfWeekNum - 1];
        } catch (Exception e) {
            super.setText("요일 에러");
            return;
        }

        @SuppressLint("DefaultLocale")
        String text =
                String.format("%d년 %d월 %d일 %s요일 %s",
                        year, month + 1, day, dayOfWeek.getName(), mealType.getMealType());
        super.setText(text);
    }
}
