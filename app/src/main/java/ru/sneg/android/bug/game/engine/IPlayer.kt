package ru.sneg.android.bug.game.engine

interface IPlayer {
    fun start()
    fun ready()
    fun cell(value: Int)
    fun exit()
}