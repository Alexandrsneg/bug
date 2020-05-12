package ru.sneg.android.bug.game.gameObjects


class BugsPlacing {

    companion object {
        var chooseHorizontal: Int = 0
    }


    // определяет в каком расположении можно ставить жука и стаит если это возможно
    fun horVertOrRemovePlacing(bugPart: Int, i: Int, chooseHor: Int, bug: Bugs, listBug: MutableList<Int>){
        //переменная для проверки выхода хвоста жука за пределы поля по иксу
        var lineX = i%10

        //вертикальные расстановки
        if (chooseHorizontal == 0 && ((lineX + bugPart-1) > 9 || !fieldNotEmpty(i, bugPart, chooseHor, bug))) {
                //если хвост не выходит за пределы поля вверх по игрику
                if (i - 10*(bugPart-1) >= 0) {
                    bugPlacing(chooseHorizontal, i, bugPart, bug, listBug)
                }
            else {
                    chooseHorizontal++
                }
        }
        //удаление вертикального жука (только вертикальная возможность установки
        if(chooseHorizontal == 1 && bug.takes[i].state == 1)
            removeBug(i,bugPart, chooseHorizontal,bug,listBug)

        //горзонтальные расстановки
        if (chooseHorizontal == 1 && (i - 10*(bugPart-1) < 0 || !fieldNotEmpty(i, bugPart, chooseHor, bug))) {
            //если хвост в пределах поля по иксу
            if ((lineX + bugPart-1) <= 9) {
                // удаление вертикального жука
                if(bug.takes[i].state == 1)
                    removeBug(i,bugPart, chooseHorizontal,bug,listBug)

                bugPlacing(chooseHorizontal, i, bugPart, bug, listBug)
            }
            else {
                chooseHorizontal++
            }
        }
        //удаление горизонтального жука (по третьему нажатию)Б завершение цикла установки
        if (chooseHorizontal == 2 && bug.takes[i].state == 1 )
            removeBug(i,bugPart, chooseHorizontal,bug,listBug)

        chooseHorizontal++

            if (chooseHorizontal == 3)
                chooseHorizontal = 0
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
    //проверка что поле не занято (выведет ТРУ если не занято)
    private fun fieldNotEmpty(i: Int, bugPart: Int, chooseHor: Int, bug: Bugs): Boolean {
        var notEmpty: Boolean = false

        if (chooseHor == 0 ) {
            for (s in 0 until bugPart)
                try {
                    if (bug.takes[i - 10 * s].state != 0)
                        notEmpty = true
                }
                catch (e: Exception){ println("fieldNotEmpty Exception")}
        }
        if (chooseHor == 1) {
            for (s in 0 until bugPart)
                try {
                    if (bug.takes[i + s].state != 0)
                        notEmpty = true
                }
                catch (e: Exception){ println("fieldNotEmpty Exception")}
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

    // копияиз PlayingField, если что-то пойдет не так
   /* fun onClickFieldBugPlacing(x: Float, y: Float, bug: Bugs) {

        val x: Int = (x / (width / 10)).toInt()
        val y: Int = (y / (height / 10)).toInt()

        val i: Int = y * 10 + x

        if (bug.fourPartBug > 0) {
            if (i in 0..99) {
                //горизонтальная установка и удаление на первых трех строках
                if ((i in 0..6 || i in 10..16 || i in 20..26)) {
                    if (chooseHorizontal == 0) {
                        chooseHorizontal = 1
                        bugPlacing(chooseHorizontal, i, 4, bug, bug.listBugFour)
                    }
                }
                //вертикальная установка по всему полю кроме первых трех строк
                if (chooseHorizontal == 0 && i in 30..99) {
                    bugPlacing(chooseHorizontal, i, 4, bug, bug.listBugFour)

                }
                //горизонтальная расстановка кроме последних трех столбцов
                if (chooseHorizontal == 1 && (i in 30..36 || i in 40..46 || i in 50..56 || i in 60..66 || i in 70..76 || i in 80..86 || i in 90..96)) {
                    removeBug(i,4, chooseHorizontal,bug,bug.listBugFour)
                    bugPlacing(chooseHorizontal, i, 4, bug, bug.listBugFour)
                }
                // удаление вертикальных кораблей в последних трех столбах
                if (!( i in 0..6 || i in 10..16 || i in 20..26 || i in 30..36 || i in 40..46 || i in 50..56 || i in 60..66 || i in 70..76 || i in 80..86 || i in 90..96) && (chooseHorizontal == 1)) {
                    removeBug(i,4, chooseHorizontal,bug,bug.listBugFour )
                    chooseHorizontal = 2
                }
                //удаление горизонтального жука, завершение цикла установки корабля
                if (chooseHorizontal == 2 )
                    removeBug(i,4, chooseHorizontal,bug,bug.listBugFour)

                chooseHorizontal++
                if (chooseHorizontal == 3)
                    chooseHorizontal = 0
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
    }*/

}