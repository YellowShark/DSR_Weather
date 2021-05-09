package ru.yellowshark.dsr_weather.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.yellowshark.dsr_weather.data.remote.api.ForecastApi
import ru.yellowshark.dsr_weather.domain.exception.NoConnectivityException
import ru.yellowshark.dsr_weather.utils.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideApi(retrofit: Retrofit): ForecastApi =
        retrofit.create(ForecastApi::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    fun provideClient(
        @ConnectivityInterceptor connectivityInterceptor: Interceptor,
        @ApiKeyInterceptor apiKeyInterceptor: Interceptor
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(connectivityInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(logging)
            .build()
    }

    @ConnectivityInterceptor
    @Provides
    fun provideConnectivityInterceptor(@ApplicationContext appContext: Context): Interceptor {
        fun isOnline(): Boolean {
            val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
        return Interceptor { chain ->
            if (!isOnline())
                throw NoConnectivityException()
            return@Interceptor chain.proceed(chain.request())
        }
    }

    @ApiKeyInterceptor
    @Provides
    fun provideApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("appid", API_KEY)
                .addQueryParameter("units", METRIC_UNITS)
                .addQueryParameter("lang", SECOND_LANGUAGE)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()
}