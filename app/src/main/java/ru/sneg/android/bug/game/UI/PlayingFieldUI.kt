package ru.sneg.android.bug.game.UI

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.gameObjects.Const
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
                if ((i in 0..6 || i in 10..16 || i in 20..26)) {
                    if (chooseHorizontal == 0) {
                        chooseHorizontal = 1
                        bugsWithSurrounding(chooseHorizontal, i, 4, takes)
                    }
                }

                //вертикальная установка по всему полю кроме первых трех строк
                if (chooseHorizontal == 0 && i in 30..99) {
                    bugsWithSurrounding(chooseHorizontal, i, 4, takes)
                }
                //горизонтальная расстановка кроме последних трех столбцов
                if (chooseHorizontal == 1 && (i in 30..36 || i in 40..46 || i in 50..56 || i in 60..66 || i in 70..76 || i in 80..86 || i in 90..96)) {
                    clear(i,4, chooseHorizontal,takes)
                    bugsWithSurrounding(chooseHorizontal,i,4, takes)
                    //chooseHorizontal = 2
                }
                // удаление вертикальных кораблей в последних трех столбах
                if (!( i in 0..6 || i in 10..16 || i in 20..26 || i in 30..36 || i in 40..46 || i in 50..56 || i in 60..66 || i in 70..76 || i in 80..86 || i in 90..96) && (chooseHorizontal == 1)) {
                    clear(i,4, chooseHorizontal,takes)
                    chooseHorizontal = 2
                }
                //удаление горизонтального жука, завершение цикла установки корабля
                if (chooseHorizontal == 2 ) {
                    clear(i,4, chooseHorizontal,takes)
                }
                chooseHorizontal++
                if (chooseHorizontal == 3) {
                    chooseHorizontal = 0
                }
            }
        } else if (fourPartBug == 0 && threePartBug > 0) {
            if (i in 0..99) {
                //горизонтальная установка и удаление на первых двух строках
                if ((i in 0..7 || i in 10..17)) {
                    if (chooseHorizontal == 0) {
                        chooseHorizontal = 1
                        bugsWithSurrounding(chooseHorizontal, i, 3, takes)
                    }
                }
                //вертикальная установка по всему полю кроме первых двух строк
                if (chooseHorizontal == 0 && i in 20..99) {
                    bugsWithSurrounding(chooseHorizontal, i, 3, takes)
                }
                //горизонтальная расстановка кроме последних двух столбцов
                if (chooseHorizontal == 1 && (i in 20..27 ||i in 30..37 || i in 40..47 || i in 50..57 || i in 60..67 || i in 70..77 || i in 80..87 || i in 90..97)) {
                    //чтобы не перетирались значения уже установленных жуков
                    if(takes[i].state == 1) {
                        clear(i, 3, chooseHorizontal, takes)
                    }
                    bugsWithSurrounding(chooseHorizontal,i,3, takes)
                    //chooseHorizontal = 2
                }
                // удаление вертикальных кораблей в последних двух столбах
                if (!( i in 0..7 || i in 10..17 || i in 20..27 || i in 30..37 || i in 40..47 || i in 50..57 || i in 60..67 || i in 70..77 || i in 80..87 || i in 90..97) && (chooseHorizontal == 1)) {
                    //чтобы не перетирались значения уже установленных жуков
                    if(takes[i].state == 1) {
                        clear(i, 3, chooseHorizontal, takes)
                    }
                    chooseHorizontal = 2
                }
                //удаление горизонтального жука, завершение цикла установки корабля
                if (chooseHorizontal == 2 ) {
                    //чтобы не перетирались значения уже установленных жуков
                    if(takes[i].state == 1) {
                        clear(i, 3, chooseHorizontal, takes)
                    }
                }
                chooseHorizontal++
                if (chooseHorizontal == 3) {
                    chooseHorizontal = 0
                }
            }
        } else if (threePartBug == 0 && twoPartBug > 0) {
            if (i in 0..99) {
                //горизонтальная установка и удаление на первых двух строках
                if (i in 0..8) {
                    if (chooseHorizontal == 0) {
                        chooseHorizontal = 1
                        bugsWithSurrounding(chooseHorizontal, i, 2, takes)
                    }
                }
                //вертикальная установка по всему полю кроме первых двух строк
                if (chooseHorizontal == 0 && i in 10..99) {
                    bugsWithSurrounding(chooseHorizontal, i, 2, takes)
                }
                //горизонтальная расстановка кроме последних двух столбцов
                if (chooseHorizontal == 1 && (i in 8..18 ||i in 20..28 || i in 30..38 || i in 40..48 || i in 50..58 || i in 60..68 || i in 70..78 || i in 80..88 || i in 90..98)) {
                    if(takes[i].state == 1) {
                        clear(i, 2, chooseHorizontal, takes)
                    }
                    bugsWithSurrounding(chooseHorizontal,i,2, takes)
                    //chooseHorizontal = 2
                }
                // удаление вертикальных кораблей в последних двух столбах
                if (!( i in 0..8 || i in 10..18 || i in 20..28 || i in 30..38 || i in 40..48 || i in 50..58 || i in 60..68 || i in 70..78 || i in 80..88 || i in 90..98) && (chooseHorizontal == 1)) {
                    if(takes[i].state == 1) {
                        clear(i, 2, chooseHorizontal, takes)
                    }
                    chooseHorizontal = 2
                }
                //удаление горизонтального жука, завершение цикла установки корабля
                if (chooseHorizontal == 2 ) {
                    if(takes[i].state == 1) {
                        clear(i, 2, chooseHorizontal, takes)
                    }
                }
                chooseHorizontal++
                if (chooseHorizontal == 3) {
                    chooseHorizontal = 0
                }
            }
        } else if (twoPartBug == 0 && onePartBug > 0) {
            bugsWithSurrounding(chooseHorizontal,i,1, takes)
                if (chooseHorizontal == 1) {
                    if(takes[i].state == 1) {
                        clear(i, 1, chooseHorizontal, takes)
                    }
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


    }
//**************************конец расстановки жуков*****************************************

//**************************выстрелы по полям***********************************************
fun onClickGameFieldFirst(x: Float, y: Float) {
    val x: Int = (x / (width / 10)).toInt()
    val y: Int = (y / (height / 10)).toInt()

    if (takes[y * 10 + x].state == 1){  //bug_part
        takes[y * 10 + x].state = 3}   //explode
    if (takes[y * 10 + x].state == 0 || takes[y * 10 + x].state == 4){ //undefined
        takes[y * 10 + x].state = 2}   //miss
}

    fun onClickGameFieldSecond(x: Float, y: Float) {
        val x: Int = (x / (width / 10)).toInt()
        val y: Int = (y / (height / 10)).toInt()

        if (takesPlayerTwo[y * 10 + x].state == 1){  //bug_part
            takesPlayerTwo[y * 10 + x].state = 3}   //explode
        if (takesPlayerTwo[y * 10 + x].state == 0 || takesPlayerTwo[y * 10 + x].state == 4){ //undefined
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

    private fun bugsWithSurrounding(chooseHor: Int, i: Int, bugPart: Int, tk: MutableList<TakeUI>) {
        // *********************вертикальные расстановки****************************
        if (chooseHor == 0) {
            // проерка, что на пути жука statы пустые
            if (!fieldNotEmpty(i, bugPart, chooseHor, tk)) {
                for (s in 0 until bugPart) tk[i - 10 * s].state = 1 // сам жук
                //если голова в крайнем левом столбце
                if (i % 10 == 0) {
                    // если голова в крайнем нижнем углу
                    if (i == 90) {
                        tk[i - 10 * bugPart].state = 4
                        for (s in 0..bugPart) tk[(i + 1) - 10 * s].state = 4
                    }
                    //если хвост попадает на крайний левый угол
                    if (i - 10 * bugPart == -10) {
                        tk[i + 10].state = 4
                        for (s in -1 until bugPart) tk[(i + 1) - 10 * s].state = 4 // поле вокруг жука
                    }
                    // все средние значения в первом столбце
                    if ((i - 10 * bugPart != -10) && i != 90) {
                        tk[i + 10].state = 4
                        tk[i - 10 * bugPart].state = 4
                        for (s in -1..bugPart) tk[(i + 1) - 10 * s].state = 4
                    }
                }
                //если голова в крайнем правом столбце
                val list = listOf<Int>(9,19,29,39,49,59,69,79,89,99)
                if (i in list) {
                    // если голова в крайнем правом углу
                    if (i == 99) {
                        tk[i - 10 * bugPart].state = 4
                        for (s in 0..bugPart) tk[i - 1 - 10 * s].state = 4
                    }
                    //если хвост попадает на крайний правый угол
                    if (i - 10 * bugPart == -1) {
                        tk[i + 10].state = 4
                        for (s in -1 until bugPart) tk[i - 1 - 10 * s].state = 4
                    }
                    if ((i - 10 * bugPart != -1) && i != 99) {
                        tk[i + 10].state = 4
                        tk[i - 10 * bugPart].state = 4
                        for (s in -1..bugPart) tk[i - 1 - 10 * s].state = 4
                    }
                }
                // если голова идет по нижнему краю
                if (i in 91..98) {
                    tk[i - 10 * bugPart].state = 4
                    for (s in 0..bugPart) {
                        tk[i - 1 - 10 * s].state = 4; tk[(i + 1) - 10 * s].state = 4
                    }
                }
                // если хвост идёт по верхнему краю
                if ((i - 10 * bugPart) in -9..-2) {
                    tk[i + 10].state = 4
                    for (s in -1 until bugPart) {
                        tk[i - 1 - 10 * s].state = 4; tk[(i + 1) - 10 * s].state = 4
                    }
                }
                // все не пограничные значения
                /*if ((i - 10 * bugPart) !in -9..-2 && ( i in 11..18 || i in 21..28 || i in 31..38 || i in 41..48 || i in 51..58 || i in 61..68 || i in 71..78 || i in 81..88)) {
                    tk[i - 10 * bugPart].state = 4
                    tk[i + 10].state = 4
                    for (s in -1..bugPart) {
                        tk[i - 1 - 10 * s].state = 4; tk[(i + 1) - 10 * s].state = 4
                    }
                }*/
            }
        }
        //*********************горизонтальные расстановки****************************
        if (chooseHor == 1) {
            /*if (fieldNotEmpty(i, bugPart, chooseHor, tk)) {
                for (s in 0 until bugPart) tk[i + 1 * s].state = 1 // сам жук
                //если голова в крайнем левом столбце
                if (i % 10 == 0) {
                    // если голова в крайнем нижнем углу
                    if (i == 90) {
                        tk[i + bugPart].state = 4
                        for (s in 0..bugPart) tk[i - 10 + 1 * s].state = 4
                    }
                    //если голова попадает на крайний левый угол
                    if (i == 0) {
                        tk[i + bugPart].state = 4
                        for (s in 0..bugPart) tk[i + 10 + 1 * s].state = 4 // поле вокруг жука
                    }
                    // все средние значения в первом столбце
                    if (i != 0 && i != 90) {
                        tk[i + bugPart].state = 4
                        for (s in 0..bugPart) tk[i - 10 + 1 * s].state = 4
                        for (s in 0..bugPart) tk[i + 10 + 1 * s].state = 4
                    }
                }
            }*/
            // проерка, что на пути жука statы пустые
            if (!fieldNotEmpty(i, bugPart, chooseHor, tk)) {
                for (s in 0 until bugPart) tk[i + 1 * s].state = 1 // сам жук
                //если голова в крайнем левом столбце
                if (i % 10 == 0) {
                    // если голова в крайнем нижнем углу
                    if (i == 90) {
                        tk[i + bugPart].state = 4
                        for (s in 0..bugPart) tk[i - 10 + 1 * s].state = 4
                    }
                    //если голова попадает на крайний левый угол
                   /* if (i == 0) {
                        tk[i + bugPart].state = 4
                        for (s in 0..bugPart) tk[i + 10 + 1 * s].state = 4 // поле вокруг жука
                    }*/
                    // все средние значения в первом столбце
                    if (i != 0 && i != 90) {
                        tk[i + bugPart].state = 4
                        for (s in 0..bugPart) tk[i - 10 + 1 * s].state = 4
                        for (s in 0..bugPart) tk[i + 10 + 1 * s].state = 4
                    }
                }
                //если хвост идёт по крайней правой колонке
                val list = listOf<Int>(9,19,29,39,49,59,69,79,89,99)
                if ((i + 1 * (bugPart-1)) in list) {
                    // если хвост в  правом верхнем углу
                    if ((i + 1 * (bugPart-1)) == 9) {
                        tk[i - 1].state = 4
                        for (s in -1 until bugPart) tk[i + 10 + s].state = 4
                    }
                    // если хвост в  правом нижнем углу
                    if ((i + 1 * (bugPart-1)) == 99) {
                        tk[i - 1].state = 4
                        for (s in -1 until bugPart) tk[i - 10 + s].state = 4
                    }
                    if (((i + 1 * (bugPart-1)) in list) && (i + 1 * (bugPart-1)) != 99 && (i + 1 * (bugPart-1)) != 9){
                        tk[i - 1].state = 4
                        for (s in -1 until bugPart) tk[i - 10 + s].state = 4
                        for (s in -1 until bugPart) tk[i + 10 + s].state = 4
                    }
                }
                // если жук идет по нижнему краю
                if ((i + bugPart-1) in 91..98 && i != 90 && i != 99) {

                    tk[i - 1].state = 4
                    tk[i + bugPart].state = 4
                    for (s in -1..bugPart) tk[i - 10 + s].state = 4

                }
                // если жук идёт по верхнему краю
                if ((i + bugPart-1) in 1..8 && i != 0 && i != 9) {
                    tk[i - 1].state = 4
                    tk[i + bugPart].state = 4
                    for (s in -1..bugPart) tk[i + 10 + s].state = 4

                }
                // все не пограничные значения
               /* if ((i in 11..18 || i in 21..28 || i in 31..38 || i in 41..48 || i in 51..58 || i in 61..68 || i in 71..78 || i in 81..88)
                        && !(((i + 1 * (bugPart-1)) in list) && (i + 1 * (bugPart-1)) != 99 && (i + 1 * (bugPart-1)) != 9)) {
                    tk[i - 1].state = 4
                    tk[i + bugPart].state = 4
                    for (s in -1..bugPart){
                        tk[i + 10 + s].state = 4; tk[i - 10 + s].state = 4
                    }
                }*/
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



    private  fun clear(i: Int, bugPart: Int, chooseHor: Int, tk: MutableList<TakeUI>){
        if (chooseHor == 1) {
            for (s in 0 until bugPart)
                try {
                    tk[i - 10 * s].state = 0 // сам жук
                }
                catch (e : IndexOutOfBoundsException){
                    println(e)
                }
            //если голова в крайнем левом столбце
            if (i % 10 == 0) {

                // если голова в крайнем нижнем углу
                if (i == 90) {
                    tk[i - 10 * bugPart].state = 0
                    for (s in 0..bugPart) tk[(i + 1) - 10 * s].state = 0
                }
                //если хвост попадает на крайний левый угол
                if (i - 10 * bugPart == -10) {
                    tk[i + 10].state = 0
                    for (s in -1 until bugPart) tk[(i + 1) - 10 * s].state = 0 // поле вокруг жука
                }
                // все средние значения в первом столбце
                if ((i - 10 * bugPart != -10) && i != 90) {
                    try {
                        tk[i + 10].state = 0
                        tk[i - 10 * bugPart].state = 0
                        for (s in -1..bugPart) tk[(i + 1) - 10 * s].state = 0
                    }
                    catch (e : ArrayIndexOutOfBoundsException){
                        println(e)
                    }
                }
            }
            //если голова в крайнем правом столбце
            val list = listOf<Int>(9,19,29,39,49,59,69,79,89,99)
            if (i in list) {
                // если голова в крайнем правом углу
                if (i == 99) {
                    tk[i - 10 * bugPart].state = 0
                    for (s in 0..bugPart) tk[i - 1 - 10 * s].state = 0
                }
                //если хвост попадает на крайний правый угол
                if (i - 10 * bugPart == -1) {
                    tk[i + 10].state = 0
                    for (s in -1 until bugPart) tk[i - 1 - 10 * s].state = 0
                }
                if ((i - 10 * bugPart != -1) && i != 99) {
                    tk[i + 10].state = 0
                    tk[i - 10 * bugPart].state = 0
                    for (s in -1..bugPart) tk[i - 1 - 10 * s].state = 0
                }
            }
            // если голова идет по нижнему краю
            if (i in 91..98) {
                tk[i - 10 * bugPart].state = 0
                for (s in 0..bugPart) {
                    tk[i - 1 - 10 * s].state = 0; tk[(i + 1) - 10 * s].state = 0
                }
            }
            // если хвост идёт по верхнему краю
            if ((i - 10 * bugPart) in -9..-2) {
                tk[i + 10].state = 0
                for (s in -1 until bugPart) {
                    tk[i - 1 - 10 * s].state = 0; tk[(i + 1) - 10 * s].state = 0
                }
            }
            // все не пограничные значения
            if (i in 41..48 || i in 51..58 || i in 61..68 || i in 71..78 || i in 81..88) {
                tk[i - 10 * bugPart].state = 0
                tk[i + 10].state = 0
                for (s in -1..bugPart) {
                    tk[i - 1 - 10 * s].state = 0; tk[(i + 1) - 10 * s].state = 0
                }
            }
        }
        if (chooseHor == 2){
            for (s in 0 until bugPart)
                    try {
                        tk[i + s].state = 0 // сам жук
                    }
                    catch (e : IndexOutOfBoundsException){
                        println(e)
                    }
            //если голова в крайнем левом столбце
            if (i % 10 == 0) {
                // если голова в крайнем нижнем углу
                if (i == 90) {
                    tk[i + bugPart].state = 0
                    for (s in 0..bugPart) tk[i - 10 + s].state = 0
                }
                //если голова попадает на крайний левый угол
                if (i == 0) {
                    tk[i + bugPart].state = 0
                    for (s in 0..bugPart) tk[i + 10 + s].state = 0 // поле вокруг жука
                }
                // все средние значения в первом столбце
                if (i != 0 && i != 90) {
                    tk[i + bugPart].state = 0
                    for (s in 0..bugPart) tk[i - 10 + s].state = 0
                    for (s in 0..bugPart) tk[i + 10 + s].state = 0
                }
            }
            //если хвост идёт по крайней правой колонке
            val list = listOf<Int>(9,19,29,39,49,59,69,79,89,99)
            if ((i + 1 * (bugPart-1)) in list) {
                // если хвост в  правом верхнем углу
                if ((i + 1 * (bugPart-1)) == 9) {
                    tk[i - 1].state = 0
                    for (s in -1 until bugPart) tk[i + 10 + s].state = 0
                }
                // если хвост в  правом нижнем углу
                if ((i + 1 * (bugPart-1)) == 99) {
                    tk[i - 1].state = 0
                    for (s in -1 until bugPart) tk[i - 10 + s].state = 0
                }
                if (((i + 1 * (bugPart-1)) in list) && (i + 1 * (bugPart-1)) != 99 && (i + 1 * (bugPart-1)) != 9){
                    tk[i - 1].state = 0
                    for (s in -1 until bugPart) tk[i - 10 + s].state = 0
                    for (s in -1 until bugPart) tk[i + 10 + s].state = 0
                }
            }
            // если жук идет по нижнему краю
            if ((i + bugPart-1) in 91..98 && i != 90 && i != 99) {

                tk[i - 1].state = 0
                tk[i + bugPart].state = 0
                for (s in -1..bugPart) tk[i - 10 + s].state = 0

            }
            // если жук идёт по верхнему краю
            if ((i + bugPart-1) in 1..8 && i != 0 && i != 9) {
                tk[i - 1].state = 0
                tk[i + bugPart].state = 0
                for (s in -1..bugPart) tk[i + 10 + s].state = 0

            }
            // все не пограничные значения
            if ((i in 11..18 || i in 21..28 || i in 31..38 || i in 41..48 || i in 51..58 || i in 61..68 || i in 71..78 || i in 81..88)
                    && !(((i + 1 * (bugPart-1)) in list) && (i + 1 * (bugPart-1)) != 99 && (i + 1 * (bugPart-1)) != 9)) {
                tk[i - 1].state = 0
                tk[i + bugPart].state = 0
                for (s in -1..bugPart){
                    tk[i + 10 + s].state = 0; tk[i - 10 + s].state = 0
                }
            }
        }
    }


}

