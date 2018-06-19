package kr.hs.dgsw.flow.util.retrofit;

import kr.hs.dgsw.flow.util.retrofit.model.signin.LoginRequestBody;
import kr.hs.dgsw.flow.util.retrofit.model.signin.LoginResponseBody;
import kr.hs.dgsw.flow.util.retrofit.model.notice.NoticeResponseBody;
import kr.hs.dgsw.flow.util.retrofit.model.notice.NoticeDetailsResponseBody;
import kr.hs.dgsw.flow.util.retrofit.model.out.OutRequestBody;
import kr.hs.dgsw.flow.util.retrofit.model.out.OutResponseBody;
import kr.hs.dgsw.flow.util.retrofit.model.signup.RegisterRequestBody;
import kr.hs.dgsw.flow.util.retrofit.model.signup.RegisterResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @Headers("Content_Type: application/json")
    @GET("/notice/{idx}")
    Call<NoticeDetailsResponseBody> getNotice(@Header("x-access-token") String token, @Path("idx") int idx);
}
