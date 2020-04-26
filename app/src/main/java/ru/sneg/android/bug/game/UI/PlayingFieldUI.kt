package ru.sneg.android.bug.game.UI

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_bug_placement_player.*
import ru.sneg.android.bug.credentials.game.bugPlacement.BugPlacementPlayerFragment
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.gameObjects.Const
import java.util.stream.IntStream
import kotlin.random.Random

//отображение игрового поля
class PlayingFieldUI: IElementUI {

    companion object {

        var fourPartBug: Int = 1
        var threePartBug: Int = 2
        var twoPartBug: Int = 3
        var onePartBug: Int = 4

        var bugsRemaining: Int = 10

        var chooseHorizontal: Int = 0

        val takes = mutableListOf<TakeUI>() //список возможных выборов пользователя
        val takesPlayerTwo = mutableListOf<TakeUI>() //список возможных выборов второго пользователя
    }

    private val bgPaint = Paint().apply { color = Color.DKGRAY }



    var width: Int = 0
    var height: Int = 0

    init {
        //заполнение игрового поля пустыми клетками (STATE_UNDEFINED)
        for (index in 0..99)
            takes.add(TakeUI(index).apply {
                state = 0
            })

        for (index in 0..99)
            takesPlayerTwo.add(TakeUI(index).apply {
                state = 0
            })

    }


//*****************расстановка жуков****************************************************
    //обработчик нажатия на клетку поля
    fun onClickFieldBugPlacingFirst(x: Float, y: Float) {
        val x: Int = (x / (width / 10)).toInt()
        val y: Int = (y / (height / 10)).toInt()

        val i: Int = y * 10 + x


        if (fourPartBug > 0) {
            if (i in 0..99) {
                //горизонтальная установка и удаление на первых трех строках
                if (i in 0..6 || i in 10..16 || i in 20..26) {
                    for (s in 0..3) takes[i + s].state = 1
                    if (chooseHorizontal == 1 || chooseHorizontal == 2) {
                        for (s in 0..3) takes[i + s].state = 0
                    }
                }
                //вертикальная установка по всему полю кроме первых трех строк
                if (chooseHorizontal == 0 && i in 30..99) {
                    for (s in 0..3) takes[i - s * 10].state = 1
                }
                // удаление вертикальных кораблей в последних трех столбах
                if (!(i in 0..6 || i in 10..16 || i in 20..26 || i in 30..36 || i in 40..46 || i in 50..56 || i in 60..66 || i in 70..76 || i in 80..86 || i in 90..96) && (chooseHorizontal == 1 || chooseHorizontal == 2)) {
                    for (s in 0..3) takes[i - s * 10].state = 0
                    chooseHorizontal = 2
                }
                //горизонтальное расположение кораблей кроме последних трех столбцов
                if ((i in 30..36 || i in 40..46 || i in 50..56 || i in 60..66 || i in 70..76 || i in 80..86 || i in 90..96) && (chooseHorizontal == 1)) {
                    for (s in 0..3) takes[i + s].state = 1
                    for (s in 1..3) takes[i - s * 10].state = 0
                }
                //удаление горизонтального корабля, завершение цикла установки корабля
                if (chooseHorizontal == 2 && (i in 30..36 || i in 40..46 || i in 50..56 || i in 60..66 || i in 70..76 || i in 80..86 || i in 90..96)) {
                    for (s in 0..3) takes[i + s].state = 0
                }
                chooseHorizontal++
                if (chooseHorizontal == 3) {
                    chooseHorizontal = 0
                }
            }
        } else if (threePartBug > 0) {
            if (i in 0..99) {
                //горизонтальная установка и удаление на первых трех строках
                if (i in 0..7 || i in 10..17) {
                    for (s in 0..2) takes[i + s].state = 1

                    if (chooseHorizontal == 1) {
                        for (s in 0..2) takes[i + s].state = 0
                        chooseHorizontal = 2
                    }
                }
                //вертикальная установка и удаление по всему полю кроме первых двух строк
                if (chooseHorizontal == 0 && i in 20..99) {
                    for (s in 0..2) takes[i - s * 10].state = 1
                }
                // удаление вертикальных кораблей в последних двух столбах
                if (!(i in 0..7 || i in 10..17 || i in 20..27 || i in 30..37 || i in 40..47 || i in 50..57 || i in 60..67 || i in 70..77 || i in 80..87 || i in 90..97) && chooseHorizontal == 1) {
                    for (s in 0..2) takes[i - s * 10].state = 0
                    chooseHorizontal = 2
                }
                //горизонтальное расположение кораблей кроме последних трех столбцов
                if ((i in 20..27 || i in 30..37 || i in 40..47 || i in 50..57 || i in 60..67 || i in 70..77 || i in 80..87 || i in 90..97) && (chooseHorizontal == 1)) {
                    for (s in 0..2) takes[i + s].state = 1
                    for (s in 1..2) takes[i - s * 10].state = 0
                }
                //удаление горизонтального корабля, завершение цикла установки корабля
                if (chooseHorizontal == 2 && (i in 20..27 || i in 30..37 || i in 40..47 || i in 50..57 || i in 60..67 || i in 70..77 || i in 80..87 || i in 90..97)) {
                    for (s in 0..2) takes[i + s].state = 0
                }
                chooseHorizontal++
                if (chooseHorizontal == 3) {
                    chooseHorizontal = 0
                }
            }
        } else if (twoPartBug > 0) {
            if (i in 0..99) {
                //горизонтальная установка и удаление на первой строке
                if (i in 0..8) {
                    for (s in 0..1) takes[i + s].state = 1

                    if (chooseHorizontal == 1) {
                        for (s in 0..1) takes[i + s].state = 0;
                        chooseHorizontal = 2
                    }
                }
                //вертикальная установка по всему полю кроме первой строки
                if (chooseHorizontal == 0 && i in 10..99) {
                    for (s in 0..1) takes[i - s * 10].state = 1
                }
                // удаление вертикальных кораблей в последних двух столбах
                if (!(i in 0..8 || i in 10..18 || i in 20..28 || i in 30..38 || i in 40..48 || i in 50..58 || i in 60..68 || i in 70..78 || i in 80..88 || i in 90..98) && chooseHorizontal == 1) {
                    for (s in 0..1) takes[i - s * 10].state = 0
                    chooseHorizontal = 2
                }
                //горизонтальное расположение кораблей кроме последних трех столбцов
                if ((i in 10..18 || i in 20..28 || i in 30..38 || i in 40..48 || i in 50..58 || i in 60..68 || i in 70..78 || i in 80..88 || i in 90..98) && (chooseHorizontal == 1)) {
                    for (s in 0..1) takes[i + s].state = 1
                    for (s in 1..1) takes[i - s * 10].state = 0
                }
                //удаление горизонтального корабля, завершение цикла установки корабля
                if (chooseHorizontal == 2 && (i in 10..18 || i in 20..28 || i in 30..38 || i in 40..48 || i in 50..58 || i in 60..68 || i in 70..78 || i in 80..88 || i in 90..98)) {
                    for (s in 0..1) takes[i + s].state = 0
                }
                chooseHorizontal++
                if (chooseHorizontal == 3) {
                    chooseHorizontal = 0
                }
            }
        } else if (onePartBug > 0) {
                takes[i].state = 1
                if (chooseHorizontal == 1) {
                    takes[i].state = 0
                    chooseHorizontal == 2
                    chooseHorizontal++
                }
                chooseHorizontal++
                if (chooseHorizontal == 3) {
                    chooseHorizontal = 0
                }
            }
    }

