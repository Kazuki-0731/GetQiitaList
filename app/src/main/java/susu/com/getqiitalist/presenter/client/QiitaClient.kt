package susu.com.getqiitalist.presenter.client

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable
import rx.Subscription
import susu.com.getqiitalist.presenter.constants.HttpConstants
import susu.com.getqiitalist.model.entities.QiitaDTO
import susu.com.getqiitalist.model.service.QiitaListService
import susu.com.getqiitalist.model.service.QiitaNoteService

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
class QiitaClient : BaseJsonClient() {
    /**
     * QiitaのList情報を取得する
     *
     * @param onSuccess 通信成功後の処理
     * @param onError 通信失敗後の処理
     * @param onComplete 通信完了後の処理
     */
    fun getQiitaList(
        onSuccess: ((List<QiitaDTO>) -> Unit),
        onError: ((Throwable) -> Unit)
    ): Subscription {
        // 受け取るデータ形式の取り決め生成
        val observable = getClient()
            .create(QiitaListService::class.java)
            .getQiitaNote(HttpConstants.PAGE, HttpConstants.PER_PAGE)

        // 定期受信要求を実行
        return asyncRequest(observable, onSuccess, onError)
    }

    /**
     * Qiita情報を取得する
     *
     * @param onSuccess 通信成功後の処理
     * @param onError 通信失敗後の処理
     * @param onComplete 通信完了後の処理
     */
    fun getQiitaNote(
        id : String,
        onSuccess: ((QiitaDTO) -> Unit),
        onError: ((Throwable) -> Unit)
    ): Subscription {
        // 受け取るデータ形式の取り決め生成
        val observable = getClientNote()
            .create(QiitaNoteService::class.java)
            .getQiitaNote(id)

        // 定期受信要求を実行
        return asyncRequest(observable, onSuccess, onError)
    }
}
