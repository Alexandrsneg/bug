package ru.sneg.android.bug.domain.di.components

import dagger.Component
import ru.sneg.android.bug.credentials.auth.SignInFragment
import ru.sneg.android.bug.credentials.loading.LoadingFragment
import ru.sneg.android.bug.credentials.profile.ProfileFragment
import ru.sneg.android.bug.credentials.registration.SignUpFragment
import ru.sneg.android.bug.domain.di.modules.NetModule
import javax.inject.Singleton


@Singleton
@Component(modules = [  // механизм создающий дагер, является мостом между  фрагментом и презентором
//    AppModule::class,
    NetModule::class
])

interface AppComponent {

    fun inject(target: SignUpFragment)
    fun inject(target: SignInFragment)
    fun inject(target: LoadingFragment)
    fun inject(target: ProfileFragment)

}