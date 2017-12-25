package com.vitta.smvp.model.http;

import com.vitta.smvp.base.BaseCodeBeen;
import com.vitta.smvp.model.MineFansBeen;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/22 15:20
 * 描述：ApiService
 */

public interface ApiService {

    /**
     * 我的粉丝
     * /J_member/userFollowList?Authorization=SVBIOjExMTEyOjQ1NjIxZWQ5NmVkYzdlYjE4N2I0M2Y0Y2MwNDQ3NDhm&type=1&page_num=2
     */
    @GET("/J_member/userFollowList")
    Observable<BaseCodeBeen<List<MineFansBeen>>> getMineFansBeen(@QueryMap Map<String, String> map);

}
