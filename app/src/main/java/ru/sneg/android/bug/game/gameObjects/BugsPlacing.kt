package ru.sneg.android.bug.game.gameObjects

import kotlin.random.Random


class BugsPlacing {

    companion object {
        var orientationAndRemoving: Int = 0 // 0 - vertical, 1 - horizontal, 2 - removing
    }

    fun autoPlacing(bugPart: Int, bug: Bugs, listBug: MutableList<Int>){
        val random = Random(System.nanoTime())
        var i = 0
        do{i = random.nextInt(0,100)}
            while(bug.takes[i].state != 0 )

            placingEngine(bugPart, i, bug, listBug)
            bug.acceptBugSurrounding()

    }

    // движок расстановки и удаления всех видов жуков
    fun placingEngine(bugPart: Int, i: Int, bug: Bugs, listBug: MutableList<Int>){

        val lineX = i%10 //переменная для проверки выхода хвоста жука за пределы поля по иксу

        // *********************вертикальные расстановки****************************
        //все возможные случаи вертикальных расстановок
        if (orientationAndRemoving == 0 && ((lineX + bugPart-1) > 9 || !fieldNotEmpty(i, bugPart, orientationAndRemoving, bug))) {
                //если хвост не выходит за пределы поля вверх по игрику
                if (i - 10*(bugPart-1) >= 0) {
                    bugPlacing(orientationAndRemoving, i, bugPart, bug, listBug)
                } else { orientationAndRemoving++ }
        }

        //увеличение счетчика ориентаций для мгновенной установки горизонтального корабля, если поля выше заняты
        if(orientationAndRemoving == 0 && fieldNotEmpty(i, bugPart, orientationAndRemoving, bug) && bug.takes[i].state == 0)
            orientationAndRemoving++

        //удаление вертикального жука (только вертикальная возможность установки)
        if(orientationAndRemoving == 1 && bug.takes[i].state == 1) {
            removeBug(i, bugPart, orientationAndRemoving, bug, listBug)
            //если гоизонтально поставить нельзя, то увеличиваем счетчик ориентации для обнуления
            if (fieldNotEmpty(i, bugPart, orientationAndRemoving, bug)){
                orientationAndRemoving ++
            }
        }
        //****************************горзонтальные расстановки******************************************
        //все возможные случаи горизонтальных расстановок
        if (orientationAndRemoving == 1 && (i - 10*(bugPart-1) < 0 || !fieldNotEmpty(i, bugPart, orientationAndRemoving, bug))) {
            //если хвост в пределах поля по иксу
            if ((lineX + bugPart-1) <= 9) {
                // удаление вертикального жука
                if(bug.takes[i].state == 1) {
                    removeBug(i, bugPart, orientationAndRemoving, bug, listBug)
                    orientationAndRemoving++
                }
                //установка горизонтального
                bugPlacing(orientationAndRemoving, i, bugPart, bug, listBug)
            }
            else { orientationAndRemoving++ }
        }

        //удаление горизонтального жука (по третьему нажатию) завершение цикла установки
        if (orientationAndRemoving == 2 && bug.takes[i].state == 1 )
            removeBug(i,bugPart, orientationAndRemoving,bug,listBug)

        //позволяет ставить и удалять однопалубный одним нажатием
        if (bug.twoPartBug == 0 && bug.onePartBug > 0){
            orientationAndRemoving++
        }
        //увеличение счетчика ориентаиции при каждой итерации цикла
        orientationAndRemoving++

            if (orientationAndRemoving >= 3) //обнуление счетчика ориентаций
                orientationAndRemoving = 0
    }

    //логика возможност выставления жука в зависимости от занятого перед ним пространства
    private fun bugPlacing(chooseHor: Int, i: Int, bugPart: Int, bug: Bugs, listBug: MutableList<Int>) {
        // *********************вертикальные расстановки****************************
        if (chooseHor == 0) {
            if (fieldNotEmpty(i, bugPart, chooseHor, bug)) {
                orientationAndRemoving = 1
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
                orientationAndRemoving = 2
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

}