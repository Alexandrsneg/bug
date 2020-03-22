package ru.sneg.android.bug.domain.repositories.rest.api

import ru.sneg.android.bug.base.ABaseRestApi
import ru.sneg.android.bug.base.IRestClient
import ru.sneg.android.bug.domain.di.models.User
import ru.sneg.android.bug.domain.di.modules.NetModule
import javax.inject.Inject
import javax.inject.Named

class UserRestApi : ABaseRestApi<IUserRestApiService> {

    @Inject
    constructor(@Named(NetModule.NAME_AUTH_REST_CLIENT) client: IRestClient) : super(client)


    fun registration(login: String, password: String)
            = service.registration(User(login = login, password = password))


    fun login(login: String, password: String)
            = service.login(User(login = login, password = password))


//    fun refreshToken(refreshToken: String)
//            = service.refreshToken("refresh_token", refreshToken, CLIENT_ID, CLIENT_SECRET)
}