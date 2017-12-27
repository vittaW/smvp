package com.vitta.smvp.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vitta.smvp.R;
import com.vitta.smvp.base.MVPActivity;
import com.vitta.smvp.main.MineFansAdapter;
import com.vitta.smvp.model.http.been.MineFansBeen;

import java.util.List;

import butterknife.BindView;

public class StateTestActivity extends MVPActivity<StateTestPresenter> implements StateTestContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.view_main)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private MineFansAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_state_test;
    }

    @Override
    protected void initView() {
        super.initView();
        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MineFansAdapter(null);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mAdapter.setEmptyView(R.layout.custom_view_empty);
        mSwipe.setOnRefreshListener(this);
        refreshData();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.userOnClick(StateTestActivity.this,mAdapter.getItem(position));
            }
        });
    }

    private void refreshData() {
        setRefresh(true);
        stateLoading();
        mPresenter.getUserList();
    }

    @Override
    public void getUserList(List<MineFansBeen> list) {
        mSwipe.setRefreshing(false);
        stateMain();
        mAdapter.setNewData(list);
    }

    @Override
    public void stateError() {
        super.stateError();
        mSwipe.setRefreshing(false);
    }

    @Override
    public void stateEmpty() {
        super.stateEmpty();
        mSwipe.setRefreshing(false);
    }

    @Override
    public void getMoreUserList(List<MineFansBeen> list) {
        stateMain();
        mAdapter.addData(list);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getMoreUserList();
        setRefresh(false);
    }

    @Override
    public void onRefresh() {
        mPresenter.getUserList();
        setRefresh(true);
    }
}
