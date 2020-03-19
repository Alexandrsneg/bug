package ru.sneg.android.bug.repositories

class UserRepository {
    fun signUp(subscriber: (String) -> Unit, login: String, pass: String){
subscriber.invoke("$login : $pass")
    }

    fun signIn(subscriber: (String) -> Unit, login: String, pass: String){
        subscriber.invoke("$login : $pass")
    }


}
