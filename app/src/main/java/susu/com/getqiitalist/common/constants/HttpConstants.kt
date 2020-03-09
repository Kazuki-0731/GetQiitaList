package susu.com.getqiitalist.common.constants

/**
 * 通信で使用する定数を定義する
 */
class HttpConstants {

    companion object {

        // ========== 通信に関する定数群 =========== //
        /** 通信のタイムアウト秒数 */
        const val CONNECT_TIMEOUT_MS = 120L
        /** 読み取りのタイムアウト秒数 */
        const val READ_TIMEOUT_MS = 120L
        /** 最大リトライ回数 */
        const val RETRY_COUNT = 3L

        // ========== リクエストヘッダー =========== //
        const val HEADER_USER_AGENT = "User-Agent"

        // ========== APIパラメータ =========== //
        /** ページ番号 */
        const val PAGE = 1
        /** 1ページあたりに含まれる要素数 */
        const val PER_PAGE = 100

        // ========== URLのリスト =========== //
        /** 指定URL */
        const val LIST_URL = "https://qiita.com/api/v2/users/susu_susu__/"
        const val NOTE_URL = "https://qiita.com/api/v2/"
    }
}
