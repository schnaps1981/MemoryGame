package com.y.memorycardgame.ui.boardscreen

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.y.memorycardgame.R
import com.y.memorycardgame.app.App
import com.y.memorycardgame.model.data.Card
import com.y.memorycardgame.model.data.GameEndStatus
import com.y.memorycardgame.ui.boardscreen.adapter.BoardScreenAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main_screen.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import ru.neura.iotable.BaseAdapterCallback
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class BoardScreen : MvpAppCompatFragment(R.layout.fragment_main_screen), BoardScreenView, BaseAdapterCallback<Card> {

    @InjectPresenter
    lateinit var presenter: BoardScreenPresenter

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var router: Router

    val adapter: BoardScreenAdapter = BoardScreenAdapter()
    var lastClickTime = 0L

//    override fun onCreateView(
//        inflater: LayoutInflater?,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater?.inflate(, container, false);
//    }

    companion object {
        fun getInstance(): BoardScreen? = BoardScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION")
        super.onViewCreated(view, savedInstanceState)
        App.instance.inject(this)

    }

    override fun onItemClick(model: Card, view: View) {

        if (SystemClock.elapsedRealtime() - lastClickTime < view.context.resources.getInteger(R.integer.memory_card_flip_time_full).toLong()) return

        Timber.d("BoardScreen onItemClick")
        lastClickTime = SystemClock.elapsedRealtime()
        presenter.cardClick(model)
    }

    override fun flipCard(card: Card) {
        Timber.d("BoardScreen Animate card FLIP")
        adapter.animateCard(card = card)
    }

    override fun flipBack(firstCard: Card, secondCard: Card) {
        val flipBack = Runnable {
            adapter.animateCard(card = firstCard)
            adapter.animateCard(card = secondCard)
        }
        Timber.d("BoardScreen Animate card FLIP BACK")
        Handler().postDelayed(flipBack, 500L)

    }

    override fun updateTimer(timer: Long) {
        tv_game_timer.text = activity?.getString(R.string.timer, timer.toString())
    }

    override fun updateScore(score: Int) {
        tv_game_score.text = activity?.getString(R.string.score, score.toString())

    }

    override fun gameEndDialog(gameEndStatus: Int, score: Int) {
        MaterialAlertDialogBuilder(this.activity).apply {
            when (gameEndStatus) {
                GameEndStatus.GAME_END_STATUS_COMPLETE -> {
                    setTitle("Game complete")
                    setMessage("Congratulations, You WIN!!!!")

                }
                GameEndStatus.GAME_END_STATUS_OVER -> {
                    setTitle("Game over")
                    setMessage("Sorry, you lose :(")
                }
            }
            setCancelable(false)
            setPositiveButton("OK") { _, _ -> presenter.returnMainScreen() }
        }.show()
    }

    override fun initBoard(cards: MutableList<Card>) {
        adapter.updateItems(cards)
    }

    override fun initRecycler(span: Int) {
        rv_field.layoutManager = GridLayoutManager(activity?.applicationContext, span)
        rv_field.adapter = adapter
        adapter.attachCallback(this)
    }

}