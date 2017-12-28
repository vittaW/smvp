package com.vitta.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        createReactX();
//        disposableCutAccept();
//        switchThread();
        rxNetRetrofit();
    }

    /**
     * 创建响应式编程  链式编程
     * 发送者 → 接收者
     */
    private void createReactX() {
        //创建一个上游发送者
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(5);
                e.onNext(10);
                e.onComplete();
            }
        });

        //创建一个下游接收者
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: " );
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " + integer );
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " );
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );
            }
        };
        //建立订阅关系 - 在调用了subscribe 方法后才开始发送事件
        observable.subscribe(observer);


        //------------------------------------------------------------------------------------------
        //把这段代码连起来就写成了RxJava 引以为傲的链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(10);
                e.onNext(100);
                e.onNext(1000);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: " );
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " + integer );
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " );
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );
            }
        });
    }

    /** Disposable 理解为上下游之间的阀门。dispose（） 可以切断，导致下游收不到事件
     *
     * onNext  一直发送
     * onComplete  与onError 互斥，只能发送一个
     * onError
     * 上游调用onNext ，下游一直接收；上游调用onComplete 或者onError ，上游继续发送，而下游不再接收
     * ObservableEmitter 发射器
     */
    private void disposableCutAccept() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Log.e(TAG, "subscribe: 1" );
                e.onNext(2);
                Log.e(TAG, "subscribe: 2" );
                e.onNext(3);
                Log.e(TAG, "subscribe: 3" );
                e.onNext(4);
                Log.e(TAG, "subscribe: 4" );
                e.onNext(5);
                Log.e(TAG, "subscribe: 5" );
            }
        }).subscribe(new Observer<Integer>() {

            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: " );
                disposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                if (integer == 2){
                    disposable.dispose();
                }
                Log.e(TAG, "onNext: " + integer );
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " );
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );
            }
        });

        //subscribe 还有几个重载方法
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(50);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                //表示下游只关心 onNext方法
                Log.e(TAG, "accept: " + integer );
            }
        });
    }


    /** 线程切换
     * 每调用一次observeOn 线程就会切换一次（接收的线程）；发送的线程知识第一次有用（subscribeOn 只第一次有用）
     *
     * RxJava 内部的线程 - 内部通过线程池维护，所以线程创建，切换的效率也比较高
     * Schedules.io()   io线程，常用欲网络，读写文件，数据库等io 密集型操作
     * Schedules.newThread()    常规的新线程
     * Schedules.computation()   密集计算线程
     * AndroidSchedules.mainThread()   Android 主线程
     */
    private void switchThread() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "Observable:thread " + Thread.currentThread().getName());
                Log.e(TAG, "emit :1");
                e.onNext(1);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "Observer:thread " + Thread.currentThread().getName());
                Log.e(TAG, "accept: " + integer);
            }
        };

//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(consumer);
        observable.subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "after observeOn mainThread : " + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "after observeOn ioThread: " + Thread.currentThread().getName());
                    }
                })
                .subscribe(consumer);
        }

    /**
     *
     */
    private void rxNetRetrofit() {
        ApiService apiService = getRetrofitClient().create(ApiService.class);
        //TODO

    }

    private Retrofit getRetrofitClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS);
        if (BuildConfig.DEBUG){
            //http log 拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        return new Retrofit.Builder()
                .baseUrl("https://api.xiaomatv.cn")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();
    }
}
