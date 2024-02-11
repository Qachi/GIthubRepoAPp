package com.example.ktrecyclerview.network

import com.bumptech.glide.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val BASE_URL: String = "https://api.github.com/"
private val sLogLevel =
    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
val interceptor = HttpLoggingInterceptor()
object Network {

    /**
     * Creates retrofit instance of api service.
     */
    operator fun invoke(): ApiService {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor.setLevel(sLogLevel))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        val retrofit by lazy {
            Retrofit.Builder().baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val service: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
        return service
    }
}
