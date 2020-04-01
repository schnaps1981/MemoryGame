package com.y.memorycardgame.model.repository

import android.content.SharedPreferences
import com.y.memorycardgame.app.App
import com.y.memorycardgame.model.data.Settings
import com.y.memorycardgame.model.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl : SettingsRepository {
    @Inject
    lateinit var prefs: SharedPreferences

    private val SPAN_NUM_KEY = "SPAN_NUM"

    init {
        App.instance.inject(this)
    }

    override fun setFiledSpans(spans: Int) {
        prefs.edit().putInt(SPAN_NUM_KEY, spans).apply()
    }

    override fun getSettings(): Settings {
        return Settings(prefs.getInt(SPAN_NUM_KEY, 4))

    }
}