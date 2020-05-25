package ru.sneg.android.bug.eac.view

import ru.sneg.android.bug.eac.players.APlayer

class FieldView {

    companion object {
        const val PIXELS_X_CELL = 10
        const val PIXELS_Y_CELL = 10
    }

    var player: APlayer? = null
        set(value) {
            field = value
            render()
        }

    fun onTouch(x: Int, y: Int) {

        player?.let {
            val cell = pixels2cell(x, y)
            it.onTouch(cell.first, cell.second)
        }
    }

    fun render() {
        // Тут рисовать состояние игры для текущего игрока player
    }

    private fun pixels2cell(x: Int, y: Int): Pair<Int, Int> {
        val xCell = x / PIXELS_X_CELL
        val yCell = y / PIXELS_X_CELL

        return Pair(xCell, yCell)
    }
}