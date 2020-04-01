package com.y.memorycardgame.ui.boardscreen.viewholder

import android.graphics.drawable.Drawable
import android.os.DeadObjectException
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.y.memorycardgame.R
import com.y.memorycardgame.app.App
import com.y.memorycardgame.model.data.Card
import com.y.memorycardgame.model.repository.SettingsRepository
import com.y.memorycardgame.utils.CardAnimation
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.card_item.view.*
import ru.neura.iotable.BaseViewHolder
import timber.log.Timber
import javax.inject.Inject

class BoardScreenViewHolder(itemView: View, private val parent: ViewGroup) :
    BaseViewHolder<Card>(itemView) {
    @Inject
    lateinit var settings: SettingsRepository
    private lateinit var flipanimation: CardAnimation

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    private var cardsNumber: Int
    private var spanNumber: Int
    private var lastClickTime: Long = 0

    init {
        App.instance.inject(this)
        spanNumber = settings.getSettings().span_num
        cardsNumber = spanNumber * spanNumber

    }

    override fun bind(model: Card) {
        Timber.d("Bind View")

        Glide
            .with(itemView.context)
            .load(model.image.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(itemView.iv_card_face)

        Glide
            .with(itemView.context)
            .load(R.drawable.ic_question)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(itemView.iv_card_back)

        calculateItemSize()

        if (model.isTopFace) {
            itemView.iv_card_back.alpha = 0.0f
            itemView.iv_card_face.alpha = 1.0f
        }
    }

    private fun calculateItemSize() {
        val rowsNum = cardsNumber / spanNumber
        itemView.layoutParams =
            RelativeLayout.LayoutParams(parent.width / spanNumber, parent.height / rowsNum)
    }

    fun animateClick(model: Card, view: View) {
        flipanimation = CardAnimation.Builder()
            .context(itemView.context)
            .card(model)
            .view(view)
            .faceImageView(view.iv_card_face)
            .backImageView(view.iv_card_back)
            .build()

        flipanimation.flip()
    }
}