package susu.com.getqiitalist.controller.action

import android.app.Activity
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.content_main.*

class UserIO (activity: Activity, fragmentManager: FragmentManager) {

    // static領域
    companion object {
        lateinit var statefulActivity : Activity
        lateinit var statefulFragment : FragmentManager
    }

    // 初期化
    init {
        statefulActivity = activity
        statefulFragment = fragmentManager

        // 設定値から読み出して初期表示
        // DBからの読み取り


        // リスナーのセット
        setButtonListener()
    }

    /**
     * ボタンのonClickのリスナー群
     */
    private fun setButtonListener(){
        // Swipeした時の挙動
        statefulActivity.swiperefresh.setOnRefreshListener {
            // ロードアイコン非表示
            statefulActivity.swiperefresh.isRefreshing = false
        }
    }
}