    fun onClickFieldBugPlacingSecond(x: Float, y: Float) {
        val x: Int = (x / (width / 10)).toInt()
        val y: Int = (y / (height / 10)).toInt()

        val i: Int = y * 10 + x


        if (fourPartBug > 0) {
            if (i in 0..99) {
                //горизонтальная установка и удаление на первых трех строках
                if (i in 0..6 || i in 10..16 || i in 20..26) {
                    for (s in 0..3) takesPlayerTwo[i + s].state = 1
                    if (chooseHorizontal == 1 || chooseHorizontal == 2) {
                        for (s in 0..3) takesPlayerTwo[i + s].state = 0
                    }
                }
                //вертикальная установка по всему полю кроме первых трех строк
                if (chooseHorizontal == 0 && i in 30..99) {
                    for (s in 0..3) takesPlayerTwo[i - s * 10].state = 1
                }
                // удаление вертикальных кораблей в последних трех столбах
                if (!(i in 0..6 || i in 10..16 || i in 20..26 || i in 30..36 || i in 40..46 || i in 50..56 || i in 60..66 || i in 70..76 || i in 80..86 || i in 90..96) && (chooseHorizontal == 1 || chooseHorizontal == 2)) {
                    for (s in 0..3) takesPlayerTwo[i - s * 10].state = 0
                    chooseHorizontal = 2
                }
                //горизонтальное расположение кораблей кроме последних трех столбцов
                if ((i in 30..36 || i in 40..46 || i in 50..56 || i in 60..66 || i in 70..76 || i in 80..86 || i in 90..96) && (chooseHorizontal == 1)) {
                    for (s in 0..3) takesPlayerTwo[i + s].state = 1
                    for (s in 1..3) takesPlayerTwo[i - s * 10].state = 0
                }
                //удаление горизонтального корабля, завершение цикла установки корабля
                if (chooseHorizontal == 2 && (i in 30..36 || i in 40..46 || i in 50..56 || i in 60..66 || i in 70..76 || i in 80..86 || i in 90..96)) {
                    for (s in 0..3) takesPlayerTwo[i + s].state = 0
                }
                chooseHorizontal++
                if (chooseHorizontal == 3) {
                    chooseHorizontal = 0
                }
            }
        } else if (threePartBug > 0) {
            if (i in 0..99) {
                //горизонтальная установка и удаление на первых трех строках
                if (i in 0..7 || i in 10..17) {
                    for (s in 0..2) takesPlayerTwo[i + s].state = 1;

                    if (chooseHorizontal == 1) {
                        for (s in 0..2) takesPlayerTwo[i + s].state = 0;
                        chooseHorizontal = 2
                    }
                }
                //вертикальная установка и удаление по всему полю кроме первых двух строк
                if (chooseHorizontal == 0 && i in 20..99) {
                    for (s in 0..2) takesPlayerTwo[i - s * 10].state = 1
                }
                // удаление вертикальных кораблей в последних двух столбах
                if (!(i in 0..7 || i in 10..17 || i in 20..27 || i in 30..37 || i in 40..47 || i in 50..57 || i in 60..67 || i in 70..77 || i in 80..87 || i in 90..97) && chooseHorizontal == 1) {
                    for (s in 0..2) takesPlayerTwo[i - s * 10].state = 0
                    chooseHorizontal = 2
                }
                //горизонтальное расположение кораблей кроме последних трех столбцов
                if ((i in 20..27 || i in 30..37 || i in 40..47 || i in 50..57 || i in 60..67 || i in 70..77 || i in 80..87 || i in 90..97) && (chooseHorizontal == 1)) {
                    for (s in 0..2) takesPlayerTwo[i + s].state = 1
                    for (s in 1..2) takesPlayerTwo[i - s * 10].state = 0
                }
                //удаление горизонтального корабля, завершение цикла установки корабля
                if (chooseHorizontal == 2 && (i in 20..27 || i in 30..37 || i in 40..47 || i in 50..57 || i in 60..67 || i in 70..77 || i in 80..87 || i in 90..97)) {
                    for (s in 0..2) takesPlayerTwo[i + s].state = 0
                }
                chooseHorizontal++
                if (chooseHorizontal == 3) {
                    chooseHorizontal = 0
                }
            }
        } else if (twoPartBug > 0) {
            if (i in 0..99) {
                //горизонтальная установка и удаление на первой строке
                if (i in 0..8) {
                    for (s in 0..1) takesPlayerTwo[i + s].state = 1

                    if (chooseHorizontal == 1) {
                        for (s in 0..1) takesPlayerTwo[i + s].state = 0
                        chooseHorizontal = 2
                    }
                }
                //вертикальная установка по всему полю кроме первой строки
                if (chooseHorizontal == 0 && i in 10..99) {
                    for (s in 0..1) takesPlayerTwo[i - s * 10].state = 1
                }
                // удаление вертикальных кораблей в последних двух столбах
                if (!(i in 0..8 || i in 10..18 || i in 20..28 || i in 30..38 || i in 40..48 || i in 50..58 || i in 60..68 || i in 70..78 || i in 80..88 || i in 90..98) && chooseHorizontal == 1) {
                    for (s in 0..1) takesPlayerTwo[i - s * 10].state = 0
                    chooseHorizontal = 2
                }
                //горизонтальное расположение кораблей кроме последних трех столбцов
                if ((i in 10..18 || i in 20..28 || i in 30..38 || i in 40..48 || i in 50..58 || i in 60..68 || i in 70..78 || i in 80..88 || i in 90..98) && (chooseHorizontal == 1)) {
                    for (s in 0..1) takesPlayerTwo[i + s].state = 1
                    for (s in 1..1) takesPlayerTwo[i - s * 10].state = 0
                }
                //удаление горизонтального корабля, завершение цикла установки корабля
                if (chooseHorizontal == 2 && (i in 10..18 || i in 20..28 || i in 30..38 || i in 40..48 || i in 50..58 || i in 60..68 || i in 70..78 || i in 80..88 || i in 90..98)) {
                    for (s in 0..1) takesPlayerTwo[i + s].state = 0
                }
                chooseHorizontal++
                if (chooseHorizontal == 3) {
                    chooseHorizontal = 0
                }
            }
        } else if (onePartBug > 0) {
            takesPlayerTwo[i].state = 1;
            if (chooseHorizontal == 1) {
                takesPlayerTwo[i].state = 0;
                chooseHorizontal == 2
                chooseHorizontal++
            }
            chooseHorizontal++
            if (chooseHorizontal == 3) {
                chooseHorizontal = 0
            }
        }
    }
//**************************конец расстановки жуков*****************************************

//**************************выстрелы по полям***********************************************
fun onClickGameFieldFirst(x: Float, y: Float) {
    val x: Int = (x / (width / 10)).toInt()
    val y: Int = (y / (height / 10)).toInt()

    if (takes[y * 10 + x].state == 1){  //bug_part
        takes[y * 10 + x].state = 3}   //explode
    if (takes[y * 10 + x].state == 0){ //undefined
        takes[y * 10 + x].state = 2}   //miss
}

