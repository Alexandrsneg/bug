package ru.sneg.android.bug.game.UI

import android.graphics.Canvas

interface IElementUI {

    fun render(canvas: Canvas)
    fun renderSecond(canvas: Canvas)

    fun renderWithoutBugsParts(canvas: Canvas)
    fun renderWithoutBugsPartsSecond(canvas: Canvas)
}