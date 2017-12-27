package com.vitta.smvp.main;

import android.content.Intent;
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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vitta.smvp.R;
import com.vitta.smvp.model.http.been.MineFansBeen;
import com.vitta.smvp.ui.StateTestActivity;
import com.vitta.smvp.ui.test.TestActivity;

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
        mainPresenter = new MainPresenter(this, this);
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
        mRecycler = (RecyclerView) findViewById(R.id.view_main);
        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MineFansAdapter(null);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mSwipe.setOnRefreshListener(this);
        refreshData();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(MainActivity.this, StateTestActivity.class));
//                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });
    }

    private boolean isRefresh;

    private void refreshData() {
        isRefresh = true;
        stateLoading();
        mainPresenter.refreshData();
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        stateLoading();
        mainPresenter.loadData();
    }

    @Override
    public void onRefresh() {
        refreshData();
    }


    //-------------main-----------------------------------------------------------------------------
    @Override
    public void refreshUserList(List<MineFansBeen> list) {
        mAdapter.setNewData(list);
    }

    @Override
    public void loadUserList(List<MineFansBeen> list) {
        mAdapter.addData(list);
    }

    @Override
    public void stateMain() {
        isRefresh = false;
        mAdapter.loadMoreComplete();
        mSwipe.setRefreshing(false);
    }
    //-------------main-----------------------------------------------------------------------------

    @Override
    public void stateLoading() {
        if (isRefresh) {
            mSwipe.setRefreshing(true);
        }
    }

    @Override
    public void stateEmpty() {
        if (isRefresh) {
            mAdapter.setEmptyView(R.layout.empty);
            mSwipe.setRefreshing(false);
        } else {
            mAdapter.loadMoreEnd();
        }
        isRefresh = false;
    }

    @Override
    public void stateError() {
        if (isRefresh) {
            mAdapter.setEmptyView(R.layout.error);
            mSwipe.setRefreshing(false);
        } else {
            mAdapter.loadMoreFail();
        }
        isRefresh = false;
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }











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
