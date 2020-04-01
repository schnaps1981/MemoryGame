package com.y.memorycardgame.model.data

class Card(val image: CardImage) {
    var isMatched : Boolean = false
    var isTopFace : Boolean = false

    fun getImageId() : String
    {
        return image.imageId
    }

    fun markMatched()
    {
        this.isMatched = true
    }
}