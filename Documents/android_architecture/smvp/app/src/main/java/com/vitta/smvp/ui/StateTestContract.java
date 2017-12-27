package com.vitta.smvp.ui;

import android.content.Context;

import com.vitta.smvp.base.BasePresenter;
import com.vitta.smvp.base.BaseView;
import com.vitta.smvp.base.MVPresenter;
import com.vitta.smvp.model.http.been.MineFansBeen;

import java.util.List;
import java.util.Map;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/27 14:35
 * 描述：StateTestContract
 */

public interface StateTestContract {

    interface View extends BaseView{
        void getUserList(List<MineFansBeen> list);
        void getMoreUserList(List<MineFansBeen> list);
    }

    interface Presenter extends BasePresenter<View> {
        void getUserList();
        void getMoreUserList();
        void userOnClick(Context context,MineFansBeen mineFansBeen);
    }

}
