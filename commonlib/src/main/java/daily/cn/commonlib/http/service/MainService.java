package daily.cn.commonlib.http.service;

import java.util.Map;

import daily.cn.commonlib.http.result.HttpResult;
import daily.cn.commonlib.http.util.ApiConstants;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * 网络请求service
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 */

public interface MainService {
    @FormUrlEncoded
    @POST(ApiConstants.BRIDGE_TRANSFER)
    Observable<ResponseBody> bridgeTransfer(@Field("api_code") String apiCode, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST(ApiConstants.DYNAMIC_ENTRY_KEY_API)
    Observable<HttpResult<String>> getDynamicEntryKey(@Field("username") String username);

    @POST(ApiConstants.GET_RS_APT_TIME_API)
    Observable<HttpResult<String>> getOptTime();

    @FormUrlEncoded
    @POST(ApiConstants.RESET_OTP_TOKEN_API)
    Observable<HttpResult<String>> isSecretKeyIsReset(@Field("username") String username);

    @POST(ApiConstants.LOGOUT_API)
    Observable<HttpResult> logout();

    @GET
    Observable<ResponseBody> scanLoginForWeb(@Url String url);

    @POST
    Observable<ResponseBody> casLoginForWeb(@Url String url);
}
