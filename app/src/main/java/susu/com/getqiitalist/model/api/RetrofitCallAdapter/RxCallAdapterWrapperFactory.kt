package susu.com.getqiitalist.model.api.RetrofitCallAdapter

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type
import io.reactivex.functions.Function
import susu.com.getqiitalist.model.api.exception.RetrofitException.Companion.asRetrofitException

/**
 * 通信受信時のキャッチ処理
 * RxJava 1系
 */
class RxCallAdapterWrapperFactory private constructor() : CallAdapter.Factory() {

    // 静的領域
    companion object {
        fun create(): CallAdapter.Factory {
            return RxCallAdapterWrapperFactory()
        }
    }

    private val original = RxJava2CallAdapterFactory.create()

    // CallAdapterを取得
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return RxCallAdapterWrapper(original.get(returnType, annotations, retrofit) ?: return null)
    }

    // インナークラス
    inner class RxCallAdapterWrapper<R>(private val wrapped: CallAdapter<R, *>) :
        CallAdapter<R, Any> {

        // HTTPレスポンスのタイプ
        override fun responseType(): Type {
            return wrapped.responseType()
        }

        /**
         * 通信結果のレスポンスをメインスレッドに通知する
         * @param call 非同期呼び出し
         */
        override fun adapt(call: Call<R>): Any {
            return when (val result = wrapped.adapt(call)) {
                is Single<*> -> result.onErrorResumeNext(Function { throwable ->
                    Single.error(
                        asRetrofitException(throwable)
                    )
                })
                is Observable<*> -> result.onErrorResumeNext(Function { throwable ->
                    Observable.error(
                        asRetrofitException(throwable)
                    )
                })
                is Completable -> result.onErrorResumeNext(Function { throwable ->
                    Completable.error(
                        asRetrofitException(throwable)
                    )
                })
                else -> result
            }
        }
    }
}
