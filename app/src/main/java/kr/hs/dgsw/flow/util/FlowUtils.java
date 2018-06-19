package kr.hs.dgsw.flow.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import kr.hs.dgsw.flow.util.retrofit.FlowService;
import kr.hs.dgsw.flow.util.retrofit.RetrofitClient;

public class FlowUtils {
    public static final String BASE_URL = "http://flow.cafe24app.com";

    private FlowUtils() { }

    public static FlowService getFlowService() {
        return RetrofitClient.getClient(BASE_URL).create(FlowService.class);
    }

    public static String dateFormat(String dateTimeStr) throws ParseException {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date dateTime = simpleDateFormat.parse(dateTimeStr);

        @SuppressLint("SimpleDateFormat")
        String resultDateTime = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분")
                .format(dateTime);

        return resultDateTime;
    }
}
