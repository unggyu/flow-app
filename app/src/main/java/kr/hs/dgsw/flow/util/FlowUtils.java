package kr.hs.dgsw.flow.util;

public class FlowUtils {
    public static final String BASE_URL = "http://flow.cafe24app.com";

    private FlowUtils() { }

    public static FlowService getFlowService() {
        return RetrofitClient.getClient(BASE_URL).create(FlowService.class);
    }
}
