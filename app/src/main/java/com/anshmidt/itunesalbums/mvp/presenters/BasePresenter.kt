package com.anshmidt.itunesalbums.mvp.presenters

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter {
    protected var subscriptions = CompositeDisposable()

    open fun onViewStopped() {
        if (!subscriptions.isDisposed) {
            subscriptions.clear()
        }
    }

    open fun onViewDestroyed() {
        if (!subscriptions.isDisposed) {
            subscriptions.dispose()
        }
    }
}