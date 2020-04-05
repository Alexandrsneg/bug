package ru.sneg.android.bug.domain.repositories

import javax.inject.Inject

abstract class AchievesRepository  {
    @Inject
    constructor()

    abstract fun showScore()

    abstract fun showWins()

    abstract fun showLoses()

}