package ru.sneg.android.bug.credentials.profile

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.domain.repositories.AchievesRepository
import javax.inject.Inject

@InjectViewState
class ProfilePresenter : MvpPresenter<IProfileView> {

    @Inject
    lateinit var achievesRepository: AchievesRepository

    @Inject
    constructor()

    fun showScore() {
        achievesRepository.showScore()
    }
    fun showWins() {
        achievesRepository.showWins()
    }
    fun showLoses() {
        achievesRepository.showLoses()
    }
}