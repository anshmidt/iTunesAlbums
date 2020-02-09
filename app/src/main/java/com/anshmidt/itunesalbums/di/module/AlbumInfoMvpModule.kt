package com.anshmidt.itunesalbums.di.module

import com.anshmidt.itunesalbums.mvp.contracts.AlbumInfoViewPresenterContract
import dagger.Module
import dagger.Provides

@Module
class AlbumInfoMvpModule(var view: AlbumInfoViewPresenterContract.View) {

    @Provides
    fun provideView(): AlbumInfoViewPresenterContract.View {
        return view
    }

}