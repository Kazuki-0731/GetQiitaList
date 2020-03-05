package susu.com.getqiitalist.http.constants

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
        const val RETRY_COUNT = 3

        // ========== リクエストヘッダー =========== //
        const val HEADER_USER_AGENT = "User-Agent"

        // ========== URLのリスト =========== //
        /** 指定URL */
        const val URL_BASE = "https://qiita.com/api/v2/users/susu_susu__/"
    }
}
