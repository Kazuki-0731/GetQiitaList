package susu.com.getqiitalist.presenter.client

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import susu.com.getqiitalist.common.util.UrlUtils

/**
 * 基底WebAPI通信クラス
 */
abstract class BaseJsonClient : BaseClient() {

    /**
     * Listのクライアントを取得する
     */
    override fun getClient(): Retrofit =
        Retrofit.Builder().apply {
            // HTTP通信のクライアントクラス(タイムアウトなど)
            client(getHttpClient())

            // アクセスURL
            baseUrl(UrlUtils.getListURL())

            // 受信時のキャッチ処理
            // RxJava 1系
//            addCallAdapterFactory(RxCallAdapterWrapperFactory.create())
            // RxJava 2系
            addCallAdapterFactory(RxJavaCallAdapterFactory.create())

            // JSON変換クラスライブラリ
            addConverterFactory(GsonConverterFactory.create(Gson()))
        }
    .build()

}