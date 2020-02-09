package com.anshmidt.itunesalbums.di.component

import com.anshmidt.itunesalbums.di.module.AlbumInfoMvpModule
import com.anshmidt.itunesalbums.view.activities.AlbumInfoActivity
import dagger.Component
import dagger.Subcomponent

@Subcomponent(modules = [AlbumInfoMvpModule::class])
interface AlbumInfoActivityComponent {
    fun inject(albumInfoActivity: AlbumInfoActivity)
}