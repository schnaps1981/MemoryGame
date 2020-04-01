package com.y.memorycardgame.app.di.modules

import com.y.memorycardgame.model.gamemanager.GameManager
import com.y.memorycardgame.model.gamemanager.GameManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GameManagerModule {
    @Provides
    @Singleton
    fun providesGameManager() : GameManager = GameManagerImpl()
}