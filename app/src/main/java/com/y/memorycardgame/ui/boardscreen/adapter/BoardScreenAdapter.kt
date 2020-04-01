package com.y.memorycardgame.ui.boardscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.y.memorycardgame.R
import com.y.memorycardgame.model.data.Card
import com.y.memorycardgame.ui.boardscreen.viewholder.BoardScreenViewHolder
import ru.neura.iotable.BaseAdapter
import ru.neura.iotable.BaseViewHolder
import timber.log.Timber

class BoardScreenAdapter : BaseAdapter<Card>() {
    private lateinit var boardScreenViewHolder: BoardScreenViewHolder
    private var viewHolders: HashMap<Int, BoardScreenViewHolder> = hashMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Card> {
        Timber.d("Adapter onCreateViewHolder")
        boardScreenViewHolder = BoardScreenViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false), parent
        )
        return this.boardScreenViewHolder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Card>, position: Int) {
        super.onBindViewHolder(holder, position)
        Timber.d("Adapter onBindViewHolder")
        viewHolders[position] = boardScreenViewHolder
    }

    fun animateCard(card: Card) {
        Timber.d("Adapter animateCard")
        val view = viewHolders[super.mDataList.indexOf(card)]
        if (view != null) {
            boardScreenViewHolder.animateClick(card, view.itemView)
        }
    }
}