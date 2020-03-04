package susu.com.getqiitalist.http.client

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import susu.com.getqiitalist.http.RxCallAdapterWrapperFactory
import susu.com.getqiitalist.util.UrlUtils

abstract class BaseJsonClient : BaseClient() {

    /**
     * 共通のクライアントを取得する
     */
    override fun getClient(): Retrofit = Retrofit.Builder().apply {

        client(getHttpClient())
        baseUrl(UrlUtils.getDomain())
        addCallAdapterFactory(RxCallAdapterWrapperFactory.create())
        addConverterFactory(GsonConverterFactory.create(Gson()))
    }.build()
}