package ru.sneg.android.bug.domain.repositories.local

import io.realm.Realm
import ru.sneg.android.bug.domain.repositories.models.Token
import ru.sneg.android.bug.domain.repositories.models.User
import ru.sneg.android.bug.domain.repositories.models.realm.UserRealm
import javax.inject.Inject

class UserStorage {

    var user: User? = null
        private set

    @Inject

    constructor()

    fun save(user: User){
    this.user = user

        Realm.getDefaultInstance().use{
            it.executeTransaction{
                it.copyToRealmOrUpdate(user.toRealm())
            }
        }

}

    fun save(token: Token){
        user?.token = token

        Realm.getDefaultInstance().use{
            it.executeTransaction{
              it.where(UserRealm::class.java).findFirst()?.let{
                  it.token = token.Realm()
                  it.copyToRealmOrUpdate(user.toRealm())
              }
            }
        }
    }

    fun dropCredentials() {
        user = null
    }
}