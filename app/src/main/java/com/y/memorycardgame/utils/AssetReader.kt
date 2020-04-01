package com.y.memorycardgame.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import timber.log.Timber
import java.io.IOException
import java.io.InputStream

class AssetsReader {
    companion object {
        fun getDrawableFromAsset(context: Context, filename: String): Drawable {
            var inputStream: InputStream? = null
            var drawable: Drawable = ColorDrawable(Color.GRAY)
            try {
                inputStream = context.assets.open(filename)
                drawable = Drawable.createFromStream(inputStream, null)
            } catch (e: IOException) {
                Timber.e(e)
            } finally {
                inputStream?.close()
            }
            return drawable
        }
    }
}