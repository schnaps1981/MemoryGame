package com.y.memorycardgame.ui.navigation

import androidx.fragment.app.Fragment
import com.y.memorycardgame.ui.boardscreen.BoardScreen
import com.y.memorycardgame.ui.startscreen.StartScreen
import ru.terrakok.cicerone.android.pure.AppScreen
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    class StartScreenNav : SupportAppScreen()
    {
        override fun getFragment() = StartScreen.getInstance()
    }

    class MainScreenNav : SupportAppScreen() {
        override fun getFragment() = BoardScreen.getInstance()
    }

}
