package ru.sneg.android.bug.game.gameObjects

import kotlinx.android.synthetic.main.fragment_bug_placement_player.*
import ru.sneg.android.bug.game.UI.PlayingFieldUI
import ru.sneg.android.bug.game.UI.TakeUI
import ru.sneg.android.bug.game.gameObjects.BugsPlacing.Companion.chooseHorizontal

class Bugs(
        var fourPartBug: Int = 1,
        var threePartBug: Int = 2,
        var twoPartBug: Int = 3,
        var onePartBug: Int = 4,
        var bugsRemaining: Int = 10
) {
    //списки отдельно для каждого установленного жука
    var listBugFour = mutableListOf<Int>()

    var listBugThreeFirst = mutableListOf<Int>()
    var listBugThreeSecond = mutableListOf<Int>()

    var listBugTwoFirst = mutableListOf<Int>()
    var listBugTwoSecond = mutableListOf<Int>()
    var listBugTwoThird = mutableListOf<Int>()

    var listBugOneFirst = mutableListOf<Int>()
    var listBugOneSecond = mutableListOf<Int>()
    var listBugOneThird = mutableListOf<Int>()
    var listBugOneFourth = mutableListOf<Int>()

    //список возможных выборов пользователя
    var takes = mutableListOf<TakeUI>()


    // удаление всех поставленных жуков и обводок, очистка списков жуков
     fun cleanField() {
        fourPartBug = 1
        threePartBug = 2
        twoPartBug = 3
        onePartBug = 4

        bugsRemaining = 10

        chooseHorizontal = 0
        for (index in 0..99) {
            takes[index].state = 0
        }

        listBugFour.clear()

        listBugThreeFirst.clear()
        listBugThreeSecond.clear()

        listBugTwoFirst.clear()
        listBugTwoSecond.clear()
        listBugTwoThird.clear()

        listBugOneFirst.clear()
        listBugOneSecond.clear()
        listBugOneThird.clear()
        listBugOneFourth.clear()

    }

     fun acceptBugSurrounding() {

        for (i: Int in 0..99) {
            if (takes[i].state == 1) {

                // обводка клетки сверху
                if (i !in 0..9) {
                    if (takes[i - 10].state == 0){
                        try {
                            takes[i - 10].state = 4;
                        } catch (e: ArrayIndexOutOfBoundsException) { println ("Exception") }
                    }
                }
                // обводка клетки снизу
                if(i !in 90..99) {
                    if (takes[i + 10].state == 0) {
                        try {
                            takes[i + 10].state = 4;
                        } catch (e: Exception) { println ("Exception") }
                    }
                }
                // обводка клетки слева
                val left = listOf<Int>(0,10,20,30,40,50,60,70,80,90)
                if (i !in left) {
                    if (takes[i - 1].state == 0 || takes[i - 1].state == 4) {
                        takes[i - 1].state = 4;
                        try {
                            takes[i + 9].state = 4
                        } catch (e: Exception) { println("Exception") }
                        try {
                            takes[i - 11].state = 4;
                        } catch (e: Exception) { println("Exception") }
                    }
                }
                // обводка клетки справа
                val right = listOf<Int>(9,19,29,39,49,59,69,79,89,99)
                if (i !in right) {
                    if (takes[i + 1].state == 0 || takes[i + 1].state == 4) {
                        takes[i + 1].state = 4;
                        try {
                            takes[i + 11].state = 4;
                        } catch (e: Exception) { println("Exception") }
                        try {
                            takes[i - 9].state = 4
                        } catch (e: Exception) { println("Exception") }
                    }
                }

            }
        }
    }

    fun killedBugSurrounding() {

        for (i: Int in 0..99) {
            if (takes[i].state == 3) {

                // обводка клетки сверху
                if (i !in 0..9) {
                    if (takes[i - 10].state == 4){
                        try {
                            takes[i - 10].state = 2
                        } catch (e: ArrayIndexOutOfBoundsException) { println ("Exception") }
                    }
                }
                // обводка клетки снизу
                if(i !in 90..99) {
                    if (takes[i + 10].state == 4) {
                        try {
                            takes[i + 10].state = 2
                        } catch (e: Exception) { println ("Exception") }
                    }
                }
                // обводка клетки слева
                val left = listOf<Int>(0,10,20,30,40,50,60,70,80,90)
                if (i !in left) {
                    if (takes[i - 1].state == 2 || takes[i - 1].state == 4) {
                        takes[i - 1].state = 2
                        try {
                            takes[i + 9].state = 2
                        } catch (e: Exception) { println("Exception") }
                        try {
                            takes[i - 11].state = 2
                        } catch (e: Exception) { println("Exception") }
                    }
                }
                // обводка клетки справа
                val right = listOf<Int>(9,19,29,39,49,59,69,79,89,99)
                if (i !in right) {
                    if (takes[i + 1].state == 2 || takes[i + 1].state == 4) {
                        takes[i + 1].state = 2
                        try {
                            takes[i + 11].state = 2
                        } catch (e: Exception) { println("Exception") }
                        try {
                            takes[i - 9].state = 2
                        } catch (e: Exception) { println("Exception") }
                    }
                }

            }
        }
    }
}