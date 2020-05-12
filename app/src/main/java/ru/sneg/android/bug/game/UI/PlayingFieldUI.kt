package ru.sneg.android.bug.game.UI

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.gameObjects.Bugs
import ru.sneg.android.bug.game.gameObjects.BugsPlacing
import ru.sneg.android.bug.game.gameObjects.BugsPlacing.Companion.chooseHorizontal
import ru.sneg.android.bug.game.gameObjects.Const
import ru.sneg.android.bug.game.gameViews.GameBugPlacementSecondPlayerView
import ru.sneg.android.bug.game.gameViews.GameBugPlacementView

//отображение игрового поля
class PlayingFieldUI: IElementUI {

    private val bgPaint = Paint().apply { color = Color.DKGRAY }

    var width: Int = 0
    var height: Int = 0

    init {
        //заполнение игрового поля пустыми клетками (STATE_UNDEFINED)
        for (index in 0..99)
            GameBugPlacementView.firstPlayerBugs.takes.add(TakeUI(index).apply {
                state = 0
            })
        for (index in 0..99)
            GameBugPlacementSecondPlayerView.secondPlayerBugs.takes.add(TakeUI(index).apply {
                state = 0
            })
    }

    var bugsPlacing = BugsPlacing()

//*****************расстановка жуков****************************************************
    //обработчик нажатия на клетку поля
    //логика возможности раастановки жуков по полю, установка в зависимости от расположения(офлайн)
    fun onClickFieldBugPlacing(x: Float, y: Float, bug: Bugs) {

        val x: Int = (x / (width / 10)).toInt()
        val y: Int = (y / (height / 10)).toInt()

        val i: Int = y * 10 + x

        if (bug.fourPartBug > 0) {
                if (i in 0..99) {
                    bugsPlacing.horVertOrRemovePlacing(4,i, chooseHorizontal,bug, bug.listBugFour)
                }
            } else if (bug.fourPartBug == 0 && bug.threePartBug > 0) {
            if (i in 0..99) {
                //горизонтальная установка и удаление на первых двух строках
                if ((i in 0..7 || i in 10..17)) {
                    if (chooseHorizontal == 0) {
                        chooseHorizontal = 1
                        when (bug.threePartBug) {
                            2 -> { bugPlacing(chooseHorizontal, i, 3, bug, bug.listBugThreeFirst) }
                            1 -> { bugPlacing(chooseHorizontal, i, 3, bug, bug.listBugThreeSecond) }
                        }
                    }
                }
                    //вертикальная установка по всему полю кроме первых двух строк
                    if (chooseHorizontal == 0 && i in 20..99) {
                        when (bug.threePartBug) {
                            2 -> { bugPlacing(chooseHorizontal, i, 3, bug, bug.listBugThreeFirst)}
                            1 -> { bugPlacing(chooseHorizontal, i, 3, bug, bug.listBugThreeSecond)}
                            }
                        }

                    //горизонтальная расстановка кроме последних двух столбцов
                    if (chooseHorizontal == 1 && (i in 20..27 ||i in 30..37 || i in 40..47 || i in 50..57 || i in 60..67 || i in 70..77 || i in 80..87 || i in 90..97)) {
                        //чтобы не перетирались значения уже установленных жуков
                        when (bug.threePartBug) {
                            2 -> {
                                    if (bug.takes[i].state == 1)
                                    removeBug(i, 3, chooseHorizontal, bug, bug.listBugThreeFirst)
                                    bugPlacing(chooseHorizontal, i, 3, bug, bug.listBugThreeFirst)
                            }
                            1 -> {
                                    if (bug.takes[i].state == 1)
                                    removeBug(i, 3, chooseHorizontal, bug, bug.listBugThreeSecond)
                                    bugPlacing(chooseHorizontal, i, 3, bug, bug.listBugThreeSecond)
                            }
                        }

                    }
                    // удаление вертикальных кораблей в последних двух столбах
                    if (!( i in 0..7 || i in 10..17 || i in 20..27 || i in 30..37 || i in 40..47 || i in 50..57 || i in 60..67 || i in 70..77 || i in 80..87 || i in 90..97) && (chooseHorizontal == 1)) {
                        //чтобы не перетирались значения уже установленных жуков
                        if (bug.takes[i].state == 1)
                            when (bug.threePartBug) {
                                2 -> {
                                    if (bug.takes[i].state == 1)
                                    removeBug(i, 3, chooseHorizontal, bug, bug.listBugThreeFirst )
                                    bugPlacing(chooseHorizontal, i, 3, bug, bug.listBugThreeFirst)
                                }
                                1 -> {
                                    if (bug.takes[i].state == 1)
                                    removeBug(i, 3, chooseHorizontal, bug, bug.listBugThreeSecond )
                                    bugPlacing(chooseHorizontal, i, 3, bug, bug.listBugThreeSecond)
                                }
                            }
                        chooseHorizontal = 2
                    }
                    //удаление горизонтального жука, завершение цикла установки корабля
                    if (chooseHorizontal == 2 ) {
                        //чтобы не перетирались значения уже установленных жуков
                        if (bug.takes[i].state == 1) {
                            when (bug.threePartBug) {
                                2 -> {
                                    if (bug.takes[i].state == 1)
                                    removeBug(i, 3, chooseHorizontal, bug, bug.listBugThreeFirst )
                                    bugPlacing(chooseHorizontal, i, 3, bug, bug.listBugThreeFirst)
                                }
                                1 -> {
                                    if (bug.takes[i].state == 1)
                                    removeBug(i, 3, chooseHorizontal, bug, bug.listBugThreeSecond )
                                    bugPlacing(chooseHorizontal, i, 3, bug, bug.listBugThreeSecond)
                                }
                            }
                        }
                    }
                    chooseHorizontal++
                    if (chooseHorizontal == 3) {
                        chooseHorizontal = 0
                    }
                }
            } else if (bug.threePartBug == 0 && bug.twoPartBug > 0) {
                if (i in 0..99) {
                    //горизонтальная установка и удаление на первых двух строках
                    if (i in 0..8) {
                        if (chooseHorizontal == 0) {
                            chooseHorizontal = 1
                            when (bug.twoPartBug) {
                                3 -> {
                                    bugPlacing(chooseHorizontal, i, 2, bug, bug.listBugTwoFirst)
                                }
                                2 -> {
                                    bugPlacing(chooseHorizontal, i, 2, bug, bug.listBugTwoSecond)
                                }
                                1 -> {
                                    bugPlacing(chooseHorizontal, i, 2, bug, bug.listBugTwoThird)
                                }
                            }
                        }
                    }
                    //вертикальная установка по всему полю кроме первых двух строк
                    if (chooseHorizontal == 0 && i in 10..99) {
                        when (bug.twoPartBug) {
                            3 -> {
                                bugPlacing(chooseHorizontal, i, 2, bug, bug.listBugTwoFirst)
                            }
                            2 -> {
                                bugPlacing(chooseHorizontal, i, 2, bug, bug.listBugTwoSecond)
                            }
                            1 -> {
                                bugPlacing(chooseHorizontal, i, 2, bug, bug.listBugTwoThird)
                            }
                        }
                    }
                    //горизонтальная расстановка кроме последних двух столбцов
                    if (chooseHorizontal == 1 && (i in 10..18 ||i in 20..28 || i in 30..38 || i in 40..48 || i in 50..58 || i in 60..68 || i in 70..78 || i in 80..88 || i in 90..98)) {
                        when (bug.twoPartBug) {
                            3 -> {
                                if (bug.takes[i].state == 1)
                                removeBug(i, 2, chooseHorizontal, bug, bug.listBugTwoFirst )
                                bugPlacing(chooseHorizontal, i, 2, bug, bug.listBugTwoFirst)
                            }
                            2 -> {
                                if (bug.takes[i].state == 1)
                                removeBug(i, 2, chooseHorizontal, bug, bug.listBugTwoSecond )
                                bugPlacing(chooseHorizontal, i, 2, bug, bug.listBugTwoSecond)
                            }
                            1 -> {
                                if (bug.takes[i].state == 1)
                                removeBug(i, 2, chooseHorizontal, bug, bug.listBugTwoThird)
                                bugPlacing(chooseHorizontal, i, 2, bug, bug.listBugTwoThird)
                            }
                        }
                    }
                    // удаление вертикальных кораблей в последних двух столбах
                    if (!( i in 0..8 || i in 10..18 || i in 20..28 || i in 30..38 || i in 40..48 || i in 50..58 || i in 60..68 || i in 70..78 || i in 80..88 || i in 90..98) && (chooseHorizontal == 1)) {
                            when (bug.twoPartBug) {
                                3 -> {
                                    if (bug.takes[i].state == 1)
                                    removeBug(i, 2, chooseHorizontal, bug, bug.listBugTwoFirst )
                                    bugPlacing(chooseHorizontal, i, 2, bug, bug.listBugTwoFirst)
                                }
                                2 -> {
                                    if (bug.takes[i].state == 1)
                                    removeBug(i, 2, chooseHorizontal, bug, bug.listBugTwoSecond )
                                    bugPlacing(chooseHorizontal, i, 2, bug, bug.listBugTwoSecond)
                                }
                                1 -> {
                                    if (bug.takes[i].state == 1)
                                    removeBug(i, 2, chooseHorizontal, bug, bug.listBugTwoThird)
                                    bugPlacing(chooseHorizontal, i, 2, bug, bug.listBugTwoThird)
                                }
                            }
                        chooseHorizontal = 2
                    }
                    //удаление горизонтального жука, завершение цикла установки корабля
                    if (chooseHorizontal == 2 ) {
                            when (bug.twoPartBug) {
                                3 -> {
                                    if (bug.takes[i].state == 1)
                                    removeBug(i, 2, chooseHorizontal, bug, bug.listBugTwoFirst )
                                    bugPlacing(chooseHorizontal, i, 2, bug, bug.listBugTwoFirst)
                                }
                                2 -> {
                                    if (bug.takes[i].state == 1)
                                    removeBug(i, 2, chooseHorizontal, bug, bug.listBugTwoSecond )
                                    bugPlacing(chooseHorizontal, i, 2, bug, bug.listBugTwoSecond)
                                }
                                1 -> {
                                    if (bug.takes[i].state == 1)
                                    removeBug(i, 2, chooseHorizontal, bug, bug.listBugTwoThird)
                                    bugPlacing(chooseHorizontal, i, 2, bug, bug.listBugTwoThird)
                                }
                            }
                    }
                    chooseHorizontal++
                    if (chooseHorizontal == 3) {
                        chooseHorizontal = 0
                    }
                }
            } else if (bug.twoPartBug == 0 && bug.onePartBug > 0) {
            when (bug.onePartBug) {
                4 -> {
                    bugPlacing(chooseHorizontal, i, 1, bug, bug.listBugOneFirst)
                }
                3 -> {
                    bugPlacing(chooseHorizontal, i, 1, bug, bug.listBugOneSecond)
                }
                2 -> {
                    bugPlacing(chooseHorizontal, i, 1, bug, bug.listBugOneThird)
                }
                1 -> {
                    bugPlacing(chooseHorizontal, i, 1, bug, bug.listBugOneFourth)
                }
            }
                if (chooseHorizontal == 1) {
                    when (bug.onePartBug) {
                        4 -> {
                            if (bug.takes[i].state == 1)
                            removeBug(i, 1, chooseHorizontal, bug, bug.listBugOneFirst)
                        }
                        3 -> {if (bug.takes[i].state == 1)
                            removeBug(i, 1, chooseHorizontal, bug, bug.listBugOneSecond)
                        }
                        2 -> {if (bug.takes[i].state == 1)
                            removeBug(i, 1, chooseHorizontal, bug, bug.listBugOneThird)
                        }
                        1 -> {if (bug.takes[i].state == 1)
                            removeBug(i, 1, chooseHorizontal, bug, bug.listBugOneFourth)
                        }
                    }
                    chooseHorizontal = 2
                }
                chooseHorizontal++
                if (chooseHorizontal == 3) {
                    chooseHorizontal = 0
                }
        }
    }

//**************************конец расстановки жуков*****************************************

//**************************выстрелы по полям***********************************************
    //отрисовки нажатий на игоровое поле первого игрока (офлайн)
    fun onClickGameField(x: Float, y: Float, bug: Bugs) {
        val x: Int = (x / (width / 10)).toInt()
        val y: Int = (y / (height / 10)).toInt()
        val i: Int = y * 10 + x

        if (bug.takes[y * 10 + x].state == 1){   //bug_part
            bug.takes[y * 10 + x].state = 3      //explode

            if (killCheck(identBug(i, bug),bug)){ // если все элементы жука подбиты
            bug.killedBugSurrounding() // обводка клеток вокруг всех убитых жуков
            }
        }

    if (bug.takes[y * 10 + x].state == 0 || bug.takes[y * 10 + x].state == 4){ //undefined
        bug.takes[y * 10 + x].state = 2  //miss
            // смена хода, блокировка первого поля, разблокировка второго поля

        }
    }

//**************************выстрелы по полям***********************************************

