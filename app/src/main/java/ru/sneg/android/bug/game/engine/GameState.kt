package ru.sneg.android.bug.game.engine

import ru.sneg.android.bug.game.engine.players.LocalPlayer

//запмисывает текущее состояние игры на сервер
data class GameState(
        val status : Int,
        val game: List<Int>, //описывает игровое поле, в каком оно сотоянии
        val players: List<LocalPlayer>, // список играющих сейчас игроков
        val winner: LocalPlayer?
    )

