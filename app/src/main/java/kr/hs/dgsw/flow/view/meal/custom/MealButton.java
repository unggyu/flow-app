package kr.hs.dgsw.flow.view.meal.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.view.meal.model.Enum.MealType;

/**
 * Created by Administrator on 2018-03-30.
 */

public class MealButton extends AppCompatButton {

    private MealType mealType;

    public MealButton(Context context) {
        super(context);
    }

    public MealButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MealButton,
                0, 0);

        try {
            mealType = MealType.valueOf(typedArray.getString(R.styleable.MealButton_mealType));
        } finally {
            typedArray.recycle();
        }
    }

    public MealButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MealType getMealType() {
        return mealType;
    }
}
