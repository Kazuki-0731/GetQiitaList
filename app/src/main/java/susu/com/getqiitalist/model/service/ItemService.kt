package susu.com.getqiitalist.model.service

import rx.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import susu.com.getqiitalist.model.entities.QiitaDTO

/**
 * Qiita記事を取得するAPIの指定とパラメータ付与するためのインターフェース
 */
interface ItemService {

    /**
     * 対象APIを指定
     * WebAPIのGETで対象APIを指定
     *
     * @param page ページ番号
     * @param perPage 1ページあたりに含まれる要素数
     *
     * @return Call<List<QiitaDTO>> 受け取るデータ形式
     */
    // 特に RxJava 等は使わず Retrofit 標準のコールバックで結果を受け取る
    @GET("items")
    fun items(
        @Query("page") page: Int,
        @Query("par_page") perPage: Int
    ): Call<List<QiitaDTO>>

    /**
     * 対象APIを指定
     * WebAPIのGETで対象APIを指定
     *
     * @param page ページ番号
     * @param perPage 1ページあたりに含まれる要素数
     *
     * @return Observable<List<QiitaDTO>> 受け取るデータ形式
     */
    // RxJavaのコールバックで結果を受け取る
    @GET("items")
    fun itemsRx(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Observable<List<QiitaDTO>>
}
