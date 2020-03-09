package susu.com.getqiitalist.presenter.client

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import susu.com.getqiitalist.model.service.Retrofit2Service
import java.util.concurrent.TimeUnit

open class ApiClientManager {
    companion object {
        private const val ENDPOINT = "https://qiita.com/api/v2/users/susu_susu__/"
        private val TAG = ApiClientManager::class.simpleName

        val apiClient: Retrofit2Service
            get() = Retrofit.Builder()
                .client(getClient())
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(Retrofit2Service::class.java)

        private fun getClient(): OkHttpClient {
            return OkHttpClient
                .Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }
    }
}
