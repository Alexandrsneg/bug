package ru.sneg.android.bug.game.UI

import android.graphics.Canvas
import ru.sneg.android.bug.game.gameObjects.Bugs

interface IElementUI {

    fun renderGameField(canvas: Canvas, bug: Bugs )

    fun renderWithoutBugsParts(canvas: Canvas, bug: Bugs )

}