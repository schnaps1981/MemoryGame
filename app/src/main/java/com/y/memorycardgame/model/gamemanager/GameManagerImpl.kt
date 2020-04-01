package com.y.memorycardgame.model.gamemanager

import android.os.CountDownTimer
import com.y.memorycardgame.app.App
import com.y.memorycardgame.app.Constants.GAME_TIME_SEC
import com.y.memorycardgame.model.data.Card
import com.y.memorycardgame.model.data.CardClickResult
import com.y.memorycardgame.model.data.CardStatus
import com.y.memorycardgame.model.data.GameEndStatus
import com.y.memorycardgame.model.repository.ImagesRepo
import com.y.memorycardgame.model.repository.SettingsRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class GameManagerImpl : GameManager {

    @Inject
    lateinit var imagesRepo: ImagesRepo

    @Inject
    lateinit var settings: SettingsRepository

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    private var cardsList: MutableList<Card> = mutableListOf()
    private var gameEventListener: GameEventListener? = null
    private var timer: CountDownTimer? = null
    private var fCard: Card? = null
    private var sCard: Card? = null
    private var cardClickResult: CardClickResult =
        CardClickResult(null, null, CardStatus.CARD_STATUS_NOTHING)
    private var isFirstCardFlipped = false
    private var score = 0
    private var isGameInProgress = false

    init {
        App.instance.inject(this)
    }

    override fun gameInit(): MutableList<Card> {
        val numSpans = settings.getSettings().span_num
        val numCardPairs = numSpans * numSpans / 2

        for (i in 1..numCardPairs) {
            val ci = imagesRepo.getRandomImage()
            cardsList.add(Card(ci))
            cardsList.add(Card(ci))
        }

        cardsList.shuffle()

        isFirstCardFlipped = false
        score = 0

        return cardsList
    }

    override fun isInProgress(): Boolean {
        return isGameInProgress
    }

    override fun getGameField(): MutableList<Card> {
        return cardsList
    }

    private fun matchCards(firstCard: Card?, secondCard: Card?): Boolean {
        val result = firstCard?.getImageId() == secondCard?.getImageId()
        if (result) {
            cardsList[cardsList.indexOf(firstCard)].markMatched()
            cardsList[cardsList.indexOf(secondCard)].markMatched()
        }
        return result
    }

    override fun allCardsMatched(): Boolean {
        return cardsList.filter { it.isMatched }.count() == cardsList.size
    }

    override fun start() {
        isGameInProgress = true

        timer = object : CountDownTimer(GAME_TIME_SEC * 1000, 1000) {
            override fun onFinish() {
                gameEventListener?.updateTimer(0)
                gameEventListener?.gameOver(GameEndStatus.GAME_END_STATUS_OVER,score)
            }

            override fun onTick(millisUntilFinished: Long) {
                gameEventListener?.updateTimer(millisUntilFinished / 1000)
                gameEventListener?.updateScore(score)
            }
        }.start()
    }

    override fun stop() {
        timer?.cancel()
        cardsList = mutableListOf()
        isGameInProgress = false
    }



    override fun cardClick(card: Card): Observable<CardClickResult> {
        Timber.d("GameManager cardClick")
        cardClickResult.cardStatus = CardStatus.CARD_STATUS_NOTHING
        cardsList[cardsList.indexOf(card)].isTopFace = cardsList[cardsList.indexOf(card)].isTopFace.not()

        //клик на первую карту из пары
        if (!isFirstCardFlipped) {
            fCard = card


            cardClickResult.apply {
                this.firstCard = fCard
                this.cardStatus = CardStatus.CARD_STATUS_WAITING_FOR_MATCH
            }
            isFirstCardFlipped = true
            return Observable.just(cardClickResult)
        }

        //второй клик на карту из пары, но это клик на первую уже повернутую карту
        //то ее нужно повернуть обратно
        if (card == fCard)
        {
            cardClickResult.apply {
                this.cardStatus = CardStatus.CARD_STATUS_FIRST_CARD_BACK
            }
            isFirstCardFlipped = false
            return Observable.just(cardClickResult)
        }


        //клик на вторую карту, и это не первая уже открытая карта
        if (isFirstCardFlipped) {
            isFirstCardFlipped = false
            sCard = card
            cardClickResult.apply {
                this.firstCard = fCard
                this.secondCard = sCard
            }
            if (matchCards(firstCard = fCard, secondCard = sCard)) {
                cardClickResult.cardStatus = CardStatus.CARD_STATUS_MATCHED
                score += 2
            }
            else {
                cardClickResult.cardStatus = CardStatus.CARD_STATUS_NOT_MATCHED
                score -=1
                cardsList[cardsList.indexOf(cardClickResult.firstCard)].isTopFace = false
                cardsList[cardsList.indexOf(cardClickResult.secondCard)].isTopFace = false
            }

            gameEventListener?.updateScore(score)

            if (allCardsMatched()) {
                stop()
                gameEventListener?.gameComplete(GameEndStatus.GAME_END_STATUS_COMPLETE, score)
            }

            return Observable.just(cardClickResult)
        }

        return Observable.just(null)
    }


    interface GameEventListener {
        fun updateTimer(timer: Long)

        fun updateScore(score: Int)

        fun gameOver(gameEndStatus : Int, score : Int)

        fun gameComplete(gameEndStatus : Int, score : Int)
    }

    override fun attachGameListener(gameEventListener: GameEventListener) {
        this.gameEventListener = gameEventListener
    }

    override fun detachGameListener() {
        this.gameEventListener = null
    }


}