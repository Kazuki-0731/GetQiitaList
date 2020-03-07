package susu.com.getqiitalist.util

import susu.com.getqiitalist.presenter.constants.HttpConstants

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