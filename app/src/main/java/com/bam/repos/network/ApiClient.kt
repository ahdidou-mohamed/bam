package com.bam.repos.network

import com.bam.repos.utils.Constant.Companion.BASE_URL
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {
    private var retrofit: Retrofit? = null

    /**
     * This method returns retrofit client instance
     *
     * @return Retrofit object
     */
    val client: Retrofit?
        get() {
            if (retrofit == null) {

                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                val httpClient = OkHttpClient.Builder()
                    .readTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                httpClient.addInterceptor(logging)

                val okHttpClient = httpClient.build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build()
            }
            return retrofit
        }
}