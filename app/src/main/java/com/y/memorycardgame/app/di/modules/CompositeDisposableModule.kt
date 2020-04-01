package com.y.memorycardgame.app.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class CompositeDisposableModule {
    @Provides
    fun provideCompositeDisposable() : CompositeDisposable = CompositeDisposable()
}