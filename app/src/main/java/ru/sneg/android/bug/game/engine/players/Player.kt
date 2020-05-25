package ru.sneg.android.bug.game.engine.players

import ru.sneg.android.bug.game.gameObjects.Bugs

class Player(bug:Bugs) {
    var bug: Bugs = bug
    var win: Boolean = true
    var move: Boolean = true
    var goodShoot: Boolean = true
}