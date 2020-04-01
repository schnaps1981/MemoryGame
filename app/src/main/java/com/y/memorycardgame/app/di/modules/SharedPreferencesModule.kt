package com.y.memorycardgame.app.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.y.memorycardgame.app.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

@Module
class SharedPreferencesModule {

    @Provides
    @Singleton
    fun provodesSharedPreferences(context: Context)
            = context.getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

}