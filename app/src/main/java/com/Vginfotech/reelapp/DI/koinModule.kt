package com.Vginfotech.reelapp.DI

import android.content.Context
import android.content.SharedPreferences
import com.Vginfotech.reelapp.API.APIURL
import com.Vginfotech.reelapp.API.ApiService
import com.Vginfotech.reelapp.API.Repository.ApiRepository
import com.Vginfotech.reelapp.API.ViewModel.ApiViewModel
import com.Vginfotech.reelapp.API.provideApiService
import com.Vginfotech.reelapp.API.provideOkHttpClient
import com.Vginfotech.reelapp.API.provideRetrofit
import com.Vginfotech.reelapp.SharedPrefManager
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(APIURL)
            .addConverterFactory(GsonConverterFactory.create())

            .client(get()) // Inject OkHttpClient
            .build()
    }

    single { get<Retrofit>().create(ApiService::class.java) } // Provide ApiService
}
// Repository Module
    val repositoryModule = module {
        single { ApiRepository(get()) } // Inject ApiService
    }
// ViewModel Module
    val viewModelModule = module {
        viewModel { ApiViewModel(get()) } // Inject DataRepository
    }

val appModule = module {
    single<SharedPreferences> {
        get<Context>().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    }
    single { SharedPrefManager(get()) }
}
