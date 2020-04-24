package ru.sneg.android.bug.domain.repositories.models.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserRealm : RealmObject()  {

    @PrimaryKey
    var id: Int = 0
    var login: String? = null
    var password: String? = null
    var score: Int = 0
    var avatarUrl: String? = null
    var token: TokenRealm? = null
}