package com.vitta.rxjava;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/28 11:42
 * 描述：ApiService
 */

public interface ApiService {

    @GET
    Observable<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET
    Observable<RegisterResponse> register (@Body RegisterRequest registerRequest);

}
