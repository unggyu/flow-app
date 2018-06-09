package kr.hs.dgsw.flow.view.out.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.view.out.model.Enum.OutType;

public class OutRadioButton extends AppCompatRadioButton {

    private OutType outType;

    public OutRadioButton(Context context) {
        super(context);
    }

    public OutRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray arr = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.OutRadioButton,
                0, 0
        );

        if (arr.hasValue(R.styleable.OutRadioButton_outType)) {
            int ordinal = arr.getInt(R.styleable.OutRadioButton_outType, 0);
            if (ordinal >= 0 && ordinal < OutType.values().length) {
                outType = OutType.values()[ordinal];
            }
        }

        arr.recycle();
    }

    public OutRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OutType getOutType() {
        return outType;
    }

    public void setOutType(OutType outType) {
        this.outType = outType;
    }
}
