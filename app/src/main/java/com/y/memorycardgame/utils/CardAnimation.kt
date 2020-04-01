package com.y.memorycardgame.utils

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.view.View
import android.widget.ImageView
import com.y.memorycardgame.R
import com.y.memorycardgame.model.data.Card

class CardAnimation(
    private var ctx: Context,
    private var card: Card,
    private var view: View,
    private var faceImageView: ImageView,
    private var backImageView: ImageView
) {
    private var leftIn: Animator = AnimatorInflater.loadAnimator(ctx, R.animator.card_flip_left_in)
    private var leftOut: Animator = AnimatorInflater.loadAnimator(ctx, R.animator.card_flip_left_out)
    private var rightIn:  Animator = AnimatorInflater.loadAnimator(ctx, R.animator.card_flip_right_in)
    private var rightOut: Animator = AnimatorInflater.loadAnimator(ctx, R.animator.card_flip_right_out)

    init {

        leftIn.setTarget(faceImageView)
        leftOut.setTarget(backImageView)
        rightIn.setTarget(backImageView)
        rightOut.setTarget(faceImageView)
    }

    fun flip() {
        if (faceImageView.drawable == null) {
            return
        }
        leftIn.end()
        leftOut.end()
        rightIn.end()
        rightOut.end()
        if (faceImageView.alpha == 0f) {
            leftIn.start()
            leftOut.start()
        } else if (faceImageView.alpha == 1f){
            rightIn.start()
            rightOut.start()
        }
    }

    class Builder {
        private lateinit var card: Card
        private lateinit var view: View
        private lateinit var faceImageView: ImageView
        private lateinit var backImageView: ImageView
        private lateinit var ctx:Context

        fun context(context: Context) : Builder
        {
            this.ctx = context
            return this
        }

        fun card(card: Card): Builder {
            this.card = card
            return this
        }

        fun view(view: View): Builder {
            this.view = view
            return this
        }

        fun faceImageView(imageView: ImageView): Builder {
            this.faceImageView = imageView
            return this
        }

        fun backImageView(imageView: ImageView): Builder {
            this.backImageView = imageView
            return this
        }

        fun build(): CardAnimation {
            return CardAnimation(
                ctx = ctx,
                card = card,
                view = view,
                faceImageView = faceImageView,
                backImageView = backImageView
            )
        }
    }


}