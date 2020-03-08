package susu.com.getqiitalist.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import susu.com.getqiitalist.R
import susu.com.getqiitalist.view.fragment.QiitaFragment

class MainActivity : BaseActivity() {

    // Fragment生成時
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 画面上部のステータスバーを消すために、全画面表示にした
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        // activityのセット
        setContentView(R.layout.activity_main)
        // ツールバーのセット
        setSupportActionBar(toolbar)

        // Fragment生成
        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, QiitaFragment.getInstance())
            transaction.commit()
        }
    }

    /**
     * TODO 今後、保守で利用する
     * 取得するユーザーの変更
     * ページ数の変更
     * などを保守する予定
     */
    // 設定メニュー
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

    /**
     * TODO 今後、保守で利用する
     * 設定ボタン以外に色の変更など
     * などを保守する予定
     */
    // 設定ボタン押下後
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}
