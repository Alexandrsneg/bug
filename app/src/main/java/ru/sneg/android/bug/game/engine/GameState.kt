package ru.sneg.android.bug.game.engine

//запмисывает текущее состояние игры на сервер
data class GameState(
    val status : Int,
    val game: List<Int>, //описывает игровое поле, в каком оно сотоянии
    val players: List<GamePlayer>, // список играющих сейчас игроков
    val winner: GamePlayer?
    )

