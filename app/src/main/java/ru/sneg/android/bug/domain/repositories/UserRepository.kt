package ru.sneg.android.bug.domain.repositories

import javax.inject.Inject

class UserRepository {

    @Inject
    constructor()
    fun signUp(subscriber: (String) -> Unit, login: String, pass: String){
subscriber.invoke("$login : $pass")
    }

    fun signIn(subscriber: (String) -> Unit, login: String, pass: String){
        subscriber.invoke("$login : $pass")
    }


}
