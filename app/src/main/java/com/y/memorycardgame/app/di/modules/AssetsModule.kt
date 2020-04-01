package com.y.memorycardgame.app.di.modules

import android.content.Context
import android.content.res.AssetManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AssetsModule {

    @Provides
    @Singleton
    fun providesAssets(context: Context) : AssetManager
    {
        return context.assets
    }
}