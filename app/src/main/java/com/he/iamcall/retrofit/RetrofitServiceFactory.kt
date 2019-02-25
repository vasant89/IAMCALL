package com.he.iamcall.retrofit


import android.content.Context
import android.widget.Toast
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.he.iamcall.webservices.UserService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import javax.inject.Singleton


@Singleton
class RetrofitServiceFactory
@Inject
constructor(private val context: Context) {

    private val TAG = RetrofitServiceFactory::class.java.simpleName

    private var httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun showMessage(message: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.post { Toast.makeText(context, message, Toast.LENGTH_LONG).show() }
    }

    private var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).addInterceptor { chain ->

        if (!isInternetAvailable()) {
            Log.e(TAG, "No Internet!")
            showMessage("No Internet!")
        }

        val original: Request = chain.request()

        val request: Request = original.newBuilder()
                .header("x-api-key", "abcd1234")
                .method(original.method(), original.body())
                .build()

        chain.proceed(request)

    }.connectTimeout(100, TimeUnit.SECONDS).readTimeout(100, TimeUnit.SECONDS).build()


    private var retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("http://imahadev.16mb.com")
            .build()


    fun createUserService(): UserService {
        return retrofit.create(UserService::class.java)
    }



}