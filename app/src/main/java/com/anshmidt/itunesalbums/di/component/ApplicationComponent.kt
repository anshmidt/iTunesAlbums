package com.anshmidt.itunesalbums.di.component

import com.anshmidt.itunesalbums.ItunesAlbumsApplication
import com.anshmidt.itunesalbums.di.module.AlbumInfoMvpModule
import com.anshmidt.itunesalbums.di.module.MainMvpModule
import com.anshmidt.itunesalbums.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface ApplicationComponent {
    fun inject(itunesAlbumsApplication: ItunesAlbumsApplication)
    fun plus(mainMvpModule: MainMvpModule): MainActivityComponent
    fun plus(albumInfoMvpModule: AlbumInfoMvpModule): AlbumInfoActivityComponent
}