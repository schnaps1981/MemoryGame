package com.y.memorycardgame.ui.startscreen

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType


interface StartScreenView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setSettingsSpan(span : Int)
}