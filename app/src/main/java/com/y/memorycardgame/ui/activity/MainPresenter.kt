package com.y.memorycardgame.ui.activity

import com.y.memorycardgame.app.App
import com.y.memorycardgame.ui.navigation.Screens
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject lateinit var router : Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        App.instance.inject(this)
        router.replaceScreen(Screens.StartScreenNav())
    }
}