    fun onClickGameFieldSecond(x: Float, y: Float) {
        val x: Int = (x / (width / 10)).toInt()
        val y: Int = (y / (height / 10)).toInt()

        if (takesPlayerTwo[y * 10 + x].state == 1){  //bug_part
            takesPlayerTwo[y * 10 + x].state = 3}   //explode
        if (takesPlayerTwo[y * 10 + x].state == 0){ //undefined
            takesPlayerTwo[y * 10 + x].state = 2}   //miss
    }

//**************************выстрелы по полям***********************************************


        fun autoBugsPlacing(kol: Int, parts: Int) {
            var count: Int = 1

            while (count <= kol) {

                val random = Random(System.currentTimeMillis())
                var kx: Int = random.nextInt(10 - (parts - 1))
                var ky: Int = random.nextInt(10 - (parts - 1))
                var j: Int = random.nextInt(2)

                if (j == 0) {
                    //вертикальная постановка
                    if (chek(parts, 0, kx, ky) == 0) {
                        for (i in 1..parts) {
                            takes[(ky + i - 1) * 10 + kx].state = 1
                        }
                        count++
                    }
                } else {
                    //горизонтальная
                    if (chek(parts, 1, kx, ky) == 0) {
                        for (i in 1..parts) {
                            takes[ky * 10 + kx + i - 1].state = 1
                        }
                        count++
                    }
                }
            }
        }


