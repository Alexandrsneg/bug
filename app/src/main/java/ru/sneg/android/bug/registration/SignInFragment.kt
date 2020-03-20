package ru.sneg.android.bug.registration

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_sign_in.*
import ru.sneg.android.bug.ProfileActivity
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
            activity?.let{
                val intent = Intent (it, ProfileActivity::class.java)
                it.startActivity(intent)
            }
            presenter.signIn("${editText.text}", "${editText2.text}")
        }


         // обработчик нажатия кнопк регистрации во ФРАГМЕНТЕ, реализует переход на др. ФРАГМЕНТ
        tvSignUpBtn.setOnClickListener {
            var fr = getFragmentManager()?.beginTransaction()
            fr?.replace(R.id.containerActivity, SignUpFragment())
            fr?.commit()
        }
    }
    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
