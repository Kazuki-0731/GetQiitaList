package susu.com.getqiitalist.util

import susu.com.getqiitalist.http.construct.HttpConstants

object UrlUtils {

    /**
     * APIのドメインを取得する
     *
     * @return APIドメイン
     */
    fun getDomain(): String {
        return HttpConstants.URL_BASE
    }
}