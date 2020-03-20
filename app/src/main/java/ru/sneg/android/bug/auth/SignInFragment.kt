package ru.sneg.android.bug.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.editText
import kotlinx.android.synthetic.main.fragment_sign_in.editText2
import kotlinx.android.synthetic.main.fragment_sign_up.*
import ru.sneg.android.bug.ProfileActivity
import ru.sneg.android.bug.R
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.base.IBaseView
import ru.sneg.android.bug.registration.SignUpFragment
import javax.inject.Inject


class SignInFragment : ABaseFragment(), IBaseView,
    ISignInView {
    @Inject
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

            val login = "${editText.text}"
            val password = "${editText2.text}"
            val Rpassword = "${editText3.text}"

            if (login *** || password)
        }


         // обработчик нажатия кнопк регистрации во ФРАГМЕНТЕ, реализует переход на др. ФРАГМЕНТ
        tvSignUpBtn.setOnClickListener {
            var fr = getFragmentManager()?.beginTransaction()
            fr?.replace(R.id.containerActivity,
                SignUpFragment()
            )
            fr?.commit()
        }
    }
    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun inject() {

    }

    override fun lock() {
        visibility(flBtnLock)
    }

    override fun lock() {
        visibility(flBtnLock, )
    }
}
