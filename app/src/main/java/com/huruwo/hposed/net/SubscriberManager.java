package com.huruwo.hposed.net;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SubscriberManager<T> {


    public void toSubscribe(Observable<T> o, final Observer<T> s) {
        o.subscribeOn(Schedulers.io())  //被观察者产生事件的线程
                .unsubscribeOn(Schedulers.io())    //反注册的线程
                .observeOn(AndroidSchedulers.mainThread())  //事件消费的线程
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                }).subscribe(s);
    }
}
