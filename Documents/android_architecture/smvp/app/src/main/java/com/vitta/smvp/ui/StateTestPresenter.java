package com.vitta.smvp.ui;

import android.content.Context;
import android.widget.Toast;

import com.vitta.smvp.base.BaseCodeBeen;
import com.vitta.smvp.base.CommonSubscriber;
import com.vitta.smvp.base.MVPresenter;
import com.vitta.smvp.model.DataManager;
import com.vitta.smvp.model.http.RxUtil;
import com.vitta.smvp.model.http.been.MineFansBeen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/27 15:04
 * 描述：StateTestPresenter
 */

public class StateTestPresenter extends MVPresenter<StateTestContract.View> implements StateTestContract.Presenter {

    private int mPage = 1;

    @Override
    public void getUserList() {
        mPage = 1;
//        addSubscribe(
                DataManager.getInstance()
               .getFans(createMap(mPage))
               .compose(RxUtil.<BaseCodeBeen<List<MineFansBeen>>>handleThread())
               .compose(RxUtil.<List<MineFansBeen>>handleResult())
               .subscribeWith(new CommonSubscriber<List<MineFansBeen>>(mView) {
                   @Override
                   protected void onNextNotEmpty(List<MineFansBeen> mineFansBeens) {
                       mView.getUserList(mineFansBeens);
                   }
               });
//        );
    }

    @Override
    public void getMoreUserList() {
//        addSubscribe(
                DataManager.getInstance()
                        .getFans(createMap(++mPage))
                        .compose(RxUtil.<BaseCodeBeen<List<MineFansBeen>>>handleThread())
                        .compose(RxUtil.<List<MineFansBeen>>handleResult())
                        .subscribeWith(new CommonSubscriber<List<MineFansBeen>>(mView) {
                            @Override
                            protected void onNextNotEmpty(List<MineFansBeen> mineFansBeens) {
                                mView.getMoreUserList(mineFansBeens);
                            }

                            @Override
                            public void onError(Throwable t) {
                                super.onError(t);
                                --mPage;
                            }
                        });
//        );
    }

    @Override
    public void userOnClick(Context context, MineFansBeen mineFansBeen) {
        Toast.makeText(context, mineFansBeen.getUser_nickname(), Toast.LENGTH_SHORT).show();
    }

    private Map<String, String> createMap(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "QVBIOjExNTgyOjI5M2JkOWY0ZjE5NjQ2ZjBhMmNmMDM1NzE5N2E5NWFj");
        map.put("user_id", "11582");
        map.put("type", "2");//type 1 : 关注的人列表；2 ： 粉丝列表；
        map.put("page_num", String.valueOf(page));
        return map;
    }
}
