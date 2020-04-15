package ru.sneg.android.bug.game.engine

data class GamePlayer(
    val userId: Int,
    val userLogin: String,
    val action: Boolean,
    val actionType: Int,
    val winCounter: Int,
    val isOnline: Boolean,
    val isReady: Boolean
    )