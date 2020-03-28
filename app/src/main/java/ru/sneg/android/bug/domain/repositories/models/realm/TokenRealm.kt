package ru.sneg.android.bug.domain.repositories.models.realm

import io.realm.RealmObject

open class TokenRealm : RealmObject() {  //open классы - финализированны, от них нельзя наследоваться

    var access : String? = null
    var refresh: String? = null
}