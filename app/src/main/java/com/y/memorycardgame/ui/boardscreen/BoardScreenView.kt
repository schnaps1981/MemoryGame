package com.y.memorycardgame.ui.boardscreen

import com.y.memorycardgame.model.data.Card
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface BoardScreenView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun initBoard(cards : MutableList<Card>)

    @StateStrategyType(SkipStrategy::class)
    fun initRecycler(span : Int)

    @StateStrategyType(SkipStrategy::class)
    fun flipCard(card: Card)

    @StateStrategyType(SkipStrategy::class)
    fun flipBack(firstCard: Card, secondCard: Card)

    @StateStrategyType(SkipStrategy::class)
    fun updateTimer(timer : Long)

    @StateStrategyType(SkipStrategy::class)
    fun updateScore(score: Int)

    @StateStrategyType(SkipStrategy::class)
    fun gameEndDialog(gameEndStatus : Int, score : Int)
}