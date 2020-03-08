package susu.com.getqiitalist.util

import susu.com.getqiitalist.presenter.constants.HttpConstants

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