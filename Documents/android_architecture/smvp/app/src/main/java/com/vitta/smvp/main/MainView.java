package com.vitta.smvp.main;

import com.vitta.smvp.base.BaseView;
import com.vitta.smvp.model.MineFansBeen;

import java.util.List;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/22 16:22
 * 描述：MainView
 */

public interface MainView extends BaseView{

    void refreshUserList(List<MineFansBeen> list);
    void loadUserList(List<MineFansBeen> list);
    void loadEnd();

}
