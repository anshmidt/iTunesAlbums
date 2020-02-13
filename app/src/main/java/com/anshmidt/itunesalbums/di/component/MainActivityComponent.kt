package com.anshmidt.itunesalbums.di.component

import com.anshmidt.itunesalbums.di.module.MainMvpModule
import com.anshmidt.itunesalbums.view.activities.MainActivity
import dagger.Subcomponent


@Subcomponent(modules = [MainMvpModule::class])
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)
}