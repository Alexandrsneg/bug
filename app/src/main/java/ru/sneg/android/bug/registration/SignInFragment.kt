package ru.sneg.android.bug.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.editText
import kotlinx.android.synthetic.main.fragment_sign_in.editText2
import kotlinx.android.synthetic.main.fragment_sign_up.*
import ru.sneg.android.bug.Profile
import ru.sneg.android.bug.R

class SignInFragment : MvpAppCompatFragment(), ISignInView {

    @InjectPresenter
    lateinit var presenter: SignInPresenter

    @ProvidePresenter
    fun providePresenter() = SignInPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

          // обработчик нажатия на кнопку вход, переход с ФРАГМЕНТА на АКТИВИТИ
        bSigInBtn.setOnClickListener {

            presenter.signIn("${editText.text}", "${editText2.text}")
        }

         // обработчик нажатия кнопкb регистрации во ФРАГМЕНТЕ, реализует переход на др. ФРАГМЕНТ
        bSignUpBtn.setOnClickListener {
            var fr = getFragmentManager()?.beginTransaction()
            fr?.replace(R.id.containerActivity, SignUpFragment())
            fr?.commit()
        }
    }
    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