        //отрисовка поля расстановки первого игрока (офлайн)
        override fun renderGameField(canvas: Canvas, bug: Bugs) {

            canvas.drawRect(Rect(0, 0, width, height), bgPaint)

            var row = 0
            var col = 0
            val itemWidth = width / 10
            val itemHeight = height / 10

            for (take in bug.takes) {

                take.x = col * itemWidth
                take.y = row * itemHeight

                take.width = itemWidth
                take.height = itemHeight

                take.renderGameField(canvas, bug)

                if (++col == 10) {
                    col = 0
                    if (++row == 10)
                        return
                }
            }
        }

    override fun renderWithoutBugsParts(canvas: Canvas, bug: Bugs) {

        canvas.drawRect(Rect(0, 0, width, height), bgPaint)

        var row = 0
        var col = 0
        val itemWidth = width / 10
        val itemHeight = height / 10

        for (take in bug.takes) {

            take.x = col * itemWidth
            take.y = row * itemHeight

            take.width = itemWidth
            take.height = itemHeight

            take.renderWithoutBugsParts(canvas, bug)

            if (++col == 10) {
                col = 0
                if (++row == 10)
                    return
            }
        }
    }

    //определяет по тычку на какую клетку попали
        fun onClick(x: Float, y: Float, bug: Bugs): TakeUI? {
            //лямбда проверяющая входит ли нажатая область в опрееленные в Тэйках клетки
            return bug.takes.firstOrNull { it.x < x && it.x + it.width >= x && it.y < y && it.y + it.height >= y }
        }

