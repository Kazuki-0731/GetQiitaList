package susu.com.getqiitalist.model.api.exception

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class RetrofitException private constructor(

    /** エラーメッセージ */
    var mMessage: String?,
    /** 接続先のURL */
    val mUrl: String?,
    /** Httpのレスポンス */
    val mResponse: Response<*>?,
    /** エラーの種別 */
    val mErrorType: ErrorType,
    val mException: Throwable

) : RuntimeException(mMessage, mException) {

    override fun toString(): String {
        return "${super.toString()} : $mErrorType : $mUrl : ${mResponse?.errorBody()?.string()}"
    }

    enum class ErrorType {

        /** サーバー通信中に発生したエラー */
        NETWORK,
        /** サーバーから200番台以外の、ステータスコードを受け取った場合のエラー */
        HTTP,
        /** それ以外のエラー */
        UNEXPECTED
    }

    companion object {

        /**
         * ステータスコードに関するエラーを生成する
         * @param url 通信したURL
         * @param response 全HTTP response
         * @param httpException エラー内容
         */
        private fun httpError(
            url: String,
            response: Response<*>,
            httpException: HttpException
        ): RetrofitException {
            val message = "${response.code()} ${response.message()}"
            return RetrofitException(message, url, response, ErrorType.HTTP, httpException)
        }

        /**
         * サーバー通信に関するエラーを生成する
         * @param exception エラー内容
         */
        private fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(exception.message, null, null, ErrorType.NETWORK, exception)
        }

        /**
         * それ以外のエラーを生成する
         * @param exception エラー内容
         */
        private fun unexpectedError(exception: Throwable): RetrofitException {
            return RetrofitException(exception.message, null, null, ErrorType.UNEXPECTED, exception)
        }

        /**
         * RetrofitExceptionに返還する
         *
         * @param throwable 例外
         */
        fun asRetrofitException(throwable: Throwable): RetrofitException =
            when (throwable) {
                is RetrofitException -> throwable
                is HttpException -> {
                    val response = throwable.response()
                    httpError(response.raw().request().url().toString(), response, throwable)
                }
                is IOException -> networkError(throwable)
                else -> unexpectedError(throwable)
            }
    }
}