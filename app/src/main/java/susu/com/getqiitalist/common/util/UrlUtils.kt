package susu.com.getqiitalist.common.util

import susu.com.getqiitalist.common.constants.HttpConstants

object UrlUtils {

    /**
     * APIのドメインを取得する
     *
     * @return APIドメイン
     */
    fun getListURL(): String {
        return HttpConstants.LIST_URL
    }

    /**
     * APIのドメインを取得する
     *
     * @return APIドメイン
     */
    fun getNoteURL(): String {
        return HttpConstants.NOTE_URL
    }
}