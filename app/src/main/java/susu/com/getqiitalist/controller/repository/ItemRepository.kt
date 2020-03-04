package susu.com.getqiitalist.controller.repository

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import susu.com.getqiitalist.model.entities.QiitaDTO
import susu.com.getqiitalist.model.service.ItemService

/**
 * WebAPIを叩いて、JSON取得してModelに格納して返却するクラス
 *
 * 公式ドキュメント
 * https://qiita.com/api/v2/docs#get-apiv2usersuser_iditems
 *
 * ## 使用API
 * https://qiita.com/api/v2/users/{{USER_ID}}/items?page={{PAGE}}&per_page={{PAR_PAGE}}
 *
 * 使用例
 * https://qiita.com/api/v2/users/:user_id/items?page=1&per_page=20
 *
 * ●パラメータについて
 * - ユーザのID
 * USER_ID = "XXXXX"
 *
 * - ページ番号 (1から100まで)
 * PAGE = "1"
 *
 * - 1ページあたりに含まれる要素数 (1から100まで)
 * PAR_PAGE = "100"
 *
 * ## 画面1
 * APIから取得した内容をリストで表示する
 * 表示項目
 * - 記事タイトル
 *
 * ## 画面2
 * 渡されたURLをWebViewで表示する
 *
 * ## 動作
 * アプリ立ち上げ時画面1へ遷移
 * 画面1のリスト内の要素を押下すると押下されたアイテムのURLを引数に画面2へ遷移
 */
class ItemRepository {

    // メンバ変数
    private var itemService: ItemService
    private var USER_ID = "susu_susu__"
    private var PAGE = 1
    private var PAR_PAGE = 20

    // 初期化
    init {
        val okHttpClient = OkHttpClient.Builder().build()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://qiita.com/api/v2/users/$USER_ID/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
        itemService = retrofit.create(ItemService::class.java)
    }

    // エラー処理は省いています
    fun getItemList(callback: (List<QiitaDTO>) -> Unit) {
        itemService.items(page = PAGE, perPage = PAR_PAGE).enqueue(object : Callback<List<QiitaDTO>> {

            // データ取得後
            override fun onResponse(call: Call<List<QiitaDTO>>?, response: Response<List<QiitaDTO>>?) {
                response?.let {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            callback(it)
                        }
                    }
                }
            }

            // エラー処理
            override fun onFailure(call: Call<List<QiitaDTO>>?, t: Throwable?) {
                // エラー処理
            }
        })
    }
}
