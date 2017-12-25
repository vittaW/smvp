package com.vitta.smvp.main;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.vitta.smvp.base.BaseCodeBeen;
import com.vitta.smvp.base.CommenObserver;
import com.vitta.smvp.model.MineFansBeen;
import com.vitta.smvp.model.http.ApiService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/22 11:45
 * 描述：响应 MainActivity 的UI 操作（处理逻辑），链接数据层和View层
 */

class MainPresenter {
    private AppCompatActivity activity;

    private MainView mainView;

    MainPresenter(AppCompatActivity activity, MainView mainView) {
        this.activity = activity;
        this.mainView = mainView;
    }

    void setToolBar(Toolbar toolBar) {
        activity.setSupportActionBar(toolBar);
    }

    void fabOnClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    void invokeCamera() {

    }

    void invokeGallery() {

    }

    void invokeSlideShow() {

    }

    void invokeTools() {

    }

    private ApiService apiService = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://api.xiaomatv.cn")
            .build().create(ApiService.class);

    private int mPage = 1;

    public void refreshData() {
        apiService.getMineFansBeen(createMap(1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommenObserver<List<MineFansBeen>>(mainView) {
                    @Override
                    public void onNext(BaseCodeBeen<List<MineFansBeen>> listBaseCodeBeen) {
                        List<MineFansBeen> t = listBaseCodeBeen.getData();
                        if (t == null || t.size() == 0) {
                            mainView.refreshEmpty();
                        } else {
                            mainView.refreshUserList(t);
                        }
                    }
                });
    }

    private Map<String, String> createMap(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "QVBIOjExNTgyOjI5M2JkOWY0ZjE5NjQ2ZjBhMmNmMDM1NzE5N2E5NWFj");
        map.put("user_id", "11582");
        map.put("type", "2");//type 1 : 关注的人列表；2 ： 粉丝列表；
        map.put("page_num", String.valueOf(page));
        return map;
    }

    public void loadData() {
        apiService.getMineFansBeen(createMap(++mPage))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommenObserver<List<MineFansBeen>>(mainView) {
                    @Override
                    public void onNext(BaseCodeBeen<List<MineFansBeen>> listBaseCodeBeen) {
                        List<MineFansBeen> t = listBaseCodeBeen.getData();
                        if (t == null || t.size() == 0) {
                            mainView.loadEnd();
                        } else {
                            mainView.loadUserList(t);
                        }
                    }
                });
    }
}
