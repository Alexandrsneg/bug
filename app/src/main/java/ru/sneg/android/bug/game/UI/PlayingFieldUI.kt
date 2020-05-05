package ru.sneg.android.bug.game.UI

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import kotlinx.android.synthetic.main.fragment_bug_placement_player.*
import ru.sneg.android.bug.credentials.bugPlacement.BugPlacementPlayerSecondFragment
import ru.sneg.android.bug.credentials.game.bugPlacement.BugPlacementPlayerFragment
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
                            bugsWithSurrounding(chooseHorizontal, i, 4, takes, BugPlacementPlayerFragment.listBugFour)
                        }
                    }
                    //вертикальная установка по всему полю кроме первых трех строк
                    if (chooseHorizontal == 0 && i in 30..99) {
                        bugsWithSurrounding(chooseHorizontal, i, 4, takes, BugPlacementPlayerFragment.listBugFour)
                    }
                    //горизонтальная расстановка кроме последних трех столбцов
                    if (chooseHorizontal == 1 && (i in 30..36 || i in 40..46 || i in 50..56 || i in 60..66 || i in 70..76 || i in 80..86 || i in 90..96)) {
                        removeBug(i,4, chooseHorizontal,takes,BugPlacementPlayerFragment.listBugFour )
                        bugsWithSurrounding(chooseHorizontal,i,4, takes, BugPlacementPlayerFragment.listBugFour)
                        //chooseHorizontal = 2
                    }
                    // удаление вертикальных кораблей в последних трех столбах
                    if (!( i in 0..6 || i in 10..16 || i in 20..26 || i in 30..36 || i in 40..46 || i in 50..56 || i in 60..66 || i in 70..76 || i in 80..86 || i in 90..96) && (chooseHorizontal == 1)) {
                        removeBug(i,4, chooseHorizontal,takes, BugPlacementPlayerFragment.listBugFour)
                        chooseHorizontal = 2
                    }
                    //удаление горизонтального жука, завершение цикла установки корабля
                    if (chooseHorizontal == 2 ) {
                        removeBug(i,4, chooseHorizontal,takes, BugPlacementPlayerFragment.listBugFour)
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
                            when (threePartBug) {
                                2 -> {
                                    bugsWithSurrounding(chooseHorizontal, i, 3, takes, BugPlacementPlayerFragment.listBugThreeFirst)
                                }
                                1 -> {
                                    bugsWithSurrounding(chooseHorizontal, i, 3, takes, BugPlacementPlayerFragment.listBugThreeSecond)
                                }
                            }
                        }
                    }
                    //вертикальная установка по всему полю кроме первых двух строк
                    if (chooseHorizontal == 0 && i in 20..99) {
                        when (threePartBug) {
                            2 -> {
                                bugsWithSurrounding(chooseHorizontal, i, 3, takes, BugPlacementPlayerFragment.listBugThreeFirst)
                            }
                            1 -> {
                                bugsWithSurrounding(chooseHorizontal, i, 3, takes, BugPlacementPlayerFragment.listBugThreeSecond)
                            }
                        }
                    }
                    //горизонтальная расстановка кроме последних двух столбцов
                    if (chooseHorizontal == 1 && (i in 20..27 ||i in 30..37 || i in 40..47 || i in 50..57 || i in 60..67 || i in 70..77 || i in 80..87 || i in 90..97)) {
                        //чтобы не перетирались значения уже установленных жуков
                        when (threePartBug) {
                            2 -> {
                                if (takes[i].state == 1)
                                    removeBug(i, 3, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugThreeFirst )
                                bugsWithSurrounding(chooseHorizontal, i, 3, takes, BugPlacementPlayerFragment.listBugThreeFirst)
                            }
                            1 -> {
                                if (takes[i].state == 1)
                                    removeBug(i, 3, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugThreeSecond )
                                bugsWithSurrounding(chooseHorizontal, i, 3, takes, BugPlacementPlayerFragment.listBugThreeSecond)
                            }
                        }

                    }
                    // удаление вертикальных кораблей в последних двух столбах
                    if (!( i in 0..7 || i in 10..17 || i in 20..27 || i in 30..37 || i in 40..47 || i in 50..57 || i in 60..67 || i in 70..77 || i in 80..87 || i in 90..97) && (chooseHorizontal == 1)) {
                        //чтобы не перетирались значения уже установленных жуков
                        if (takes[i].state == 1)
                            when (threePartBug) {
                                2 -> {
                                    if (takes[i].state == 1)
                                    removeBug(i, 3, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugThreeFirst )
                                    bugsWithSurrounding(chooseHorizontal, i, 3, takes, BugPlacementPlayerFragment.listBugThreeFirst)
                                }
                                1 -> {
                                    if (takes[i].state == 1)
                                    removeBug(i, 3, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugThreeSecond )
                                    bugsWithSurrounding(chooseHorizontal, i, 3, takes, BugPlacementPlayerFragment.listBugThreeSecond)
                                }
                            }
                        chooseHorizontal = 2
                    }
                    //удаление горизонтального жука, завершение цикла установки корабля
                    if (chooseHorizontal == 2 ) {
                        //чтобы не перетирались значения уже установленных жуков
                        if (takes[i].state == 1) {
                            when (threePartBug) {
                                2 -> {
                                    if (takes[i].state == 1)
                                    removeBug(i, 3, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugThreeFirst )
                                    bugsWithSurrounding(chooseHorizontal, i, 3, takes, BugPlacementPlayerFragment.listBugThreeFirst)
                                }
                                1 -> {
                                    if (takes[i].state == 1)
                                    removeBug(i, 3, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugThreeSecond )
                                    bugsWithSurrounding(chooseHorizontal, i, 3, takes, BugPlacementPlayerFragment.listBugThreeSecond)
                                }
                            }
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
                            when (twoPartBug) {
                                3 -> {
                                    bugsWithSurrounding(chooseHorizontal, i, 2, takes, BugPlacementPlayerFragment.listBugTwoFirst)
                                }
                                2 -> {
                                    bugsWithSurrounding(chooseHorizontal, i, 2, takes, BugPlacementPlayerFragment.listBugTwoSecond)
                                }
                                1 -> {
                                    bugsWithSurrounding(chooseHorizontal, i, 2, takes, BugPlacementPlayerFragment.listBugTwoThird)
                                }
                            }
                        }
                    }
                    //вертикальная установка по всему полю кроме первых двух строк
                    if (chooseHorizontal == 0 && i in 10..99) {
                        when (twoPartBug) {
                            3 -> {
                                bugsWithSurrounding(chooseHorizontal, i, 2, takes, BugPlacementPlayerFragment.listBugTwoFirst)
                            }
                            2 -> {
                                bugsWithSurrounding(chooseHorizontal, i, 2, takes, BugPlacementPlayerFragment.listBugTwoSecond)
                            }
                            1 -> {
                                bugsWithSurrounding(chooseHorizontal, i, 2, takes, BugPlacementPlayerFragment.listBugTwoThird)
                            }
                        }
                    }
                    //горизонтальная расстановка кроме последних двух столбцов
                    if (chooseHorizontal == 1 && (i in 10..18 ||i in 20..28 || i in 30..38 || i in 40..48 || i in 50..58 || i in 60..68 || i in 70..78 || i in 80..88 || i in 90..98)) {
                        when (twoPartBug) {
                            3 -> {
                                if (takes[i].state == 1)
                                removeBug(i, 2, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugTwoFirst )
                                bugsWithSurrounding(chooseHorizontal, i, 2, takes, BugPlacementPlayerFragment.listBugTwoFirst)
                            }
                            2 -> {
                                if (takes[i].state == 1)
                                removeBug(i, 2, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugTwoSecond )
                                bugsWithSurrounding(chooseHorizontal, i, 2, takes, BugPlacementPlayerFragment.listBugTwoSecond)
                            }
                            1 -> {
                                if (takes[i].state == 1)
                                removeBug(i, 2, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugTwoThird)
                                bugsWithSurrounding(chooseHorizontal, i, 2, takes, BugPlacementPlayerFragment.listBugTwoThird)
                            }
                        }
                    }
                    // удаление вертикальных кораблей в последних двух столбах
                    if (!( i in 0..8 || i in 10..18 || i in 20..28 || i in 30..38 || i in 40..48 || i in 50..58 || i in 60..68 || i in 70..78 || i in 80..88 || i in 90..98) && (chooseHorizontal == 1)) {
                            when (twoPartBug) {
                                3 -> {
                                    if (takes[i].state == 1)
                                    removeBug(i, 2, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugTwoFirst )
                                    bugsWithSurrounding(chooseHorizontal, i, 2, takes, BugPlacementPlayerFragment.listBugTwoFirst)
                                }
                                2 -> {
                                    if (takes[i].state == 1)
                                    removeBug(i, 2, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugTwoSecond )
                                    bugsWithSurrounding(chooseHorizontal, i, 2, takes, BugPlacementPlayerFragment.listBugTwoSecond)
                                }
                                1 -> {
                                    if (takes[i].state == 1)
                                    removeBug(i, 2, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugTwoThird)
                                    bugsWithSurrounding(chooseHorizontal, i, 2, takes, BugPlacementPlayerFragment.listBugTwoThird)
                                }
                            }
                        chooseHorizontal = 2
                    }
                    //удаление горизонтального жука, завершение цикла установки корабля
                    if (chooseHorizontal == 2 ) {
                            when (twoPartBug) {
                                3 -> {
                                    if (takes[i].state == 1)
                                    removeBug(i, 2, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugTwoFirst )
                                    bugsWithSurrounding(chooseHorizontal, i, 2, takes, BugPlacementPlayerFragment.listBugTwoFirst)
                                }
                                2 -> {
                                    if (takes[i].state == 1)
                                    removeBug(i, 2, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugTwoSecond )
                                    bugsWithSurrounding(chooseHorizontal, i, 2, takes, BugPlacementPlayerFragment.listBugTwoSecond)
                                }
                                1 -> {
                                    if (takes[i].state == 1)
                                    removeBug(i, 2, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugTwoThird)
                                    bugsWithSurrounding(chooseHorizontal, i, 2, takes, BugPlacementPlayerFragment.listBugTwoThird)
                                }
                            }
                    }
                    chooseHorizontal++
                    if (chooseHorizontal == 3) {
                        chooseHorizontal = 0
                    }
                }
            } else if (twoPartBug == 0 && onePartBug > 0) {
            when (onePartBug) {
                4 -> {
                    bugsWithSurrounding(chooseHorizontal, i, 1, takes, BugPlacementPlayerFragment.listBugOneFirst)
                }
                3 -> {
                    bugsWithSurrounding(chooseHorizontal, i, 1, takes, BugPlacementPlayerFragment.listBugOneSecond)
                }
                2 -> {
                    bugsWithSurrounding(chooseHorizontal, i, 1, takes, BugPlacementPlayerFragment.listBugOneThird)
                }
                1 -> {
                    bugsWithSurrounding(chooseHorizontal, i, 1, takes, BugPlacementPlayerFragment.listBugOneFourth)
                }
            }
                if (chooseHorizontal == 1) {
                    when (onePartBug) {
                        4 -> {
                            if (takes[i].state == 1)
                            removeBug(i, 1, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugOneFirst)
                            bugsWithSurrounding(chooseHorizontal, i, 1, takes, BugPlacementPlayerFragment.listBugOneFirst)
                        }
                        3 -> {if (takes[i].state == 1)
                            removeBug(i, 1, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugOneSecond)
                            bugsWithSurrounding(chooseHorizontal, i, 1, takes, BugPlacementPlayerFragment.listBugOneSecond)
                        }
                        2 -> {if (takes[i].state == 1)
                            removeBug(i, 1, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugOneThird)
                            bugsWithSurrounding(chooseHorizontal, i, 1, takes, BugPlacementPlayerFragment.listBugOneThird)
                        }
                        1 -> {if (takes[i].state == 1)
                            removeBug(i, 1, chooseHorizontal, takes, BugPlacementPlayerFragment.listBugOneFourth)
                            bugsWithSurrounding(chooseHorizontal, i, 1, takes, BugPlacementPlayerFragment.listBugOneFourth)
                        }
                    }
                    chooseHorizontal = 2
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
                 if ((i in 0..6 || i in 10..16 || i in 20..26)) {
                     if (chooseHorizontal == 0) {
                         chooseHorizontal = 1
                         bugsWithSurrounding(chooseHorizontal, i, 4, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugFour)
                     }
                 }
                 //вертикальная установка по всему полю кроме первых трех строк
                 if (chooseHorizontal == 0 && i in 30..99) {
                     bugsWithSurrounding(chooseHorizontal, i, 4, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugFour)
                 }
                 //горизонтальная расстановка кроме последних трех столбцов
                 if (chooseHorizontal == 1 && (i in 30..36 || i in 40..46 || i in 50..56 || i in 60..66 || i in 70..76 || i in 80..86 || i in 90..96)) {
                     removeBug(i,4, chooseHorizontal, takesPlayerTwo,BugPlacementPlayerSecondFragment.listBugFour )
                     bugsWithSurrounding(chooseHorizontal,i,4, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugFour)
                     //chooseHorizontal = 2
                 }
                 // удаление вертикальных кораблей в последних трех столбах
                 if (!( i in 0..6 || i in 10..16 || i in 20..26 || i in 30..36 || i in 40..46 || i in 50..56 || i in 60..66 || i in 70..76 || i in 80..86 || i in 90..96) && (chooseHorizontal == 1)) {
                     removeBug(i,4, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugFour)
                     chooseHorizontal = 2
                 }
                 //удаление горизонтального жука, завершение цикла установки корабля
                 if (chooseHorizontal == 2 ) {
                     removeBug(i,4, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugFour)
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
                         when (threePartBug) {
                             2 -> {
                                 bugsWithSurrounding(chooseHorizontal, i, 3, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeFirst)
                             }
                             1 -> {
                                 bugsWithSurrounding(chooseHorizontal, i, 3, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeSecond)
                             }
                         }
                     }
                 }
                 //вертикальная установка по всему полю кроме первых двух строк
                 if (chooseHorizontal == 0 && i in 20..99) {
                     when (threePartBug) {
                         2 -> {
                             bugsWithSurrounding(chooseHorizontal, i, 3, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeFirst)
                         }
                         1 -> {
                             bugsWithSurrounding(chooseHorizontal, i, 3, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeSecond)
                         }
                     }
                 }
                 //горизонтальная расстановка кроме последних двух столбцов
                 if (chooseHorizontal == 1 && (i in 20..27 ||i in 30..37 || i in 40..47 || i in 50..57 || i in 60..67 || i in 70..77 || i in 80..87 || i in 90..97)) {
                     //чтобы не перетирались значения уже установленных жуков
                     if (takesPlayerTwo[i].state == 1) {
                         when (threePartBug) {
                             2 -> {
                                 removeBug(i, 3, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeFirst )
                                 bugsWithSurrounding(chooseHorizontal, i, 3, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeFirst)
                             }
                             1 -> {
                                 removeBug(i, 3, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeSecond )
                                 bugsWithSurrounding(chooseHorizontal, i, 3, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeSecond)
                             }
                         }
                     }
                 }
                 // удаление вертикальных кораблей в последних двух столбах
                 if (!( i in 0..7 || i in 10..17 || i in 20..27 || i in 30..37 || i in 40..47 || i in 50..57 || i in 60..67 || i in 70..77 || i in 80..87 || i in 90..97) && (chooseHorizontal == 1)) {
                     //чтобы не перетирались значения уже установленных жуков
                     if (takesPlayerTwo[i].state == 1) {
                         when (threePartBug) {
                             2 -> {
                                 removeBug(i, 3, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeFirst )
                                 bugsWithSurrounding(chooseHorizontal, i, 3, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeFirst)
                             }
                             1 -> {
                                 removeBug(i, 3, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeSecond )
                                 bugsWithSurrounding(chooseHorizontal, i, 3, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeSecond)
                             }
                         }
                     }
                     chooseHorizontal = 2
                 }
                 //удаление горизонтального жука, завершение цикла установки корабля
                 if (chooseHorizontal == 2 ) {
                     //чтобы не перетирались значения уже установленных жуков
                     if (takesPlayerTwo[i].state == 1) {
                         when (threePartBug) {
                             2 -> {
                                 removeBug(i, 3, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeFirst )
                                 bugsWithSurrounding(chooseHorizontal, i, 3, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeFirst)
                             }
                             1 -> {
                                 removeBug(i, 3, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeSecond )
                                 bugsWithSurrounding(chooseHorizontal, i, 3, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugThreeSecond)
                             }
                         }
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
                         when (twoPartBug) {
                             3 -> {
                                 bugsWithSurrounding(chooseHorizontal, i, 2, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoFirst)
                             }
                             2 -> {
                                 bugsWithSurrounding(chooseHorizontal, i, 2, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoSecond)
                             }
                             1 -> {
                                 bugsWithSurrounding(chooseHorizontal, i, 2, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoThird)
                             }
                         }
                     }
                 }
                 //вертикальная установка по всему полю кроме первых двух строк
                 if (chooseHorizontal == 0 && i in 10..99) {
                     when (twoPartBug) {
                         3 -> {
                             bugsWithSurrounding(chooseHorizontal, i, 2, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoFirst)
                         }
                         2 -> {
                             bugsWithSurrounding(chooseHorizontal, i, 2, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoSecond)
                         }
                         1 -> {
                             bugsWithSurrounding(chooseHorizontal, i, 2, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoThird)
                         }
                     }
                 }
                 //горизонтальная расстановка кроме последних двух столбцов
                 if (chooseHorizontal == 1 && (i in 10..18 ||i in 20..28 || i in 30..38 || i in 40..48 || i in 50..58 || i in 60..68 || i in 70..78 || i in 80..88 || i in 90..98)) {
                     if (takesPlayerTwo[i].state == 1) {
                         when (twoPartBug) {
                             3 -> {
                                 removeBug(i, 2, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoFirst )
                                 bugsWithSurrounding(chooseHorizontal, i, 2, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoFirst)
                             }
                             2 -> {
                                 removeBug(i, 2, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoSecond )
                                 bugsWithSurrounding(chooseHorizontal, i, 2, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoSecond)
                             }
                             1 -> {
                                 removeBug(i, 2, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoThird)
                                 bugsWithSurrounding(chooseHorizontal, i, 2, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoThird)
                             }
                         }
                     }
                 }
                 // удаление вертикальных кораблей в последних двух столбах
                 if (!( i in 0..8 || i in 10..18 || i in 20..28 || i in 30..38 || i in 40..48 || i in 50..58 || i in 60..68 || i in 70..78 || i in 80..88 || i in 90..98) && (chooseHorizontal == 1)) {
                     if (takesPlayerTwo[i].state == 1) {
                         when (twoPartBug) {
                             3 -> {
                                 removeBug(i, 2, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoFirst )
                                 bugsWithSurrounding(chooseHorizontal, i, 2, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoFirst)
                             }
                             2 -> {
                                 removeBug(i, 2, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoSecond )
                                 bugsWithSurrounding(chooseHorizontal, i, 2, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoSecond)
                             }
                             1 -> {
                                 removeBug(i, 2, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoThird)
                                 bugsWithSurrounding(chooseHorizontal, i, 2, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoThird)
                             }
                         }
                     }
                     chooseHorizontal = 2
                 }
                 //удаление горизонтального жука, завершение цикла установки корабля
                 if (chooseHorizontal == 2 ) {
                     if (takesPlayerTwo[i].state == 1) {
                         when (twoPartBug) {
                             3 -> {
                                 removeBug(i, 2, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoFirst )
                                 bugsWithSurrounding(chooseHorizontal, i, 2, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoFirst)
                             }
                             2 -> {
                                 removeBug(i, 2, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoSecond )
                                 bugsWithSurrounding(chooseHorizontal, i, 2, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoSecond)
                             }
                             1 -> {
                                 removeBug(i, 2, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoThird)
                                 bugsWithSurrounding(chooseHorizontal, i, 2, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugTwoThird)
                             }
                         }
                     }
                 }
                 chooseHorizontal++
                 if (chooseHorizontal == 3) {
                     chooseHorizontal = 0
                 }
             }
         } else if (twoPartBug == 0 && onePartBug > 0) {
             when (onePartBug) {
                 4 -> {
                     bugsWithSurrounding(chooseHorizontal, i, 1, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugOneFirst)
                 }
                 3 -> {
                     bugsWithSurrounding(chooseHorizontal, i, 1, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugOneSecond)
                 }
                 2 -> {
                     bugsWithSurrounding(chooseHorizontal, i, 1, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugOneThird)
                 }
                 1 -> {
                     bugsWithSurrounding(chooseHorizontal, i, 1, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugOneFourth)
                 }
             }
             if (chooseHorizontal == 1) {
                 if(takesPlayerTwo[i].state == 1) {
                     when (onePartBug) {
                         4 -> {
                             removeBug(i, 1, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugOneFirst)
                             bugsWithSurrounding(chooseHorizontal, i, 1, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugOneFirst)
                         }
                         3 -> {
                             removeBug(i, 1, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugOneSecond)
                             bugsWithSurrounding(chooseHorizontal, i, 1, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugOneSecond)
                         }
                         2 -> {
                             removeBug(i, 1, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugOneThird)
                             bugsWithSurrounding(chooseHorizontal, i, 1, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugOneThird)
                         }
                         1 -> {
                             removeBug(i, 1, chooseHorizontal, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugOneFourth)
                             bugsWithSurrounding(chooseHorizontal, i, 1, takesPlayerTwo, BugPlacementPlayerSecondFragment.listBugOneFourth)
                         }
                     }
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
//**************************конец расстановки жуков*****************************************

//**************************выстрелы по полям***********************************************
fun onClickGameFieldFirst(x: Float, y: Float) {
    val x: Int = (x / (width / 10)).toInt()
    val y: Int = (y / (height / 10)).toInt()
    val i: Int = y * 10 + x

    if (takes[y * 10 + x].state == 1)//bug_part
    {
        takes[y * 10 + x].state = 3 //explode
        if (killCheck(identBug(i),takes)){ // если все элементы жука подбиты
            surrounding(takes) // обводка клеток вокруг убитого жука
        }
    }


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
                    Const.SELECT_TYPE_EXPLODE -> TakeUI.STATE_EXPLODE
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
                Const.SELECT_TYPE_EXPLODE -> TakeUI.STATE_EXPLODE
                else -> TakeUI.STATE_UNDEFINED
            }

        if (state.winner != null)
            println("WIN!")
    }

    private fun bugsWithSurrounding(chooseHor: Int, i: Int, bugPart: Int, tk: MutableList<TakeUI>, listBug: MutableList<Int>) {
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


    private  fun removeBug (i: Int, bugPart: Int, chooseHor: Int, tk: MutableList<TakeUI>, listBug: MutableList<Int>) {
        if (chooseHor == 1) {
            for (s in 0 until bugPart)
                try {
                    tk[i - 10 * s].state = 0 // сам жук
                    listBug.remove(i - 10 * s)
                }
                catch (e : IndexOutOfBoundsException){
                    println(e)
                }
        }
        if (chooseHor == 2){
            for (s in 0 until bugPart)
                    try {
                        tk[i + s].state = 0 // сам жук
                        listBug.remove(i + s)
                    }
                    catch (e : IndexOutOfBoundsException){
                        println(e)
                    }
        }
    }

    private fun identBug(i: Int): MutableList<Int>{
        lateinit var listBug: MutableList<Int>
        // 4x-bug
       if (BugPlacementPlayerFragment.listBugFour.contains(i)){
            listBug = BugPlacementPlayerFragment.listBugFour
       }
        // 3x-bug
        if (BugPlacementPlayerFragment.listBugThreeFirst.contains(i)){
            listBug = BugPlacementPlayerFragment.listBugThreeFirst
        }
        if (BugPlacementPlayerFragment.listBugThreeSecond.contains(i)){
            listBug = BugPlacementPlayerFragment.listBugThreeSecond
        }
        //2x-bug
        if (BugPlacementPlayerFragment.listBugTwoFirst.contains(i)){
            listBug = BugPlacementPlayerFragment.listBugTwoFirst
        }
        if (BugPlacementPlayerFragment.listBugTwoSecond.contains(i)){
            listBug = BugPlacementPlayerFragment.listBugTwoSecond
        }
        if (BugPlacementPlayerFragment.listBugTwoThird.contains(i)){
            listBug = BugPlacementPlayerFragment.listBugTwoThird
        }
        //1x-bug
        if (BugPlacementPlayerFragment.listBugOneFirst.contains(i)){
            listBug = BugPlacementPlayerFragment.listBugOneFirst
        }
        if (BugPlacementPlayerFragment.listBugOneSecond.contains(i)){
            listBug = BugPlacementPlayerFragment.listBugOneSecond
        }
        if (BugPlacementPlayerFragment.listBugOneThird.contains(i)){
            listBug = BugPlacementPlayerFragment.listBugOneThird
        }
        if (BugPlacementPlayerFragment.listBugOneFourth.contains(i)){
            listBug = BugPlacementPlayerFragment.listBugOneFourth
        }
        return listBug
    }

    private fun killCheck(listBug : MutableList<Int>, tk: MutableList<TakeUI>): Boolean{
        var killed : Boolean = false
        var sum : Int = 0

        for (i in listBug){
            if (tk[i].state == 3) sum ++
        }
        if (sum - listBug.size == 0) killed = true

        return killed
    }

    private fun surrounding(tk: MutableList<TakeUI>) {

        for (i: Int in 0..99) {
            if (tk[i].state == 3) {

                // обводка клетки сверху
                if (i !in 0..9) {
                    if (tk[i - 10].state == 4){
                        try {
                            // tk[i - 11].state = 4;
                            tk[i - 10].state = 2;
                            // tk[i - 9].state = 4
                        } catch (e: ArrayIndexOutOfBoundsException) { println ("Exception") }
                    }
                }
                // обводка клетки снизу
                if(i !in 90..99) {
                    if (tk[i + 10].state == 4) {
                        try {
                            tk[i + 10].state = 2;
                        } catch (e: Exception) { println ("Exception") }
                    }
                }
                // обводка клетки слева
                val left = listOf<Int>(0,10,20,30,40,50,60,70,80,90)
                if (i !in left) {
                    if (tk[i - 1].state == 2 || tk[i - 1].state == 4) {
                        tk[i - 1].state = 2;
                        try {
                            tk[i + 9].state = 2
                        } catch (e: Exception) { println("Exception") }
                        try {
                            tk[i - 11].state = 2;
                        } catch (e: Exception) { println("Exception") }
                    }
                }
                // обводка клетки справа
                val right = listOf<Int>(9,19,29,39,49,59,69,79,89,99)
                if (i !in right) {
                    if (tk[i + 1].state == 2 || tk[i + 1].state == 4) {
                        tk[i + 1].state = 2;
                        try {
                            tk[i + 11].state = 2;
                        } catch (e: Exception) { println("Exception") }
                        try {
                            tk[i - 9].state = 2
                        } catch (e: Exception) { println("Exception") }
                    }
                }

            }
        }
    }
}

