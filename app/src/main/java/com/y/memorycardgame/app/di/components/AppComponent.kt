package com.y.memorycardgame.app.di.components

import com.y.memorycardgame.app.di.modules.*
import com.y.memorycardgame.model.gamemanager.GameManagerImpl
import com.y.memorycardgame.model.repository.ImagesRepoImpl
import com.y.memorycardgame.model.repository.SettingsRepositoryImpl
import com.y.memorycardgame.ui.activity.MainActivity
import com.y.memorycardgame.ui.activity.MainPresenter
import com.y.memorycardgame.ui.boardscreen.BoardScreen
import com.y.memorycardgame.ui.boardscreen.BoardScreenPresenter
import com.y.memorycardgame.ui.boardscreen.viewholder.BoardScreenViewHolder
import com.y.memorycardgame.ui.startscreen.StartScreen
import com.y.memorycardgame.ui.startscreen.StartScreenPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        AssetsModule::class,
        NavigationModule::class,
        SharedPreferencesModule::class,
        SettingsRepositoryModule::class,
        GameManagerModule::class,
        ImageRepositoryModule::class,
        CompositeDisposableModule::class
    ]
)

interface AppComponent {
    fun inject(imagesRepo: ImagesRepoImpl)
    fun inject(settingsRepo: SettingsRepositoryImpl)

    fun inject(activity: MainActivity)

    fun inject(screen: BoardScreen)
    fun inject(screen: StartScreen)


    fun inject(presenter: MainPresenter)
    fun inject(presenter: StartScreenPresenter)
    fun inject(presenter: BoardScreenPresenter)

    fun inject(gameManager: GameManagerImpl)

    fun inject(viewHolder: BoardScreenViewHolder)

}