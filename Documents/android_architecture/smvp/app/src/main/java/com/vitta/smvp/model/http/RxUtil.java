package com.vitta.smvp.model.http;

import com.vitta.smvp.base.BaseCodeBeen;
import com.vitta.smvp.model.http.exception.ApiException;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/25 17:35
 * 描述：RxUtil
 */

public class RxUtil {

    //compose 操作符 ，配合使用FlowableTransformer

    //线程调度
    public static <T> FlowableTransformer<T, T> handleThread() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    //统一处理返回结果 code message
    public static <T> FlowableTransformer<BaseCodeBeen<T>, T> handleResult() {
        return new FlowableTransformer<BaseCodeBeen<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<BaseCodeBeen<T>> upstream) {
                //转化为另一个发射序列
                return upstream.flatMap(new Function<BaseCodeBeen<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(BaseCodeBeen<T> tBaseCodeBeen) throws Exception {
                        if (tBaseCodeBeen.getRetCode() == 0) {
                            return createData(tBaseCodeBeen.getData());
                        }
                        return Flowable.error(new ApiException(tBaseCodeBeen.getMessage(), tBaseCodeBeen.getRetCode()));
                    }
                });
            }
        };
    }

    /**
     * 使用背压，生成Flowable
     *
     * @param <T>
     * @return Observable<></>
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
