package ru.sneg.android.bug.domain.repositories.models.rest

import com.google.gson.annotations.SerializedName
import ru.sneg.android.bug.domain.repositories.models.rest.Token

data class User (
    val id: Int? = null,
    val login: String,
    val password: String,

    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    var token: Token? = null
)