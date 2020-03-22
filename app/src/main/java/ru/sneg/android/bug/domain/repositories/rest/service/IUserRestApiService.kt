package ru.sneg.android.bug.domain.repositories.rest.service


import ru.sneg.android.bug.domain.di.models.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface IUserRestApiService {
    /**
     * Регистрация нового профиля пользователя
     */
    @PUT("/user/v1/registration")
    fun registration(@Body user: User): Observable<User>


    /**
     * Авторизация пользователя по существующему профилю
     */
    @POST("/user/v1/login")
    fun login(@Body user: User): Observable<User>


//    /**
//     * Будет использовать для обновления текущего токена пользователя
//     *
//     *  Получить токен можно, если задать: grantType="refresh_token" и refresh_token
//     */
//    @FormUrlEncoded
//    @POST("/oauth/token")
//    fun refreshToken(
//            @Field("grant_type") grantType: String,
//            @Field("refresh_token") refreshToken: String,
//            @Field("client_id") clientId: String,
//            @Field("client_secret") clientSecret: String
//    ): Call<AuthToken>
}