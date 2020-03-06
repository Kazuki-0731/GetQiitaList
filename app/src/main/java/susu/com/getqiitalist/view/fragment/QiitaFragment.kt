package susu.com.getqiitalist.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import susu.com.getqiitalist.R
import susu.com.getqiitalist.http.client.ApiClientManager
import susu.com.getqiitalist.http.client.QiitaClient
import susu.com.getqiitalist.http.exception.RetrofitException
import susu.com.getqiitalist.model.entities.QiitaDTO
import susu.com.getqiitalist.util.LogUtils
import susu.com.getqiitalist.view.activity.BaseActivity
import susu.com.getqiitalist.view.adapter.QiitaAdapter

/**
 * ListViewのFragment
 */
class QiitaFragment : BaseFragment() {

    // リストAdapterのメンバ変数
    private var adapter : QiitaAdapter? = null
    // Adapterにセットするデータリスト
    private var dataList: List<QiitaDTO> = mutableListOf()

    // 静的領域
    companion object {
        // 遅延宣言
        private var instance : QiitaFragment = QiitaFragment()
        // シングルトンなインスタンス取得
        fun getInstance(): QiitaFragment {
            return instance
        }
    }

    // region Fragment()拡張
    // View生成時
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        /**
         * 再描画防止(onCreateは1度しか呼ばれない)
         * 横画面に回転する度にFragmentが呼ばれてしまうため
         */
        retainInstance = true
        return inflater.inflate(R.layout.content_main, container, false)
    }

    // View生成後後
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // ローディングを表示
        activity!!.progressBar.visibility = View.VISIBLE

        // データ取得
        getQiitaData()
        // Viewの表示処理
        setupQiitaList()

        // スワイプ時の処理
        swiperefresh.setOnRefreshListener {
            // 初期化
            // なぜ初期化するのかは、以下を参照
            // https://gfx.hatenablog.com/entry/2015/06/08/091656
            mCompositeDisposable.unsubscribe()
            mCompositeDisposable = CompositeSubscription()

            getQiitaData()
        }
    }
    //endregion

    /**
     * Qiitaのリスト表示をするレイアウトを構築する
     */
    private fun setupQiitaList() {
        // Adapter生成
        adapter = QiitaAdapter(activity!!.applicationContext, fragmentManager!!)
        // 初期値格納
        adapter!!.qiitaList = dataList
        // listViewに代入
        listView.adapter = adapter
    }

    /**
     * Qiita記事のタイトル一覧を取得する
     */
    private fun getQiitaData() {
        // region RxJava 1系
//        mCompositeDisposable.add(
//            ApiClientManager.apiClient.itemsRx(page = 1, perPage = 20)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext {
//                    // 非同期スレッド
//                }
//                .doOnError {
//                    // 非同期スレッド
//                }
//                .doOnCompleted {
//                    // 非同期スレッド
//                }
//                .subscribe({
//                    LogUtils.d("debug", "rx response=$it")
//                    if(activity!!.progressBar.visibility == View.VISIBLE){
//                        // ローディングを非表示
//                        activity!!.progressBar.visibility = View.GONE
//                    }
//                    // ロードアイコン非表示
//                    swiperefresh.isRefreshing = false
//                    // Listをadapterにセット
//                    adapter!!.qiitaList = it
//                    // リロード
//                    adapter!!.notifyDataSetChanged()
//                    // ロードアイコン非表示
//                    stopSwipeLoadIcon()
//                },{
//                    // エラー種別振り分け
//                    val exception = RetrofitException.asRetrofitException(it!!)
//                    // ダイアログ表示
//                    (activity!! as? BaseActivity)?.showHttpErrorDialog(exception)
//                    // ロードアイコン非表示
//                    stopSwipeLoadIcon()
//                })
//        )
        // endregion

        // region RxJava 2系
        // 非同期処理
        mCompositeDisposable.add(
            // Qiitaの記事一覧取得
            QiitaClient().getQiitaNote(
                { qiita ->
                    // 通信後の処理
                    LogUtils.d("debug", "rx response = $qiita")
                    if(activity!!.progressBar.visibility == View.VISIBLE){
                        // ローディングを非表示
                        activity!!.progressBar.visibility = View.GONE
                    }
                    // ロードアイコン非表示
                    swiperefresh.isRefreshing = false
                    // Listをadapterにセット
                    adapter!!.qiitaList = qiita
                    // リロード
                    adapter!!.notifyDataSetChanged()
                    // ロードアイコン非表示
                    stopSwipeLoadIcon()
                },
                { throwable ->
                    // 例外発生時
                    // エラー種別振り分け
                    val exception = RetrofitException.asRetrofitException(throwable)
                    // ダイアログ表示
                    (activity!! as? BaseActivity)?.showHttpErrorDialog(exception)
                    // ロードアイコン非表示
                    stopSwipeLoadIcon()
                }
            )
        )
        // endregion
    }

    /**
     * 通信後のロードアイコン非表示
     */
    fun stopSwipeLoadIcon(){
        // ローディングを非表示
        activity!!.progressBar.visibility = View.GONE
        // ロードアイコン非表示
        swiperefresh.isRefreshing = false
    }
}
