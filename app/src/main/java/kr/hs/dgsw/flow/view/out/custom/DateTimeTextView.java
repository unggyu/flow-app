package kr.hs.dgsw.flow.view.out.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class DateTimeTextView extends AppCompatTextView {

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    public DateTimeTextView(Context context) {
        super(context);
    }

    public DateTimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateTimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("SetTextI18n")
    public void setDateTime(int year, int month, int day, int hour, int minute) {
        mYear = year;
        mMonth = month;
        mDay = day;
        mHour = hour;
        mMinute = minute;

        @SuppressLint("DefaultLocale")
        String date = String.format("%04d-%02d-%02d", year, month, day);

        @SuppressLint("DefaultLocale")
        String time = String.format("%02d:%02d", hour, minute);

        this.setText(date + "   " + time);
    }

    public void setDate(int year, int month, int day) {
        mYear = year;
        mMonth = month;
        mDay = day;

        this.setDateTime(year, month, day, mHour, mMinute);
    }

    public void setTime(int hour, int minute) {
        this.setDateTime(mYear, mMonth, mDay, hour, minute);
    }
}
