package ru.sneg.android.bug.game.gameObjects

import ru.sneg.android.bug.game.UI.PlayingFieldUI
import ru.sneg.android.bug.game.UI.TakeUI

class BugsPlacing {

    companion object {
        var chooseHorizontal: Int = 0
    }

    var firstPlayerBugs = Bugs ()



    // определяет варианты в которых возможна только одна из двух ориентация
    fun identOrientaion(partBug: Int, i: Int): String{
        var orientation = ""
       // val tailInRightBorder = listOf<Int>(9,19,29,39,49,59,69,79,89,99)

        if (i - 10*partBug < 0) {
            if (chooseHorizontal == 0) {
                chooseHorizontal = 1
            }
            orientation = "hor"
        }

        if ((i + partBug-1)%10 > 9 || (i + partBug-1)%10 == 0 ) {
            if (chooseHorizontal == 0) {
                chooseHorizontal = 1
            }
            orientation = "vert"
        }
        return orientation
    }


    private fun bugPlacing(chooseHor: Int, i: Int, bugPart: Int, tk: MutableList<TakeUI>, listBug: MutableList<Int>) {
        // *********************вертикальные расстановки****************************
        if (chooseHor == 0) {
            if (fieldNotEmpty(i, bugPart, chooseHor, tk)) {
                chooseHorizontal = 1
            }
            // проерка, что на пути жука statы пустые
            if (!fieldNotEmpty(i, bugPart, chooseHor, tk)) {
                for (s in 0 until bugPart) {
                    tk[i - 10 * s].state = 1 // сам жук
                    listBug.add(i - 10 * s)
                }
            }
        }
        //*********************горизонтальные расстановки****************************
        if (chooseHor == 1) {
            if (fieldNotEmpty(i, bugPart, chooseHor, tk)) {
                chooseHorizontal = 2
            }

            // проерка, что на пути жука statы пустые
            if (!fieldNotEmpty(i, bugPart, chooseHor, tk)) {
                for (s in 0 until bugPart) {
                    tk[i + s].state = 1 // сам жук
                    listBug.add(i + s)
                }
            }
        }
    }

    private fun fieldNotEmpty(i: Int, bugPart: Int, chooseHor: Int, tk: MutableList<TakeUI>): Boolean {
        var notEmpty: Boolean = false

        if (chooseHor == 0 ) {
            for (s in 0 until bugPart)
                if (tk[i - 10 * s].state != 0)
                    notEmpty = true
        }
        if (chooseHor == 1) {
            for (s in 0 until bugPart)
                if (tk[i + s].state != 0)
                    notEmpty = true
        }
        if (chooseHor == 1 && (tk[i].state == 1)) {
            notEmpty = false
        }
        return notEmpty
    }



}