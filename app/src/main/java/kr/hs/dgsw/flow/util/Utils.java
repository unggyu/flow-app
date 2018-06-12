package kr.hs.dgsw.flow.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String dateFormat(String dateTimeStr) throws ParseException {
        @SuppressLint("SimpleDateFormat")
        Date dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'")
                .parse(dateTimeStr);

        @SuppressLint("SimpleDateFormat")
        String resultDateTime = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분")
                .format(dateTime);

        return resultDateTime;
    }
}
