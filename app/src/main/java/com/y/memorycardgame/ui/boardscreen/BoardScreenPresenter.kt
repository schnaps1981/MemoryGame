package com.y.memorycardgame.ui.boardscreen

import com.y.memorycardgame.app.App
import com.y.memorycardgame.model.gamemanager.GameManager
import com.y.memorycardgame.model.data.Card
import com.y.memorycardgame.model.data.CardStatus
import com.y.memorycardgame.model.gamemanager.GameManagerImpl
import com.y.memorycardgame.model.repository.SettingsRepository
import com.y.memorycardgame.ui.navigation.Screens
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class BoardScreenPresenter : MvpPresenter<BoardScreenView>(), GameManagerImpl.GameEventListener {
    @Inject
    lateinit var gameManager: GameManager

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var settings: SettingsRepository

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Timber.d("BoardScreenPresenter onFirstViewAttach")
        App.instance.inject(this)
    }

    override fun attachView(view: BoardScreenView?) {
        super.attachView(view)
        Timber.d("BoardScreenPresenter attachView")
        viewState.initRecycler(settings.getSettings().span_num)

        gameManager.attachGameListener(this)

        if (gameManager.isInProgress()) {
            viewState.initBoard(gameManager.getGameField())
        } else {
            viewState.initBoard(gameManager.gameInit())
            gameManager.start()
        }

    }

    fun cardClick(card: Card) {
        Timber.d("BoardScreenPresenter cardClick")
        if (card.isMatched) return else viewState.flipCard(card)

        compositeDisposable.add(
            gameManager.cardClick(card)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Timber.d("Card click status returned")
                    when (it.cardStatus) {
                        CardStatus.CARD_STATUS_WAITING_FOR_MATCH -> {
                            Timber.d("Card wait for match")
                        }

                        CardStatus.CARD_STATUS_NOT_MATCHED -> {
                            viewState.flipBack(it.firstCard!!, it.secondCard!!)
                        }
                        CardStatus.CARD_STATUS_MATCHED -> {
                            Timber.d("Cards matched")
                        }

                        CardStatus.CARD_STATUS_FIRST_CARD_BACK ->{
                            viewState.flipCard(it.firstCard!!)
                            Timber.d("Flip first card back")
                        }
                    }
                }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("BoardScreenPresenter destroy")
        compositeDisposable.dispose()
        gameManager.detachGameListener()
    }

    override fun isInRestoreState(view: BoardScreenView?): Boolean {
        Timber.d("BoardScreenPresenter isInRestoreState")
        return super.isInRestoreState(view)
    }

    override fun updateTimer(timer: Long) {
        viewState.updateTimer(timer)
    }

    override fun updateScore(score: Int) {
        viewState.updateScore(score)
    }

    override fun gameOver(gameEndStatus: Int, score: Int) {
        Timber.d("GAME OVER")
        gameManager.stop()
        viewState.gameEndDialog(gameEndStatus, score)
    }

    override fun gameComplete(gameEndStatus: Int, score: Int) {
        Timber.d("GAME COMPLETE")
        gameManager.stop()
        viewState.gameEndDialog(gameEndStatus, score)
    }

    override fun destroyView(view: BoardScreenView?) {
        super.destroyView(view)
        Timber.d("BoardScreenPresenter destroyView")

    }

    fun returnMainScreen() {
        router.replaceScreen(Screens.StartScreenNav())

    }
}