        //отображение данных присланных с сервера
        fun setGameState(state: GameState, bug: Bugs) {

            val game = state.game.toTypedArray()
            for (i in 0 until 99)
                bug.takes[i].state = when (game[i]) {
                    Const.SELECT_TYPE_SHIP_PART -> TakeUI.STATE_BUG_PART
                    Const.SELECT_TYPE_MISS -> TakeUI.STATE_MISS
                    Const.SELECT_TYPE_EXPLODE -> TakeUI.STATE_EXPLODE
                    else -> TakeUI.STATE_UNDEFINED
                }

            if (state.winner != null)
                println("WIN!")
        }


    //логика возможност выставления жука в зависимости от занятого перед ним пространства
    private fun bugPlacing(chooseHor: Int, i: Int, bugPart: Int, bug: Bugs, listBug: MutableList<Int>) {
        // *********************вертикальные расстановки****************************
        if (chooseHor == 0) {
            if (fieldNotEmpty(i, bugPart, chooseHor, bug)) {
                chooseHorizontal = 1
            }
            // проерка, что на пути жука statы пустые
            if (!fieldNotEmpty(i, bugPart, chooseHor, bug)) {
                for (s in 0 until bugPart) {
                    bug.takes[i - 10 * s].state = 1 // сам жук
                    listBug.add(i - 10 * s)
                }
            }
        }
        //*********************горизонтальные расстановки****************************
        if (chooseHor == 1) {
            if (fieldNotEmpty(i, bugPart, chooseHor, bug)) {
                chooseHorizontal = 2
            }

            // проерка, что на пути жука statы пустые
            if (!fieldNotEmpty(i, bugPart, chooseHor, bug)) {
                for (s in 0 until bugPart) {
                    bug.takes[i + s].state = 1 // сам жук
                    listBug.add(i + s)
                }
            }
        }
    }

