package com.y.memorycardgame.model.gamemanager

import com.y.memorycardgame.model.data.Card
import com.y.memorycardgame.model.data.CardClickResult
import io.reactivex.Observable

interface GameManager {
    fun gameInit() : MutableList<Card>
    fun getGameField() : MutableList<Card>

    fun allCardsMatched() : Boolean

    fun cardClick(card: Card) : Observable<CardClickResult>

    fun start()
    fun stop()
    fun isInProgress() : Boolean

    fun attachGameListener(gameEventListener: GameManagerImpl.GameEventListener)
    fun detachGameListener()



}