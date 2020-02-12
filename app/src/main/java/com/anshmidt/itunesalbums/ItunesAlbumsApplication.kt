package com.anshmidt.itunesalbums

import android.app.Application
import com.anshmidt.itunesalbums.di.component.ApplicationComponent
import com.anshmidt.itunesalbums.di.component.DaggerApplicationComponent
import com.anshmidt.itunesalbums.di.module.NetworkModule



class ItunesAlbumsApplication : Application() {

    companion object {
        lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        val applicationComponent = initDaggerComponent()
        applicationComponent.inject(this)
        component = applicationComponent
    }

    protected fun initDaggerComponent(): ApplicationComponent {
        return DaggerApplicationComponent
            .builder()
            .networkModule(NetworkModule(this))
            .build()
    }
}