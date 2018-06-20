package kr.hs.dgsw.flow.util.retrofit.model.signup;

import kr.hs.dgsw.flow.util.retrofit.model.BaseResponseBody;

public class RegisterResponseBody extends BaseResponseBody {
    public RegisterResponseBody(int status, String message) {
        super(status, message);
    }
}
