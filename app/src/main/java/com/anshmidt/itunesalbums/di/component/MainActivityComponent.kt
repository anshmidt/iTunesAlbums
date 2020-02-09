package com.anshmidt.itunesalbums.di.component

import com.anshmidt.itunesalbums.di.module.AlbumInfoMvpModule
import com.anshmidt.itunesalbums.view.activities.MainActivity
import com.anshmidt.itunesalbums.di.module.MainMvpModule
import com.anshmidt.itunesalbums.di.module.NetworkModule
import com.anshmidt.itunesalbums.view.activities.AlbumInfoActivity
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton


@Subcomponent(modules = [MainMvpModule::class])
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)
}