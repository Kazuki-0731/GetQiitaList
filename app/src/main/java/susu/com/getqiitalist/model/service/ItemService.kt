package susu.com.getqiitalist.model.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import susu.com.getqiitalist.model.entities.QiitaDTO

interface ItemService {

    // 特に RxJava 等は使わず Retrofit 標準のコールバックで結果を受け取る
    @GET("items")
    fun items(
        @Query("page") page: Int,
        @Query("par_page") perPage: Int
    ): Call<List<QiitaDTO>>
}
