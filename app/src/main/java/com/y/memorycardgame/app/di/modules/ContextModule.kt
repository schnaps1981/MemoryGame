package com.y.memorycardgame.app.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val context: Context) {

    @Provides
    @Singleton
    fun providesContext() : Context
    {
        return this.context
    }
}