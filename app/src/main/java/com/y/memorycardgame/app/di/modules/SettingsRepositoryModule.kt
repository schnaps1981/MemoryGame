package com.y.memorycardgame.app.di.modules

import com.y.memorycardgame.model.repository.SettingsRepository
import com.y.memorycardgame.model.repository.SettingsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SettingsRepositoryModule {

    @Provides
    @Singleton
    fun provideSettings() : SettingsRepository =
        SettingsRepositoryImpl()
}