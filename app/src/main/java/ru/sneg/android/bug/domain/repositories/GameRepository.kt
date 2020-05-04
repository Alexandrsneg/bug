package ru.sneg.android.bug.domain.repositories

import javax.inject.Inject

class GameRepository {
    @Inject
    constructor()
    fun createGame(subscriber: (String) -> Unit, nameGame: String) {
        subscriber.invoke("$nameGame")
    }

    fun bugPlacementPlayerShow(){

    }
}