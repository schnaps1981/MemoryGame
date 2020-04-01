package com.y.memorycardgame.app.di.modules

import com.y.memorycardgame.model.repository.ImagesRepo
import com.y.memorycardgame.model.repository.ImagesRepoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageRepositoryModule {
    @Provides
    @Singleton
    fun providesImagesRepository() : ImagesRepo = ImagesRepoImpl()
}