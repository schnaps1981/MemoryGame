package com.y.memorycardgame.ui.startscreen

import com.y.memorycardgame.app.App
import com.y.memorycardgame.model.repository.SettingsRepository
import com.y.memorycardgame.ui.navigation.Screens
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class StartScreenPresenter: MvpPresenter<StartScreenView>() {
    @Inject lateinit var router : Router
    @Inject lateinit var settings : SettingsRepository

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        App.instance.inject(this)

        viewState.setSettingsSpan(settings.getSettings().span_num)
    }

    fun startGameClick() {
        router.replaceScreen(Screens.MainScreenNav())
    }

    fun setFiledSpan(fl: Float) {
        settings.setFiledSpans(fl.toInt())
    }
}