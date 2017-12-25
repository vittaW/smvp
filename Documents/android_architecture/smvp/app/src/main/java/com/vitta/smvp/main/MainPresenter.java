package com.vitta.smvp.main;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.vitta.smvp.base.BaseCodeBeen;
import com.vitta.smvp.base.CommonSubscriber;
import com.vitta.smvp.model.DataManager;
import com.vitta.smvp.model.http.RxUtil;
import com.vitta.smvp.model.http.been.MineFansBeen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private int mPage = 1;

    public void refreshData() {
        DataManager.getInstance().getFans(createMap(1))
                .compose(RxUtil.<BaseCodeBeen<List<MineFansBeen>>>handleThread()) // 处理线程问题
                .compose(RxUtil.<List<MineFansBeen>>handleResult())//统一处理 code message
                .subscribeWith(new CommonSubscriber<List<MineFansBeen>>(mainView) {
                    @Override
                    protected void onNextNotEmpty(List<MineFansBeen> mineFansBeens) {
                        mainView.refreshUserList(mineFansBeens);
                    }
                });
//        apiService.getMineFansBeen(createMap(1))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new CommonSubscriber<List<MineFansBeen>>(mainView) {
//                    @Override
//                    public void onNext(BaseCodeBeen<List<MineFansBeen>> listBaseCodeBeen) {
//                        List<MineFansBeen> t = listBaseCodeBeen.getData();
//                        if (t == null || t.size() == 0) {
//                            mainView.stateEmpty();
//                        } else {
//                            mainView.refreshUserList(t);
//                        }
//                    }
//                });
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
        DataManager.getInstance().getFans(createMap(++mPage))
                .compose(RxUtil.<BaseCodeBeen<List<MineFansBeen>>>handleThread())
                .compose(RxUtil.<List<MineFansBeen>>handleResult())
                .subscribe(new CommonSubscriber<List<MineFansBeen>>(mainView) {
                    @Override
                    protected void onNextNotEmpty(List<MineFansBeen> mineFansBeens) {
                        mainView.loadUserList(mineFansBeens);
                    }
                });
//        apiService.getMineFansBeen(createMap(++mPage))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new CommonSubscriber<List<MineFansBeen>>(mainView) {
//                    @Override
//                    public void onNext(BaseCodeBeen<List<MineFansBeen>> listBaseCodeBeen) {
//                        List<MineFansBeen> t = listBaseCodeBeen.getData();
//                        if (t == null || t.size() == 0) {
//                            mainView.stateMain();
//                        } else {
//                            mainView.loadUserList(t);
//                        }
//                    }
//                });
    }
}
