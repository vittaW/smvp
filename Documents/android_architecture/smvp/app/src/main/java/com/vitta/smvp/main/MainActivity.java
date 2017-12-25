package com.vitta.smvp.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vitta.smvp.R;
import com.vitta.smvp.model.MineFansBeen;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, MainView {

    MainPresenter mainPresenter;
    private RecyclerView mRecycler;
    private MineFansAdapter mAdapter;
    private SwipeRefreshLayout mSwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //连接Presenter
        mainPresenter = new MainPresenter(this,this);
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mainPresenter.setToolBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.fabOnClick(view);
            }
        });
        //drawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //RecyclerView 初始化
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new GridLayoutManager(this,2));
        mAdapter = new MineFansAdapter(null);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this,mRecycler);
        mSwipe.setOnRefreshListener(this);
        refreshData();
    }

    private void refreshData() {
        mSwipe.setRefreshing(true);
        mainPresenter.refreshData();
    }

    @Override
    public void onLoadMoreRequested() {
        mainPresenter.loadData();
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    @Override
    public void refreshUserList(List<MineFansBeen> list) {
        mSwipe.setRefreshing(false);
        mAdapter.setNewData(list);
    }

    @Override
    public void loadUserList(List<MineFansBeen> list) {
        mAdapter.addData(list);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void loadEnd() {
        mAdapter.loadMoreEnd();
    }

    @Override
    public void loadError(String message) {
        mAdapter.loadMoreFail();
        mSwipe.setRefreshing(false);
    }

    @Override
    public void refreshEmpty() {
        mAdapter.setEmptyView(R.layout.empty);
        mSwipe.setRefreshing(false);
    }

    //还差刷新错误的状态没有写








    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            mainPresenter.invokeCamera();
        } else if (id == R.id.nav_gallery) {
            mainPresenter.invokeGallery();
        } else if (id == R.id.nav_slideshow) {
            mainPresenter.invokeSlideShow();
        } else if (id == R.id.nav_manage) {
            mainPresenter.invokeTools();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
