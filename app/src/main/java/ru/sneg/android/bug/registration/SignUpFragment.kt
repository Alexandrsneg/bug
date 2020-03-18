package ru.sneg.android.bug.registration

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_sign_up.*
import ru.sneg.android.bug.R

class SignUpFragment : MvpAppCompatFragment(), ISignUpView {

    @InjectPresenter
    lateinit var presenter: SignUpPresenter

    @ProvidePresenter
    fun providePresenter() = SignUpPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        button.setOnClickListener { // обработчик нажатия на кнопку зерег-ся
            presenter.signUp("${editText.text}", "${editText2.text}", "${editText3.text}")
        }

        //при нажатии кнопки Sign Up переходим на экран Входа
       /* fun signUpBtn(view: View) {
            val intent = Intent(this@SignUpFragment, SignIn::class.java)
            startActivity(intent)
            presenter.signUp("${editText}", "${editText2}")

        }*/
    }
    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}