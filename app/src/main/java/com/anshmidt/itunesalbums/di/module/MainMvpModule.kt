package com.anshmidt.itunesalbums.di.module

import com.anshmidt.itunesalbums.mvp.contracts.MainViewPresenterContract
import com.anshmidt.itunesalbums.mvp.presenters.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainMvpModule(var view: MainViewPresenterContract.View) {

    @Provides
    fun provideView(): MainViewPresenterContract.View {
        return view
    }

}