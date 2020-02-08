package com.anshmidt.itunesalbums.di.component

import com.anshmidt.itunesalbums.view.activities.MainActivity
import com.anshmidt.itunesalbums.di.module.MvpModule
import com.anshmidt.itunesalbums.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, MvpModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}