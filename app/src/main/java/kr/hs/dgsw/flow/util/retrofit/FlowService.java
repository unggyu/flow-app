package kr.hs.dgsw.flow.util.retrofit;

import kr.hs.dgsw.flow.view.login.model.body.LoginRequestBody;
import kr.hs.dgsw.flow.view.login.model.body.LoginResponseBody;
import kr.hs.dgsw.flow.view.notice.model.NoticeResponseBody;
import kr.hs.dgsw.flow.view.out.model.body.OutRequestBody;
import kr.hs.dgsw.flow.view.out.model.body.OutResponseBody;
import kr.hs.dgsw.flow.view.register.model.body.RegisterRequestBody;
import kr.hs.dgsw.flow.view.register.model.body.RegisterResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FlowService {
    @Headers("Content-Type: application/json")
    @POST("/auth/signup")
    Call<RegisterResponseBody> signUp(@Body RegisterRequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("/auth/signin")
    Call<LoginResponseBody> signIn(@Body LoginRequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("/out/go")
    Call<OutResponseBody> outGo(@Header("x-access-token") String token, @Body OutRequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("/out/sleep")
    Call<OutResponseBody> outSleep(@Header("x-access-token") String token, @Body OutRequestBody requestBody);

    @Headers("Content-Type: application/json")
    @GET("/notice")
    Call<NoticeResponseBody> getNotices(@Header("x-access-token") String token);
}
