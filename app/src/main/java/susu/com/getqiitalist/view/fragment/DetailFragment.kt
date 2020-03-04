package susu.com.getqiitalist.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.detail_fragment.*
import susu.com.getqiitalist.R
import susu.com.getqiitalist.util.LogUtils

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
         * 横画面に回転した際に何度もFragmentが呼ばれてしまうため
         */
        retainInstance = true
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    // View生成後後
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        LogUtils.d("debug", "detailFragment")

        /**
         * TODO : もし空であれば、ダイアログ表示するか、404ページを表示させる
         * 表示中はロードアイコンを表示させる
         */
        // URL読み込み
        detailView.loadUrl(url)
    }
}
