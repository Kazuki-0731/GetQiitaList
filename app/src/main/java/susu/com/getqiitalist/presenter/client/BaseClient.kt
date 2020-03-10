package susu.com.getqiitalist.presenter.client

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import rx.Subscription
import susu.com.getqiitalist.common.constants.HttpConstants
import susu.com.getqiitalist.common.util.DeviceUtils
import susu.com.getqiitalist.common.util.LogUtils
import java.util.concurrent.TimeUnit

/**
 * 通信クラスの親クラス
 */
abstract class BaseClient {

    // OkHTTPクライアント取得
    protected fun getHttpClient(): OkHttpClient {
        // OkHttpの通信用クライアント
        return OkHttpClient().newBuilder().apply {
            // 通信のタイムアウト秒数
            connectTimeout(HttpConstants.CONNECT_TIMEOUT_MS, TimeUnit.SECONDS)
            // 読み取りのタイムアウト秒数
            readTimeout(HttpConstants.READ_TIMEOUT_MS, TimeUnit.SECONDS)
            // Android StudioのLogcatに「D/OkHttp:」が表示される
            addInterceptor(HttpLoggingInterceptor().apply {
                // okhttpのBodyをログ出力
                level = HttpLoggingInterceptor.Level.BODY
            })
            // Interceptorのヘッダ部を形成
            addInterceptor(HeaderInterceptor())
        }.build()
    }

    abstract fun getClient(): Retrofit

    /**
     * 非同期で通信する
     *
     * @param observable 通信ストリーム
     * @param onNext 通信成功後の処理
     * @param onError 通信失敗後の処理
     * @param onComplete 通信完了後の処理
     */
    fun <T> asyncRequest(
        observable: Observable<T>,
        onNext: ((T) -> Unit),
        onError: ((Throwable) -> Unit)
    ): Subscription {
        /**
         * Subscriptionを形成
         * 定期実行用のオブジェクト
         *
         * [ 公式 ]
         * https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#subscription
         *
         * [ 解説 ]
         * https://qiita.com/FumihikoSHIROYAMA/items/201536d9b45ef21b6bc7
         */
        return observable
            // 非同期のブロッキングI/Oに向いたサブスレッド
            .subscribeOn(Schedulers.io())
            // Observableが吐き出したデータを受け取って加工するメインスレッド
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                // データが正常に受信した時(サブスレッド)
                LogUtils.d(this::class.java.simpleName, "doOnNext : ${it.toString()}")
            }
            .doOnError {
                // 例外発生時(サブスレッド)
                LogUtils.e(this::class.java.simpleName, "doOnError : ${it.message}")
            }
            // 非同期通信の結果をメインスレッドへ通知
            .subscribe({
                // データが正常に受信した時
                LogUtils.d(this::class.java.simpleName, "doOnNext : ${it.toString()}")
                onNext(it)
            }, {
                // 例外発生時
                LogUtils.e(this::class.java.simpleName, "doOnError : ${it.message}")
                onError(it)
            })
    }

    /**
     * Interceptorのヘッダ部を形成
     *
     * [ 解説 ]
     * Interceptorは、Apache Strutsで使用されている自動的に逐次実行される処理を指す。
     *
     *「Apache Struts」とは
     * サーバサイドJava開発のデファクトスタンダードとしてあまりにも有名な、オープンソースのWebアプリケーションフレームワーク
     *
     * インターセプターは、その処理のなかで再帰的に次のインターセプターを呼び出します。
     * そして、プロパティの内容をActionに引き継ぐ
     * 詳細については、以下を参照
     * https://codezine.jp/article/detail/3264
     */
    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            return chain.run {
                // 処理
                proceed(
                    // リクエスト形成
                    request()
                        // 新規作成
                        .newBuilder()
                        // アクセストークン
                        // 閲覧数を取得する際に利用する
                        .header("Authorization", "Bearer dab4729df07243e9db5e937cf9e260edb0dc0b8a")
                        // ヘッダ(User Agent指定)
                        .addHeader(HttpConstants.HEADER_USER_AGENT, DeviceUtils.getModel())
                        // 生成
                        .build()
                )
            }
        }
    }
}