    private fun fieldNotEmpty(i: Int, bugPart: Int, chooseHor: Int, bug: Bugs): Boolean {
            var notEmpty: Boolean = false

            if (chooseHor == 0 ) {
                for (s in 0 until bugPart)
                    if (bug.takes[i - 10 * s].state != 0)
                        notEmpty = true
            }
            if (chooseHor == 1) {
                for (s in 0 until bugPart)
                    if (bug.takes[i + s].state != 0)
                        notEmpty = true
            }
            if (chooseHor == 1 && (bug.takes[i].state == 1)) {
                notEmpty = false
            }
        return notEmpty
        }

    //удаление жука в зависимости от расположения, удаление элементов жука из списка
    private  fun removeBug (i: Int, bugPart: Int, chooseHor: Int, bug: Bugs, listBug: MutableList<Int>) {
        if (chooseHor == 1) {
            for (s in 0 until bugPart)
                try {
                    bug.takes[i - 10 * s].state = 0 // сам жук
                    listBug.remove(i - 10 * s)
                }
                catch (e : IndexOutOfBoundsException){
                    println(e)
                }
        }
        if (chooseHor == 2){
            for (s in 0 until bugPart)
                    try {
                        bug.takes[i + s].state = 0 // сам жук
                        listBug.remove(i + s)
                    }
                    catch (e : IndexOutOfBoundsException){
                        println(e)
                    }
        }
    }

