package com.y.memorycardgame.app

import android.app.Application
import com.y.memorycardgame.BuildConfig
import com.y.memorycardgame.app.di.components.AppComponent
import com.y.memorycardgame.app.di.modules.ContextModule
import com.y.memorycardgame.app.di.components.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree


class App : Application() {
    companion object {
        lateinit var instance : AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        instance = DaggerAppComponent.builder().contextModule(
            ContextModule(applicationContext)
        ).build()

        initLogger()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                }
            })
        }
    }

}