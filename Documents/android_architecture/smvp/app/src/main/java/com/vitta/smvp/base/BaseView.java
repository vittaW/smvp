package com.vitta.smvp.base;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/22 16:33
 * 描述：P 和 Activity ，Fragment 回调的接口！
 * P 是主动的一方，P 主动去调用接口的方法，View 去实现这个接口。
 * View 显示数据变化。响应交互事件。
 */

public interface BaseView {

    void stateMain();

    void stateLoading();

    void stateEmpty();

    //网络请求失败，①显示错误的布局②吐丝错误信息
    void stateError();

    void showErrorMessage(String message);

}
