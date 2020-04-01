package com.y.memorycardgame.model.repository

import com.y.memorycardgame.model.data.Settings

interface SettingsRepository {
    fun setFiledSpans(spans : Int)

    fun getSettings() : Settings
}