        fun chek(part: Int, j: Int, x: Int, y: Int): Int {
            var s: Int = 0
            if (j == 0) {
                //vertical
                var a: Int = x - 1
                var b: Int = x + 1
                var c: Int = y - 1
                var d: Int = y + part
                //проверка не вышли ли за пределы поля
                if (a == -1) a++
                if (b == 10) b--
                if (c == -1) c++
                if (d == 10) d--

                for (i in a..b)
                    for (j in c..d)
                        if (takes[j * 10 + i].state == 1)
                            s++
            } else {//gorizontal
                var a: Int = x - 1
                var b: Int = x + part
                var c: Int = y - 1
                var d: Int = y + 1
                if (a == -1) a++
                if (b == 10) b--
                if (c == -1) c++
                if (d == 10) d--

                for (i in a..b)
                    for (j in c..d)
                        if (takes[j * 10 + i].state == 1)
                            s++
            }
            return s
        }

        override fun render(canvas: Canvas) {

            canvas.drawRect(Rect(0, 0, width, height), bgPaint)

            var row = 0
            var col = 0
            val itemWidth = width / 10
            val itemHeight = height / 10

            for (take in takes) {

                take.x = col * itemWidth
                take.y = row * itemHeight

                take.width = itemWidth
                take.height = itemHeight

                take.render(canvas)

                if (++col == 10) {
                    col = 0
                    if (++row == 10)
                        return
                }
            }
        }