    //определение скольки-палубный жук задет, для того чтобы узнать когда он будет убит
    private fun identBug(i: Int, bug: Bugs): MutableList<Int>{
        lateinit var listBug: MutableList<Int>
        // 4x-bug
       if (bug.listBugFour.contains(i)){
            listBug = bug.listBugFour
       }
        // 3x-bug
        if (bug.listBugThreeFirst.contains(i)){
            listBug = bug.listBugThreeFirst
        }
        if (bug.listBugThreeSecond.contains(i)){
            listBug = bug.listBugThreeSecond
        }
        //2x-bug
        if (bug.listBugTwoFirst.contains(i)){
            listBug = bug.listBugTwoFirst
        }
        if (bug.listBugTwoSecond.contains(i)){
            listBug = bug.listBugTwoSecond
        }
        if (bug.listBugTwoThird.contains(i)){
            listBug = bug.listBugTwoThird
        }

        //1x-bug
        if (bug.listBugOneFirst.contains(i)){
            listBug = bug.listBugOneFirst
        }
        if (bug.listBugOneSecond.contains(i)){
            listBug = bug.listBugOneSecond
        }
        if (bug.listBugOneThird.contains(i)){
            listBug = bug.listBugOneThird
        }
        if (bug.listBugOneFourth.contains(i)){
            listBug = bug.listBugOneFourth
        }
        return listBug
    }

    private fun killCheck(listBug : MutableList<Int>, bug: Bugs): Boolean{
        var killed : Boolean = false
        var sum : Int = 0

        for (i in listBug){
            if (bug.takes[i].state == 3) sum ++
        }
        if (sum - listBug.size == 0) killed = true

        return killed
    }
}

