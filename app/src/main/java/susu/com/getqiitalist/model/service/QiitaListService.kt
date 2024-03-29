package susu.com.getqiitalist.model.service

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable
import susu.com.getqiitalist.model.entities.QiitaDTO

/**
 * Qiita記事を取得するAPIの指定とパラメータ付与するためのインターフェース
 */
interface QiitaListService {
    /**
     * 対象APIを指定
     * WebAPIのGETで対象APIを指定
     *
     * @param page ページ番号
     * @param perPage 1ページあたりに含まれる要素数
     *
     * @return Observable<List<QiitaDTO>> 受け取るデータ形式
     */
    @GET("items")
    fun getQiitaNote(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Observable<List<QiitaDTO>>
}