    override fun renderSecond(canvas: Canvas) {
        canvas.drawRect(Rect(0, 0, width, height), bgPaint)

        var row = 0
        var col = 0
        val itemWidth = width / 10
        val itemHeight = height / 10

        for (take in takesPlayerTwo) {

            take.x = col * itemWidth
            take.y = row * itemHeight

            take.width = itemWidth
            take.height = itemHeight

            take.renderSecond(canvas)

            if (++col == 10) {
                col = 0
                if (++row == 10)
                    return
            }
        }
    }

    override fun renderWithoutBugsParts(canvas: Canvas) {
        canvas.drawRect(Rect(0, 0, width, height), bgPaint)

        var row = 0
        var col = 0
        val itemWidth = width / 10
        val itemHeight = height / 10

        for (take in takes) {

            take.x = col * itemWidth
            take.y = row * itemHeight

            take.width = itemWidth
            take.height = itemHeight

            take.renderWithoutBugsParts(canvas)

            if (++col == 10) {
                col = 0
                if (++row == 10)
                    return
            }
        }
    }

    override fun renderWithoutBugsPartsSecond(canvas: Canvas) {
        canvas.drawRect(Rect(0, 0, width, height), bgPaint)

        var row = 0
        var col = 0
        val itemWidth = width / 10
        val itemHeight = height / 10

        for (take in takesPlayerTwo) {

            take.x = col * itemWidth
            take.y = row * itemHeight

            take.width = itemWidth
            take.height = itemHeight

            take.renderWithoutBugsPartsSecond(canvas)

            if (++col == 10) {
                col = 0
                if (++row == 10)
                    return
            }
        }
    }

    //определяет по тычку на какую клетку попали
        fun onClick(x: Float, y: Float): TakeUI? {
            //лямбда проверяющая входит ли нажатая область в опрееленные в Тэйках клетки
            return takes.firstOrNull { it.x < x && it.x + it.width >= x && it.y < y && it.y + it.height >= y }
        }

    fun onClickSecond(x: Float, y: Float): TakeUI? {
        //лямбда проверяющая входит ли нажатая область в опрееленные в Тэйках клетки
        return takesPlayerTwo.firstOrNull { it.x < x && it.x + it.width >= x && it.y < y && it.y + it.height >= y }
    }


        //отображение данных присланных с сервера
        fun setGameStateOne(state: GameState) {

            val game = state.game.toTypedArray()
            for (i in 0 until 99)
                takes[i].state = when (game[i]) {
                    Const.SELECT_TYPE_SHIP_PART -> TakeUI.STATE_BUG_PART
                    Const.SELECT_TYPE_MISS -> TakeUI.STATE_MISS
                    else -> TakeUI.STATE_UNDEFINED
                }

            if (state.winner != null)
                println("WIN!")
        }

    fun setGameStateSecond(state: GameState) {

        val game = state.game.toTypedArray()
        for (i in 0 until 99)
            takesPlayerTwo[i].state = when (game[i]) {
                Const.SELECT_TYPE_SHIP_PART -> TakeUI.STATE_BUG_PART
                Const.SELECT_TYPE_MISS -> TakeUI.STATE_MISS
                else -> TakeUI.STATE_UNDEFINED
            }

        if (state.winner != null)
            println("WIN!")
    }

    }

