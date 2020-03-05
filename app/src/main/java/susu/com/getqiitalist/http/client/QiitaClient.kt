package susu.com.getqiitalist.http.client

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable
import rx.Subscription
import susu.com.getqiitalist.http.construct.HttpConstants
import susu.com.getqiitalist.model.entities.QiitaDTO

/**
 * Qiitaを取得する通信クライアント
 */
class QiitaClient : BaseJsonClient() {


    /**
     * Qiita情報を取得する
     *
     * @param onSuccess 通信成功後の処理
     * @param onError 通信失敗後の処理
     */
    fun getQiitaNote(
        onSuccess: ((List<QiitaDTO>) -> Unit),
        onError: ((Throwable) -> Unit),
        onComplete:(() -> Unit)
    ): Subscription {
        val single = getClient()
            .create(QiitaService::class.java)
            .getQiitaNote(1, 20)

        return asyncRequest(single, onSuccess, onError, onComplete)
    }
}

/**
 * Qiita記事を取得するAPI
 */
interface QiitaService {

    @GET("items")
    fun getQiitaNote(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Observable<List<QiitaDTO>>
}
