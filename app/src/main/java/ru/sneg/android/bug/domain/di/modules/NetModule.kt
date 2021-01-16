package ru.sneg.android.bug.domain.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.sneg.android.bug.base.IRestClient
import ru.sneg.android.bug.domain.repositories.rest.TokenInterceptor
import ru.sneg.android.bug.domain.repositories.UserRepository
import ru.sneg.android.bug.domain.repositories.rest.RestClient
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

//скопипастили с рабочего проекта
@Module
class NetModule {
    companion object {

        private const val DOMAIN = "212.75.210.227" // взяли со свагера
        private const val DOMAIN_MAIN_API = "http://$DOMAIN:8080"
        private const val CONNECTION_TIMEOUT = 20000L

        const val NAME_AUTH_REST_CLIENT = "NAME_AUTH_REST_CLIENT"
        const val NAME_CLIENT_WITHOUT_TOKEN_INTERCEPTOR = "NAME_CLIENT_WITHOUT_TOKEN_INTERCEPTOR"
    }


    @Provides
    @Singleton
    fun provideTokenInterceptor(userRepository: UserRepository) =
        TokenInterceptor(
            userRepository
        )


    @Provides
    @Singleton
    fun provideLoggerInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    } as Interceptor


    @Provides
    @Singleton
    @Named(NAME_CLIENT_WITHOUT_TOKEN_INTERCEPTOR)
    fun provideOkHttpClientWithOutTokenInterceptor(logger: Interceptor) = OkHttpClient.Builder().apply {
        connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
        readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
        writeTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
        addInterceptor(logger)
    }.build()

//    @Provides
//    @Singleton
//    fun provideOkHttpClientWithTokenInterceptor(logger: Interceptor, token: TokenInterceptor): OkHttpClient {
//        return OkHttpClient.Builder()
//            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
//            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
//            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
//            .addInterceptor(logger)
//            .addInterceptor(token)
//            .build()
//    }


    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().create()


    @Provides
    @Singleton
    @Named(NAME_AUTH_REST_CLIENT)
    fun provideAuthRestClient(@Named(NAME_CLIENT_WITHOUT_TOKEN_INTERCEPTOR) client: OkHttpClient, gson: Gson)
            = RestClient(client, gson, DOMAIN_MAIN_API) as IRestClient
}