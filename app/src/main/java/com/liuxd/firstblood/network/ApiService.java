package com.liuxd.firstblood.network;

import com.liuxd.firstblood.constant.Constant;
import com.liuxd.firstblood.entity.HttpResult;
import com.liuxd.firstblood.entity.IdCard;
import com.liuxd.firstblood.entity.News;

import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Liuxd on 2016/11/21 14:27.
 */

public interface ApiService {

    //查询新闻
    @GET("toutiao/index?key=1e79b54104857c22282f23672f4cae1f")
    Observable<HttpResult<News>> getNewsByType(@Query("type") String type);

    //查询身份证
    @HTTP(path = Constant.BASE_API_URL + "idcard/index?key=3771a3e549918faff7db9e7151c82df3", method = "GET")
    Observable<HttpResult<IdCard>> searchIdCardByCardNo(@Query("cardno") String cardNo);

    //查询身份证泄漏情况
    @HTTP(path = Constant.BASE_API_URL + "idcard/leak?key=3771a3e549918faff7db9e7151c82df3", method = "GET")
    Observable<HttpResult<IdCard>> searchIdCardLeakByCardNo(@Query("cardno") String cardNo);

    //查询身份证挂失
    @HTTP(path = Constant.BASE_API_URL + "idcard/loss?key=3771a3e549918faff7db9e7151c82df3", method = "GET")
    Observable<HttpResult<IdCard>> searchIdCardLossByCardNo(@Query("cardno") String cardNo);

}
