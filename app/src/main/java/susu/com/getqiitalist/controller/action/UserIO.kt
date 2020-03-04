package susu.com.getqiitalist.controller.action

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.content_main.*
import susu.com.getqiitalist.util.LogUtils

class UserIO {

    // static領域
    companion object {
        lateinit var statefulActivity : Activity
        lateinit var statefulFragment : FragmentManager

        // 遅延宣言
        private var instance: UserIO? = null
        // シングルトンなインスタンス取得
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: UserIO().also { instance = it }
        }
    }

    // 初期化
    init {
        /**
         * TODO : 初期表示周り
         * 設定値から読み出して初期表示
         * DBからの読み取り
         */
        LogUtils.d("debug", "UserIO init()")
    }

    /**
     * スワイプのリスナー処理
     */
    fun setSwipeListener(getQiitaData:()->Unit){
        // Swipeした時の挙動
        statefulActivity.swiperefresh.setOnRefreshListener {
            // ロードアイコン非表示
            statefulActivity.swiperefresh.isRefreshing = false
            getQiitaData()
        }
    }
}