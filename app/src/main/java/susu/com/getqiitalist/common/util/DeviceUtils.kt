package susu.com.getqiitalist.common.util

import android.os.Build

/**
 * 端末情報を管理するユーティリティクラス
 */
object DeviceUtils {

    /**
     * モデル名を取得する
     *
     * @return モデル名
     */
    fun getModel(): String = Build.MODEL
}