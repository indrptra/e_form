package com.kreditplus.eform.presenter;

/**
 * Created by Iwan Nurdesa on 24/05/16.
 */
public interface BasePresenter<V> {
    void attachView(V view);
    void detachView();
}
