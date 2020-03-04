package susu.com.getqiitalist.http.client

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import susu.com.getqiitalist.http.construct.HttpConstants
import susu.com.getqiitalist.util.DeviceUtils
import susu.com.getqiitalist.util.LogUtils
import java.util.concurrent.TimeUnit

/**
 * 通信クラスの親クラス
 */
abstract class BaseClient {

    protected fun getHttpClient(): OkHttpClient {

        return OkHttpClient().newBuilder().apply {

            connectTimeout(HttpConstants.CONNECT_TIMEOUT_MS, TimeUnit.SECONDS)
            readTimeout(HttpConstants.READ_TIMEOUT_MS, TimeUnit.SECONDS)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
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
        onError: ((Throwable) -> Unit),
        onComplete: (() -> Unit)

    ): Disposable {

        return observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retryWhen { observable ->
                observable.take(HttpConstants.RETRY_COUNT).flatMap {
                    return@flatMap Observable.timer(100, TimeUnit.MILLISECONDS)
                }

            }
            .subscribe({
                LogUtils.d(this::class.java.simpleName, "doOnNext : ${it.toString()}")
                onNext(it)
            }, {
                LogUtils.e(this::class.java.simpleName, "doOnError : ${it.message}")
                onError(it)
            }, {
                LogUtils.d(this::class.java.simpleName, "doOnComplete")
                onComplete()
            })

    }

    /**
     * 非同期で通信する
     *
     * @param single 通信ストリーム
     * @param onSuccess 通信成功後の処理
     * @param onError 通信失敗後の処理
     */
    fun <T> asyncSingleRequest(
        single: Single<T>,
        onSuccess: ((T) -> Unit),
        onError: ((Throwable) -> Unit)
    ): Disposable {

        return single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry(HttpConstants.RETRY_COUNT)
            .subscribe({
                LogUtils.d(this::class.java.simpleName, "doOnSuccess : ${it.toString()}")
                onSuccess(it)
            }, {
                LogUtils.e(this::class.java.simpleName, "doOnError : ${it.message}")
                onError(it)
            })
    }

    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            return chain.run {
                proceed(
                    request()
                        .newBuilder()
                        .addHeader(HttpConstants.HEADER_USER_AGENT, DeviceUtils.getModel())
                        .build()
                )
            }
        }
    }
}