package com.y.memorycardgame.model.repository

import com.y.memorycardgame.model.data.CardImage

interface ImagesRepo {
    fun getRandomImage() : CardImage
}