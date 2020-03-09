package susu.com.getqiitalist.view.activity

import android.os.Bundle
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
}
