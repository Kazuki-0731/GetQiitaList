package susu.com.getqiitalist.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_fragment.*
import susu.com.getqiitalist.R
import susu.com.getqiitalist.model.api.client.LocalWebViewClient

class DetailFragment  : BaseFragment() {

    // 静的領域
    companion object {
        // 遅延宣言
        private var instance: DetailFragment = DetailFragment()
        private var url : String = ""
        // シングルトンなインスタンス取得
        fun getInstance(value : String = ""): DetailFragment {
            url = value
            return instance
        }
    }

    // View生成時
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        /**
         * 再描画防止(onCreateは1度しか呼ばれない)
         * 横画面に回転する度にFragmentが呼ばれてしまうため
         */
        retainInstance = true
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    // View生成後後
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // ローディングを表示
        activity!!.progressBar.visibility = View.VISIBLE
        // 読み込みタイミング処理
        detailView.webViewClient = LocalWebViewClient(activity!!)
        // URL読み込み
        detailView.loadUrl(url)
    }
}
