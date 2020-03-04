package susu.com.getqiitalist.http.client

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import retrofit2.http.GET
import retrofit2.http.Query
import susu.com.getqiitalist.http.construct.HttpConstants
import susu.com.getqiitalist.model.entities.QiitaDTO

/**
 * 都市の天気情報を取得する通信クライアント
 */
class QiitaClient : BaseJsonClient() {


    /**
     * Qiita情報を取得する
     *
     * @param onSuccess 通信成功後の処理
     * @param onError 通信失敗後の処理
     */
    fun getQiitaNote(
        onSuccess: ((QiitaDTO) -> Unit),
        onError: ((Throwable) -> Unit)
    ): Disposable {
        val single = getClient()
            .create(QiitaService::class.java)
            .getQiitaNote(1, 20)

        return asyncSingleRequest(single, onSuccess, onError)
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
    ): Single<QiitaDTO>
}
