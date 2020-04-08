package ru.sneg.android.bug.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import java.util.jar.Attributes

 abstract class ABaseView constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr){

    abstract  fun getViewId(): Int

     init{
         val view = inflate(context, getViewId(), this)
     }
}


