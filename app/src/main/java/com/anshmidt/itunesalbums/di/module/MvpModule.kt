package com.anshmidt.itunesalbums.di.module

import com.anshmidt.itunesalbums.presenters.MainViewPresenterContract
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MvpModule(var view: MainViewPresenterContract.View) {

    @Provides
    fun provideView(): MainViewPresenterContract.View {
        return view
    }

}