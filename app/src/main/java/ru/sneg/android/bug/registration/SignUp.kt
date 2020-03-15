package ru.sneg.android.bug.registration

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.*
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.sneg.android.bug.R

class SignUp : MvpAppCompatActivity(), ISignUpView {

@InjectPresenter
lateinit var presenter : SignUpPresenter

@ProvidePresenter
fun providePresenter() = SignUpPresenter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN )
        setContentView(R.layout.activity_sign_up)
    }


   override fun showError(message: String) {
       Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show()
    }

}