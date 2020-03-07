package ru.sneg.android.bug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    val quantity = 10;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

fun  main(args: Array<String>){
    var forr = 1..10
    for (y in forr){
        println(quantity)
    }

    var arr = arrayOf(1,2,3)
    println(arr[0])
}

}
