package com.example.demoapp.retrofit

import android.content.Context
import com.example.demoapp.App
import io.requestly.rqinterceptor.api.RQCollector
import io.requestly.rqinterceptor.api.RQInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var BASE_URL = "https://reqres.in/api/"
    private val loggingInterceptor = HttpLoggingInterceptor()

    init {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    private val collector =
        RQCollector(context = App.mContext!!, sdkKey = "m9sGF3UG50O07ByVARsb")
    private val rqInterceptor =
        RQInterceptor.Builder(App.mContext!!).collector(collector).build()

    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(rqInterceptor)
        .build()

    fun create(): ApiInterface {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        return retrofit.create(ApiInterface::class.java)
    }
}