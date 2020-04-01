package com.y.memorycardgame.model.repository

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.y.memorycardgame.app.App
import com.y.memorycardgame.model.data.CardImage
import com.y.memorycardgame.utils.AssetsReader
import javax.inject.Inject
import kotlin.random.Random

class ImagesRepoImpl : ImagesRepo {
    @Inject
    lateinit var context: Context
    @Inject
    lateinit var assets: AssetManager

    init {
        App.instance.inject(this)
    }

    override fun getRandomImage(): CardImage {
        val assetsList = assets.list("cards") ?: arrayOf()

        if (assetsList.isEmpty()) return CardImage(image = ColorDrawable(Color.GRAY), imageId = "")

        val index = assetsList.size.let { Random.nextInt(0, it - 1) }
        val filename = assetsList[index]
        return CardImage(
            image = AssetsReader.getDrawableFromAsset(context, "cards/$filename"),
            imageId = filename
        )

    }
}